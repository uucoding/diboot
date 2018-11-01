package com.diboot.framework.security;

import com.diboot.framework.config.Status;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.V;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT 认证过滤器
 * @author Mazc@dibo.ltd
 * @version 2018/1/6
 * Copyright © www.dibo.ltd
 */
public class BaseJwtAuthenticationFilter extends BasicHttpAuthenticationFilter {
    private static final Logger logger =  LoggerFactory.getLogger(BaseJwtAuthenticationFilter.class);

    /**
     * Shiro权限拦截核心方法 返回true允许访问，这里使用JWT进行认证
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 获取Token
        String accessToken = JwtHelper.getRequestToken(httpRequest);
        if (V.isEmpty(accessToken)) {
            logger.warn("Token为空！url="+httpRequest.getRequestURL());
            return false;
        }
        //获取userId
        String username = JwtHelper.getUsernameFromToken(accessToken);
        if(V.notEmpty(username)){
            logger.debug("Token认证成功！username="+username);
            return true;
        }
        logger.debug("Token认证失败！");
        return false;
    }

    /**
     * 当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理
     * @param
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        logger.debug("Token认证： onAccessDenied");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 获取Token
        String accessToken = JwtHelper.getRequestToken(httpRequest);
        String username = JwtHelper.getUsernameFromToken(accessToken);
        // 获取Token
        BaseJwtAuthenticationToken token = new BaseJwtAuthenticationToken(username, accessToken);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        SecurityUtils.getSubject().login(token);
        logger.debug("Token认证完成, username="+token);
        if(SecurityUtils.getSubject().isAuthenticated()){
            logger.debug("Token认证成功, username="+token);
            return true;
        }
        //TODO 刷新Token有效期
        // 认证失败
        logger.warn(httpRequest.getMethod() + " => " + httpRequest.getRequestURI() + " Token验证失败");
        JsonResult jsonResult = new JsonResult(Status.FAIL_INVALID_TOKEN);
        BaseHelper.responseJson((HttpServletResponse) response, jsonResult);
        logger.debug("Token认证失败, username="+token);
        return false;
    }

}
