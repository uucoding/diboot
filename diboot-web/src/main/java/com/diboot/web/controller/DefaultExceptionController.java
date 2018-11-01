package com.diboot.web.controller;

import com.diboot.framework.async.AsyncLogger;
import com.diboot.framework.config.Status;
import com.diboot.framework.exception.BusinessException;
import com.diboot.framework.exception.PermissionException;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.model.OperationLog;
import com.diboot.framework.utils.BaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 默认的异常处理
 * @author Mazc@dibo.ltd
 * @version 2018/4/11
 * Copyright © www.dibo.ltd
 */
@ControllerAdvice
public class DefaultExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionController.class);

    @Autowired
    AsyncLogger asyncLogger;

    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler({Exception.class, BusinessException.class, PermissionException.class})
    public String handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String errorInfo = BaseHelper.buildExceptionInfo(request);
        if (this.asyncLogger != null) {
            this.asyncLogger.saveErrorLog(null, errorInfo);
        }
        if (BaseHelper.isAjaxRequest(request)) {
            Integer statusCode = (Integer)request.getAttribute("statusCode");
            boolean noPermission = ex instanceof PermissionException || statusCode != null && statusCode.equals(403);
            JsonResult errorResult = new JsonResult(noPermission ? Status.FAIL_NO_PERMISSION : Status.FAIL_EXCEPTION, new String[]{"处理异常: " + ex.getMessage()});
            BaseHelper.responseJson(response, errorResult);
            logger.debug("Ajax请求处理异常: " + errorResult.getMsg());
            return null;
        }
        else {
            return "common/error";
        }
    }

}
