package com.diboot.web.controller;

import com.diboot.framework.model.*;
import com.diboot.framework.service.RoleMenuService;
import com.diboot.framework.utils.UrlUtils;
import com.diboot.framework.utils.V;
import com.diboot.web.cache.AppCache;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mazc@dibo.ltd
 * @version 2018/1/4
 * Copyright © www.dibo.ltd
 */
@Controller
public class LoginAuthController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LoginAuthController.class);

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    protected String getViewPrefix() {
        return "common";
    }

    /**
     * 显示登录注册页面
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/login")
    public String manageLogin(HttpServletRequest request, ModelMap model) throws Exception{
        beforeLoginAction(request, model);
        return view(request, model, "login");
    }

    /***
     * 登录之前的验证
     * @param request
     * @param model
     * @throws Exception
     */
    private void beforeLoginAction(HttpServletRequest request, ModelMap model) throws Exception{
        // 记录之前访问的页面
        String referer = request.getHeader("Referer");
        if(referer != null && UrlUtils.isValidRedirectUrl(referer)){
            String requestURL = request.getRequestURL().toString();
            String requestURI = request.getRequestURI();
            String urlPrefix = requestURL.replace(requestURI, "")+request.getContextPath();
            if(referer.startsWith(urlPrefix)){
                model.addAttribute("referer", referer.substring(urlPrefix.length()));
            }
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public String loginAuth(HttpServletRequest request, ModelMap modelMap){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean rememberMe = "true".equals(request.getParameter("remember_me"));
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        //获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        String errorMsg = null;
        try {
            subject.login(token);
        }
        catch(UnknownAccountException uae){
            errorMsg = "invalid";
            logger.warn("用户[" + username + "]登录验证失败: 未知账户!", uae);
        }
        catch(IncorrectCredentialsException ice){
            errorMsg = "invalid";
            logger.warn("用户[" + username + "]登录验证失败: 错误的用户名密码!", ice);
        }
        catch(LockedAccountException lae){
            errorMsg = "locked";
            logger.warn("用户[" + username + "]登录验证失败: 账号已锁定!", lae);
        }
        catch(ExcessiveAttemptsException eae){
            errorMsg = "attempts";
            logger.warn("用户[" + username + "]登录验证失败: 失败尝试次数过多!", eae);
        }
        catch(AuthenticationException ae){
            errorMsg = "invalid";
            logger.warn("用户[" + username + "]登录验证失败: 错误的用户名密码!", ae);
        }

        //验证是否登录成功
        if(subject.isAuthenticated()){
            BaseUser user = (BaseUser)subject.getPrincipal();
            logger.debug("用户[" + username + "]登录认证通过！");
            // 获取可访问菜单
            Map<String, Object> criteria = new HashMap<>();
            criteria.put(BaseMenu.F.type, BaseMenu.TYPE.PC.name());
            criteria.put(BaseMenu.F.application, BaseMenu.APPLIACTION.MS.name());
            List<BaseMenu> menus = user.isAdmin()? AppCache.getAllMenus() : roleMenuService.getMenuListByRoleList(user.getRoleList(), criteria);
            user.setMenus(menus);
            // 加入缓存
            subject.getSession().setAttribute("user", user);
            // 跳转到首页
            return redirectTo("/welcome");
        }
        else{
            token.clear();
            // 验证失败，返回登录页
            String suffix = V.notEmpty(errorMsg)? "?error="+errorMsg : "";
            return redirectTo("/login" + suffix);
        }
    }

}
