package com.diboot.rest.controller;

import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.service.BaseUserService;
import com.diboot.rest.utils.AppHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户相关
 * <p>用户信息接口（包含用户信息的增删改查等）</p>
 * @author Mazc@dibo.ltd
 * @version 2017/9/1
 * Copyright @ www.dibo.ltd
 */
@RestController
@RequestMapping("/user")
public class UserRestController extends BaseCrudRestController{
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private BaseUserService baseUserService;
    @Override
    protected BaseService getService() {
        return baseUserService;
    }

    /***
     * 返回用户的集合
     * 可选参数: pageSize, ...
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    @Override
    public JsonResult getModelList(HttpServletRequest request, ModelMap modelMap) throws Exception{
        logger.debug("当前用户="+ AppHelper.getCurrentUser());
        return super.getModelList(request, modelMap);
    }

    /***
     * 创建用户
     * @return
     * @throws Exception
     */
    @PostMapping("/")
    public JsonResult createModel(@ModelAttribute BaseUser user, BindingResult result, HttpServletRequest request, ModelMap modelMap)
    throws Exception{
        logger.debug("当前用户="+AppHelper.getCurrentUser());
        return super.createModel(user, result, request, modelMap);
    }

    /***
     * 查询用户
     * @param userId 用户ID
     * @return
     * @throws Exception
     */
    @GetMapping("/{id}")
    public JsonResult getModel(@PathVariable("id")Long userId, HttpServletRequest request, ModelMap modelMap)
            throws Exception{
        logger.debug("当前用户="+AppHelper.getCurrentUser());
        return super.getModel(userId, request, modelMap);
    }

    /***
     * 更新用户
     * @param id 用户ID
     * @return
     * @throws Exception
     */
    @PutMapping("/{id}")
    public JsonResult updateModel(@PathVariable("id")Long id, @ModelAttribute BaseUser user) {
        logger.debug("当前用户="+AppHelper.getCurrentUser());
        baseUserService.updateModel(user);
        return new JsonResult(Status.OK);
    }

    /***
     * 删除用户
     * @param id 用户ID
     * @return
     * @throws Exception
     */
    @DeleteMapping("/{id}")
    public JsonResult deleteModel(@PathVariable("id")Long id) {
        logger.debug("当前用户="+AppHelper.getCurrentUser());
        baseUserService.deleteModel(id);
        return new JsonResult(Status.OK);
    }

}
