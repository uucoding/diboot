package com.diboot.rest.controller;

import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.service.BaseService;
import com.diboot.rest.utils.AppFileHelper;
import com.diboot.common.service.FileService;
import com.diboot.common.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;

/***
 * 增删改查通用管理功能-父类
 * @author Mazc@dibo.ltd
 */
@Controller
public abstract class BaseCrudRestController extends com.diboot.framework.controller.BaseCrudRestController {
	private static final Logger logger = LoggerFactory.getLogger(BaseCrudRestController.class);

	/**
	 * 获取service实例
 	 */
	@Override
	protected abstract BaseService getService();

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Override
	protected JsonResult createModel(BaseModel model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
		return super.createModel(model, result, request, modelMap);
	}

	/**
	 * 创建前
	 * @param request
	 * @param model
	 * @param modelMap
	 * @throws Exception
	 */
	@Override
	protected void beforeCreate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{	
	}

	/**
	 * 创建后
	 * @param request
	 * @param model
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String afterCreated(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
		return null;
	}

	/**
	 * 更新前
	 * @param request
	 * @param model
	 * @param modelMap
	 * @throws Exception
	 */
	@Override
	protected void beforeUpdate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
	}

	/**
	 * 更新后
	 * @param request
	 * @param model
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String afterUpdated(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{		
		return null;
	}

	/**
	 * 删除前（需要权限, 默认不可删除）
	 * @param model
	 * @return
	 */
	@Override
	protected String beforeDelete(BaseModel model){
		return Status.FAIL_NO_PERMISSION.label();
	}

	/**
	 * 删除后
	 * @param request
	 * @param model
	 */
	@Override
	protected void afterDeleted(HttpServletRequest request, BaseModel model) {
	}

	/**
	 * 保存上传文件
	 * @param request
	 * @param model
	 * @param fileInputName
	 * @return
	 * @throws Exception
	 */
	protected String saveFiles(HttpServletRequest request, BaseModel model, String... fileInputName) throws Exception{
		return AppFileHelper.saveFiles(fileService, request, model, fileInputName);
	}

}