package com.diboot.rest.controller;

import com.diboot.framework.config.Status;
import com.diboot.framework.model.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 允许匿名访问的Rest接口示例
 * @author Mazc@dibo.ltd
 * @version 2017/9/2
 * Copyright @ www.dibo.ltd
 */
@Controller
@RequestMapping("/public")
public class PublicExampleController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(PublicExampleController.class);

    /***
     * 允许匿名访问的Rest接口示例
     * @return JsonResult
     */
    @GetMapping("/rest")
    @ResponseBody
    public JsonResult publicRest() throws Exception{
        return new JsonResult(Status.OK, "该接口允许匿名访问！");
    }

    /***
     * 允许匿名访问的Web页面示例
     * @return template path
     */
    @GetMapping("/page")
    public String publicPage() throws Exception{
        return "/common/welcome";
    }

}
