package com.diboot.framework.controller;

import com.diboot.framework.config.Status;
import com.diboot.framework.exception.PermissionException;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.model.TraceLog;
import com.diboot.framework.utils.BaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理类
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
public class ExceptionController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	/***
	 * 默认异常处理
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public String handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		// 记录日志
		logger.error("发生异常:", ex);	
		
		String requestUrl = (String) request.getAttribute("javax.servlet.error.request_uri");
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Object exception = request.getAttribute("javax.servlet.error.exception");
		
		StringBuilder sb = new StringBuilder();
		sb.append("request_uri: [").append(requestUrl).append("] Error occured : ").append("status_code=").append(statusCode)
		.append(";message=").append(request.getAttribute("javax.servlet.error.message")).append(";exception=").append(exception);
		logger.warn(sb.toString());

		// 记录错误信息
		if(asyncLogger != null){
			asyncLogger.saveErrorLog(null, ex.getMessage());
		}

		boolean noPermission = (ex instanceof PermissionException || (statusCode != null && statusCode.equals(403)));
		// 是否为AJAX请求
		if (BaseHelper.isAjaxRequest(request)){
			JsonResult errorResult = new JsonResult(noPermission? Status.FAIL_NO_PERMISSION : Status.FAIL_EXCEPTION, "处理异常: "+ex.getMessage());
			BaseHelper.responseJson(response, errorResult);
			logger.debug("Ajax请求处理异常: " + errorResult.getMsg());
			return null;
		}

		// 获取异常状态
		String exceptionType = exception != null? exception.getClass().getSimpleName() : "Exception";
		request.setAttribute("statusCode", statusCode);
		request.setAttribute("ExceptionType", exceptionType);

		return viewPage(request, "common/error");				
	}
	
	/**
	 * 页面view
	 * @param request
	 * @param view
	 * @return
	 */
	private String viewPage(HttpServletRequest request, String view){
		// 绑定操作结果
		return view;
	}

	@Override
	protected String getViewPrefix() {
		return null;
	}

}