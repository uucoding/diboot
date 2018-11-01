package com.diboot.rest.controller;

import com.diboot.framework.async.AsyncLogger;
import com.diboot.framework.config.Status;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.model.OperationLog;
import com.diboot.framework.utils.BaseHelper;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mazc@dibo.ltd
 * @version 2018/9/22
 * Copyright © www.dibo.ltd
 */
@RestController
public class CustomErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @Autowired
    AsyncLogger asyncLogger;

    @RequestMapping(value = "/error")
    public JsonResult error(HttpServletRequest request) {
        String errorInfo = BaseHelper.buildExceptionInfo(request);
        if (this.asyncLogger != null) {
            this.asyncLogger.saveErrorLog(null, errorInfo);
        }
        return new JsonResult(Status.FAIL_NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return "/common/error";
    }
}
