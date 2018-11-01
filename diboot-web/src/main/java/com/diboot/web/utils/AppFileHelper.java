package com.diboot.web.utils;

import com.diboot.framework.model.BaseFile;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.utils.D;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import com.diboot.common.model.File;
import com.diboot.common.service.FileService;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mazc@dibo.ltd
 * @version 2018/8/31
 * Copyright © www.dibo.ltd
 */
public class AppFileHelper extends com.diboot.components.file.FileHelper {
    private static final Logger logger = LogManager.getLogger(AppFileHelper.class);

    /**
     * 保存上传的文件
     * @param request
     * @param model
     * @param fileInputName
     * @return
     * @throws Exception
     */
    public static String saveFiles(FileService fileService, HttpServletRequest request, BaseModel model, String... fileInputName) throws Exception{
        String inputName = "attachment";
        String fileExt = null;
        if(fileInputName != null && fileInputName.length > 0){
            inputName = fileInputName[0];
            if(fileInputName.length > 1){
                fileExt = fileInputName[1];
            }
        }
        // model
        String relObjType = V.notEmpty(model)? model.getClass().getSimpleName() : request.getParameter(File.F.relObjType);
        if(V.isEmpty(relObjType)){
            relObjType = "";
        }
        if(!ServletFileUpload.isMultipartContent(request)){
            logger.warn("提交表单不支持文件类型，请检查form中是否存在: enctype='multipart/form-data'！");
            return null;
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles(inputName);
        if(V.isEmpty(files)){
            logger.warn("未检测到有效的文件提交！");
            return null;
        }
        // 文件链接
        List<String> fileLinks = new ArrayList<>();
        for(MultipartFile file : files){
            String fileName = file.getOriginalFilename();
            if(V.isEmpty(fileName)){
                continue;
            }
            String ext = fileName.substring(fileName.lastIndexOf(".")+1);
            if(!com.diboot.components.file.FileHelper.isValidFileExt(ext)){
                logger.info("非法的附件类型: "+fileName);
                continue;
            }
            if(fileExt != null && !fileExt.equalsIgnoreCase(ext)){
                throw new Exception("错误的文件格式！");
            }
            // 保存文件记录
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String newFileName = uuid+"."+ext;
            BaseFile fileObj = new BaseFile();
            fileObj.setUuid(uuid);
            fileObj.setName(fileName);
            fileObj.setRelObjType(relObjType);
            fileObj.setRelObjId(V.notEmpty(model)? model.getId() : 0);
            fileObj.setFileType(ext);
            fileObj.setName(fileName);
            fileObj.setSize(file.getSize());
            fileObj.setCreateBy(AppHelper.getCurrentUserId());
            // 是否为图片
            boolean isImageFile = com.diboot.components.file.FileHelper.isImage(ext);
            String path = isImageFile? com.diboot.components.file.FileHelper.saveImage(file, newFileName) : com.diboot.components.file.FileHelper.saveFile(file, newFileName);
            fileObj.setPath(path);
            String link = "/file/download/" + D.getYearMonth() + "_" + newFileName;
            fileObj.setLink(link);
            boolean success = fileService.createModel(fileObj);
            if(success){
                fileLinks.add(link);
                logger.info("保存文件成功，path="+ path);
            }
            else{
                logger.info("保存文件失败，path="+ path);
            }
        }
        return S.join(fileLinks);
    }

}
