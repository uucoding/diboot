package com.diboot.web.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.diboot.framework.utils.*;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.config.Status;

import com.diboot.framework.config.BaseCons;
import com.diboot.web.model.TestRichtext;
import com.diboot.web.service.TestRichtextService;
import com.diboot.components.file.FileHelper;
import com.diboot.components.file.excel.ExcelWriter;

/***
 * 测试富文本相关CRUD操作Controller
 * @author Mazc
 * @version 2018-08-21
 * Copyright © www.dibo.ltd
 */
@Controller
@RequestMapping("/testRichtext")
public class TestRichtextController extends BaseCrudController {
	private static final Logger logger = LoggerFactory.getLogger(TestRichtextController.class);
	
	@Autowired
	private TestRichtextService testRichtextService;



	@Override
	protected String getViewPrefix() {
		return "testRichtext";
	}

	/***
	 * 根路径/，显示测试富文本列表数据第1页
	 * @return view
	 * @throws Exception
	 */
    @Override
	@GetMapping("/")
	public String index(HttpServletRequest request, ModelMap modelMap) throws Exception {
		return list(request, modelMap);
	}
	
	/***
	 * 显示首页面
	 * @return view
	 * @throws Exception
	 */
    @Override
	@GetMapping("/list")
	public String list(HttpServletRequest request, ModelMap modelMap) throws Exception {
		// 加载第一页
		return listPaging(1, request, modelMap);
	}
	
	/**
	 * 显示首页-分页
	 * @return view
	 * @throws Exception
	 */
    @Override
	@GetMapping("/list/{pageIndex}")
	public String listPaging(@PathVariable("pageIndex")int pageIndex, HttpServletRequest request, ModelMap modelMap) throws Exception{
		return super.listPaging(pageIndex, request, modelMap);
	}

	/***
	 * 加载测试富文本详细数据
	 * @return JsonResult
	 * @throws Exception
	 */
	@GetMapping("/view/{id}")
    public String viewPage(@PathVariable("id")long id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		return super.viewPage(id, request, modelMap);
    }
	
	/***
	 * 显示测试富文本创建页面
	 * @return view
	 * @throws Exception
	 */
    @Override
	@GetMapping("/create")
    public String createPage(HttpServletRequest request, ModelMap modelMap) throws Exception {  
		return super.createPage(request, modelMap);
    }
	
	/***
	 * 测试富文本创建的后台处理
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/create")
    public String create(@Valid TestRichtext model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
        
		return super.create(model, result, request, modelMap);
    }
	
	/***
	 * 显示更新页面
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		return super.updatePage(id, request, modelMap);
    }
	
	/***
	 * 更新的后台处理
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id")Long id, @Valid TestRichtext model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception{
		
		return super.update(id, model, result, request, modelMap);
	}

	/***
	* 批量勾选操作的后台处理
	* @return view
	* @throws Exception
	*/
	@PostMapping("/batch")
	public String batch(HttpServletRequest request, ModelMap modelMap) throws Exception{
		String redirectUrl = super.getRedirectUrlWithQueryParams(request);
		String modelIdsStr = request.getParameter("modelIds");
		if(V.isEmpty(modelIdsStr)){
			super.addResultMsg(request, false, "请选择需要操作的记录！");
			return redirectUrl;
		}
		//TODO 批量操作的后台处理
		super.addResultMsg(request, true, "TODO://请完成批量操作的后台处理！");
		return redirectUrl;
	}

	/**
	* 数据导出Excel
	* @return view
	* @throws Exception
	*/
	@GetMapping("/export")
	public String export(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception{
		// 封装查询条件
		Map<String, Object> criteria = super.buildQueryCriteria(request, 1);
		criteria.remove("pageIndex");
		List<TestRichtext> modelList = testRichtextService.getLimitModelList(criteria, BaseCons.MAX_SIZE_EXPORT);

		// 组装数据
        List<String[]> dataRows = new ArrayList<>();
		String[] headers = new String[]{"编号", "标题", "内容", "内容2", "创建人ID", "创建时间"};
        String[] fields = new String[]{TestRichtext.F.id, TestRichtext.F.title, TestRichtext.F.content, TestRichtext.F.content2, TestRichtext.F.createBy, TestRichtext.F.createTime};
		if(V.notEmpty(modelList)){
            for(TestRichtext model : modelList){
            	String[] row = new String[6];
            	for(int i=0; i<fields.length; i++){
					String fld = fields[i];
            		row[i] = (String)BeanUtils.getProperty(model, fld); //TODO 此处可能需要关联其他表的字段值
				}
            	dataRows.add(row);
            }
		}
		// 写入excel
		String outFileName = "测试富文本数据记录_" + D.getDate(new Date()) + ".xls";
		ExcelWriter writer = new ExcelWriter(outFileName);
        writer.addSheet(Arrays.asList(headers), dataRows);

        // 生成并下载
        if(writer.generate()){
        	FileHelper.downloadLocalFile(writer.getGeneratedFilePath(), request, response);
        }
        return null;
	}

	/***
	 * 删除测试富文本
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@PostMapping("/delete/{id}")
	public Map<String, Object> delete(@PathVariable("id")Long id, HttpServletRequest request) throws Exception{
		return super.delete(id, request);
	}

	/**
	 * 删除之前的权限检查等处理
	 * @param model
	 * @return 错误提示信息
	 */
	@Override
    protected String beforeDelete(BaseModel model) {
		BaseUser currentUser = BaseHelper.getCurrentUser();
    	boolean success = (currentUser != null && (currentUser.isAdmin() || currentUser.getId().equals(model.getCreateBy())));
		return success? null : Status.FAIL_OPERATION.label();
	}

	/**
	 * 附加更多的关联数据 以便列表页面和编辑表单中使用
	 */
	@Override
	protected void attachMore(HttpServletRequest request, ModelMap modelMap) throws Exception{

	}

	/**
	 * 附加更多的关联数据到view查看详细页面
	 */
	@Override
	protected void attachMore4View(Object id, BaseModel model, ModelMap modelMap) throws Exception {
	}




	@Override
	protected BaseService getService() {
		return testRichtextService;
	}
}