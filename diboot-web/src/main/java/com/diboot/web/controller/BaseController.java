package com.diboot.web.controller;

import com.diboot.framework.model.BaseModel;
import com.diboot.common.service.FileService;
import com.diboot.web.utils.AppFileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/***
 * Dibo Controller的父类
 * @author Mazc@dibo.ltd
 * @version 2016年12月5日
 * Copyright @ www.dibo.ltd
 */
@Controller
public abstract class BaseController extends com.diboot.framework.controller.BaseController{
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	protected FileService fileService;

	/***
	 * 获取view视图页面的path前缀（文件夹地址）
	 * @return
	 */
	@Override
	protected abstract String getViewPrefix();

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