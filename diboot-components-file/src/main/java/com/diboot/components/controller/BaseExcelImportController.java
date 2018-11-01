package com.diboot.components.controller;

import com.diboot.components.service.ExcelColumnService;
import com.diboot.components.file.FileHelper;
import com.diboot.components.file.excel.ExcelReader;
import com.diboot.components.model.ExcelColumn;
import com.diboot.framework.controller.BaseCrudController;
import com.diboot.framework.model.BaseFile;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.service.BaseFileService;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.service.ExcelImportRecordService;
import com.diboot.framework.utils.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Excel导入基类
 * @author Mazc@dibo.ltd
 * @version 2017/9/18
 * Copyright @ www.dibo.ltd
 */
public abstract class BaseExcelImportController extends BaseCrudController {
    private static final Logger logger = LoggerFactory.getLogger(BaseExcelImportController.class);

    @Autowired
    protected BaseFileService baseFileService;
    @Autowired
    protected ExcelColumnService excelColumnService;
    @Autowired
    protected ExcelImportRecordService excelImportRecordService;

    protected static final String PARAM_IMPORT_UUID = "importUid";

    /**
     * 获取Excel列的定义
     */
    private static Map<String, List<ExcelColumn>> excelColumnMap = new ConcurrentHashMap<>();

    /***
     * 获取Model类
     * @return
     */
    protected abstract Class<?> getModelClass();

    /***
     * 获取业务的service
     * @return
     */
    protected abstract BaseService getBusinessService();

    /***
     * 列表页处理
     * @param pageIndex
     * @param request
     * @param modelMap
     * @return
     * @throws Exception
     */
    @Override
    public String listPaging(int pageIndex, HttpServletRequest request, ModelMap modelMap) throws Exception{
        Query query = new Query(BaseFile.F.relObjType, getModelClass().getSimpleName());
        BaseUser user = BaseHelper.getCurrentUser();
        query.add(BaseModel.F.createBy, user.getId());
        query.add(BaseModel.F.active, true);

        modelMap.addAttribute("criteria", query.toMap());

        return super.listPaging(pageIndex, request, modelMap);
    }

    /***
     * 预览处理
     * @param request
     * @param modelMap
     * @return
     * @throws Exception
     */
    public String preview(HttpServletRequest request, ModelMap modelMap) throws Exception {
        try{
            BaseModel model = (BaseModel) getModelClass().newInstance();
            List<String[]> dataList = saveExcelFiles(request, model);
            // 原始数据
            modelMap.addAttribute("dataList", dataList);
            // 尝试解析数据
            convert2ModelList(dataList, true);
            // 上传文件的id
            modelMap.put(PARAM_IMPORT_UUID, model.getFromJson(PARAM_IMPORT_UUID));
        }
        catch(Exception e){
            super.bindError(modelMap, e.getMessage());
            String errorMsg = "上传数据错误: "+ e.getMessage();
            logger.error(errorMsg, e);
            modelMap.addAttribute("error", errorMsg);
        }
        return super.view(request, modelMap, "preview");
    }

    /***
     * 预览保存
     * @param request
     * @param modelMap
     * @return
     * @throws Exception
     */
    public String previewSave(HttpServletRequest request, ModelMap modelMap) throws Exception {
        String importUid = request.getParameter(PARAM_IMPORT_UUID);
        if(V.isEmpty(importUid)){
            throw new Exception("调用错误，无法获取上传文件编号 importUid！");
        }
        try{
            BaseFile importFile = (BaseFile)baseFileService.getModel(importUid);
            List<String[]> dataList = extract2List(FileHelper.getFileStorageDirectory() + importFile.getPath());
            List modelList = convert2ModelList(dataList, false);
            boolean success = false;
            String message = null;
            if(V.notEmpty(modelList)){
                enhanceModel(modelList);
                success = getBusinessService().batchCreateModels(modelList);
                if(success){
                    // 更新上传文件信息
                    importFile.setActive(true);
                    importFile.setDataCount(modelList.size());
                    baseFileService.updateModel(importFile,
                            BaseFile.F.dataCount,
                            BaseFile.F.active);

                    // 批量保存导入记录明细
                    success = excelImportRecordService.batchCreateRecords(importUid, modelList);
                    if(!success){
                        logger.warn("数据导入成功，但保存导入历史记录信息失败！fileUuid="+importUid);
                    }
                    message = this.afterCreated(request, dataList);
                }
                if (V.isEmpty(message)){
                    message = success? "成功上传 "+(modelList.size()) +"条数据。" : "数据上传失败";
                }
            }
            else{
                message = "数据上传失败: 无任何有效数据！";
            }
            super.addResultMsg(request, success, message);
        }
        catch(Exception e){
            super.bindError(modelMap, e.getMessage());
            logger.error("上传数据错误: "+ e.getMessage(), e);
        }
        return "redirect:/"+getViewPrefix()+"/list/";
    }

    /***
     * 直接上传
     * @param request
     * @param modelMap
     * @return
     * @throws Exception
     */
    public String upload(HttpServletRequest request, ModelMap modelMap) throws Exception {
        try{
            BaseModel model = (BaseModel) getModelClass().newInstance();
            List<String[]> dataList = saveExcelFiles(request, model);
            List modelList = convert2ModelList(dataList, false);
            boolean success = false;
            String message = null;
            if(V.notEmpty(modelList)){
                Object importUid = model.getFromJson(PARAM_IMPORT_UUID);
                enhanceModel(modelList);
                success = getBusinessService().batchCreateModels(modelList);
                if(success){
                    // 更新上传文件信息
                    BaseFile importFile = baseFileService.getModel(importUid);
                    if(importFile != null){
                        importFile.setActive(true);
                        importFile.setDataCount(modelList.size());
                        baseFileService.updateModel(importFile,
                                BaseFile.F.dataCount,
                                BaseModel.F.active);
                    }
                    // 批量保存导入记录明细
                    if(importUid != null){
                        success = excelImportRecordService.batchCreateRecords((String)importUid, modelList);
                        if(!success){
                            logger.warn("数据导入成功，但保存导入历史记录信息失败！fileUuid="+importUid);
                        }
                    }
                    else{
                        logger.warn("数据导入成功，但无法导入历史记录信息: importUuid不存在！");
                    }
                    // 后续处理
                    message = this.afterCreated(request, dataList);
                }
                if (V.isEmpty(message)){
                    message = success? "成功上传 "+(modelList.size()) +"条数据。" : "数据上传失败";
                }
            }
            else{
                message = "数据上传失败: 无任何有效数据！";
            }
            super.addResultMsg(request, success, message);
        }
        catch(Exception e){
            super.bindError(modelMap, e.getMessage());
            super.addResultMsg(request, false, e.getMessage());
            logger.error("上传数据错误: "+ e.getMessage(), e);
        }
        return "redirect:/"+getViewPrefix()+"/list/";
    }

    /***
     * 附加其他属性到model
     * @param modelList
     * @throws Exception
     */
    protected void enhanceModel(List<? extends BaseModel> modelList) throws Exception{
        if(V.isEmpty(modelList)){
            return;
        }
        // 填充属性
        BaseUser user = BaseHelper.getCurrentUser();
        for(BaseModel m : modelList){
            if(user != null){
                m.setCreateBy(user.getId());
                m.setCreatorName(user.getRealname());
            }
        }
    }

    protected String afterCreated(HttpServletRequest request, List<String[]> dataList) throws Exception{
        return null;
    }

    @Override
    protected void attachMore4View(Object id, BaseModel model, ModelMap modelMap) throws Exception {
        BaseFile importFile = (BaseFile)model;
        try{
            List<String[]> dataList = extract2List(FileHelper.getFileStorageDirectory() + importFile.getPath());
            modelMap.addAttribute("dataList", dataList);
        }
        catch(Exception e){
            String errMsg = "读取上传文件异常，文件可能已被删除！";
            logger.error(errMsg, e);
            modelMap.addAttribute("error", errMsg);
        }
    }

    /**
     * 加载表格列定义
     */
    private List<ExcelColumn> getExcelColumnList(String modelClass) throws Exception{
        List<ExcelColumn> list = excelColumnMap.get(modelClass);
        if(list == null){
            // 构建查询时的排序定义，根据列序号进行升序排列
            Query query = new Query(ExcelColumn.F.modelClass, modelClass);
            list = excelColumnService.getModelList(query.build());
            excelColumnMap.put(modelClass, list);
        }
        return list;
    }

    /****
     * 提取excel内容到List
     * @param excelFilePath
     * @return
     * @throws Exception
     */
    protected List<String[]> extract2List(String excelFilePath) throws Exception{
        return ExcelReader.toList(excelFilePath);
    }

    /***
     * 关联关系转换，如导入 员工姓名 转为 员工id
     * @param excelColumns
     * @param dataList
     * @return
     * @throws Exception
     */
    protected List<String[]> transform(List<ExcelColumn> excelColumns, List<String[]> dataList, boolean isPreview) throws Exception{
        return dataList;
    }

    /**
     * 将Excel数据转化为ModelList
     * @param dataList
     * @return
     * @throws Exception
     */
    protected <T extends BaseModel> List<T> convert2ModelList(List<String[]> dataList, boolean isPreview) throws Exception {
        List modelList = new ArrayList<>();
        if(V.isEmpty(dataList)){
            return modelList;
        }
        //
        List<ExcelColumn> excelColumns = getExcelColumnList(getModelClass().getSimpleName());
        // 转换为id-model的map
        Map<Object, ExcelColumn> index2ModelMap = BeanUtils.convert2KeyModelMap(excelColumns, ExcelColumn.F.colIndex);
        // 处理关联字段值转换
        dataList = transform(excelColumns, dataList, isPreview);
        // 忽略第一行
        List<String> validationErrors = new ArrayList<>();
        // 列总数
        int columnCount = dataList.get(0).length;
        if(excelColumns.size() > columnCount){
            String errorMsg = "导入Excel中的列数少于预期，请检查！期望>=" + excelColumns.size() + ", 实际=" + columnCount;
            logger.warn(errorMsg);
            validationErrors.add(errorMsg);
        }
        else{
            for (int i=1; i<dataList.size(); i++){
                Map<String, Object> map = new HashMap<>();
                String[] dataArray = dataList.get(i);
                // 非预览模式，检验字段类型
                if (!isPreview) {
                    String error = null;
                    for(Object colIndexObj : index2ModelMap.keySet()){
                        int colIndex = (int)colIndexObj;
                        // 校验字符串的值
                        ExcelColumn col = index2ModelMap.get(colIndex);
                        String cellValue = dataArray[colIndex-1];
                        String errorMsg = V.validate(cellValue, col.getValidation());
                        if(V.notEmpty(errorMsg)){
                            errorMsg = col.getColName() +" "+errorMsg;
                            if(error == null){
                                error = errorMsg;
                            }
                            else{
                                error += "; " + errorMsg;
                            }
                        }
                        // 兼容更多格式
                        Object value = BeanUtils.convertValue2Type(cellValue, col.getDataType());
                        map.put(col.getModelField(), value);
                    }
                    if(V.notEmpty(error)){
                        validationErrors.add("[第"+(i+1)+"行]: "+error);
                    }
                }
                // 绑定属性到bean
                BaseModel model = (BaseModel) getModelClass().newInstance();
                BeanUtils.bindProperties(model, map);
                // 返回
                modelList.add(model);
            }
        }
        // 有错误信息
        if(!validationErrors.isEmpty()){
            throw new Exception(S.join(validationErrors, "<br>"));
        }
        // 返回组装结果
        return modelList;
    }

    /****
     * 保存上传文件
     * @param request
     * @param model
     * @param fileInputName
     * @return
     * @throws Exception
     */
    protected List<String[]> saveExcelFiles(HttpServletRequest request, BaseModel model, String... fileInputName) throws Exception{
        // 解析上传文件
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(!isMultipart){
            throw new Exception("请上传待处理的excel文件！");
        }
        // 获取附件文件名
        String inputName = "attachedFiles";
        if(fileInputName != null && fileInputName.length > 0){
            inputName = fileInputName[0];
        }
        // 解析上传文件
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles(inputName);
        if(V.isEmpty(files)) {
            throw new Exception("未获取待处理的excel文件！");
        }
        // 解析结果，暂时处理一个excel文件
        List<String[]> dataList = null;
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (V.isEmpty(fileName) || !FileHelper.isExcel(fileName)) {
                logger.info("非Excel类型: " + fileName);
                continue;
            }
            // 文件后缀
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            // 先保存文件
            String uuid = S.newUuid();
            String newFileName = uuid + "." + ext;
            String path = FileHelper.saveFile(file, newFileName);

            BaseFile fileObj = new BaseFile();
            fileObj.setUid(uuid);
            fileObj.setUuid(uuid);
            fileObj.setName(fileName);
            fileObj.setRelObjType(model.getClass().getSimpleName());
            fileObj.setRelObjId(model.getId());
            fileObj.setFileType(ext);
            fileObj.setName(fileName);
            fileObj.setPath(path);
            fileObj.setLink("/file/download/" + uuid);
            fileObj.setSize(file.getSize());
            fileObj.setComment(request.getParameter("comment"));
            // 上传记录暂不可见，数据解析成功后再恢复
            fileObj.setActive(false);
            dataList = ExcelReader.toList(FileHelper.getFileStorageDirectory() + path);
            // 初始设置为0，批量保存数据后更新
            fileObj.setDataCount(0);
            //build ext data
            BaseUser user = BaseHelper.getCurrentUser();
            if(user != null){
                fileObj.setCreateBy(user.getId());
                fileObj.setCreatorName(user.getRealname());
            }
            boolean success = baseFileService.createModel(fileObj);
            // 绑定属性到model
            model.addToJson(PARAM_IMPORT_UUID, uuid);
            logger.info("成功保存附件: uid=" + uuid + ", name=" + fileName);
        }
        // 返回结果
        return dataList;
    }

    /***
     * 获取未能匹配的值，用于transform
     * @param inputList
     * @param realMap
     * @return
     */
    protected List<String> extractInvalidList(List<String> inputList, Map<String, Object> realMap){
        List<String> invalidList = null;
        for(String adviser : inputList){
            if(V.isEmpty(realMap.get(adviser))){
                if(invalidList == null){
                    invalidList = new ArrayList<>();
                }
                invalidList.add(adviser);
            }
        }
        return invalidList;
    }

    @Override
    protected BaseService getService() {
        return baseFileService;
    }

}
