package com.diboot.web.controller;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.service.BaseService;
import com.diboot.common.service.FileService;
import com.diboot.web.utils.AppFileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

/***
 * 增删改查通用管理功能-父类
 * @author Mazc@dibo.ltd
 */
@Controller
public abstract class BaseCrudController extends com.diboot.framework.controller.BaseCrudController {
	private static final Logger logger = LoggerFactory.getLogger(BaseCrudController.class);

	@Autowired
	protected FileService fileService;

	/**
	 * 获取service实例
	 */
	@Override
	protected abstract BaseService getService();

	/*
	 * 在这里添加Controller公用的方法
	*/

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
	 * 删除前
	 * @param model
	 * @return
	 */
	@Override
	protected String beforeDelete(BaseModel model){
		return super.beforeDelete(model);
	}

	/**
	 * 删除后
	 * @param request
	 * @param model
	 */
	@Override
	protected void afterDeleted(HttpServletRequest request, BaseModel model) {
	}

}