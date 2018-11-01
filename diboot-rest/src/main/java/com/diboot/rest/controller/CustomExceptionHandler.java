package com.diboot.rest.controller;

import com.diboot.framework.async.AsyncLogger;
import com.diboot.framework.config.Status;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.model.OperationLog;
import com.diboot.framework.utils.BaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义异常处理类
 * @author Mazc@dibo.ltd
 * @version 2018/4/11
 * Copyright © www.dibo.ltd
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @Autowired
    AsyncLogger asyncLogger;

    /***
     * 捕获异常的统一处理
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public JsonResult handleException(HttpServletRequest request, Throwable ex) {
        String errorInfo = BaseHelper.buildExceptionInfo(request);
        if (this.asyncLogger != null) {
            this.asyncLogger.saveErrorLog(null, errorInfo);
        }
        return new JsonResult(Status.FAIL_EXCEPTION, ex.getMessage());
    }

}
