package com.diboot.framework.security;

import com.diboot.framework.exception.PermissionException;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/***
 * CSRF Token拦截器
 * @author Mazc@dibo.ltd
 * @version 2017年11月24日
 * Copyright@www.Dibo.ltd
 */
public class CsrfProtectionInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(CsrfProtectionInterceptor.class);

	/**
	 * 是否忽略json格式
	 */
	private boolean ignoreAjaxRequest = true;
	/**
	 * 需要忽略的urls
	 */
	private String[] ignoreUrls = null;
	/**
	 * TOKEN名称
	 */
	private static final String TOKEN_NAME = "_csrf_token";
	/**
	 * Session属性名称
	 */
	private static final String SESSION_TOKEN_NAME = "_csrf";
	/**
	 * 需要校验Token的请求Method
	 */
	private static final List<String> CHECK_TOKEN_METHODS = Arrays.asList("POST", "PUT", "DELETE");
	/**
	 * 需要生成Token的请求Method
	 */
	private static final List<String> GENERATE_TOKEN_METHODS = Arrays.asList("GET", "POST", "PUT", "DELETE");

	/***
	 * 设置是否忽略json，默认为true
	 * @param ignoreAjaxRequest
	 */
	public void setIgnoreAjaxRequest(boolean ignoreAjaxRequest){
		this.ignoreAjaxRequest = ignoreAjaxRequest;
	}

	/***
	 * 设置忽略的url前缀
	 * @param ignoreUrls
	 */
	public void setIgnoreUrls(String[] ignoreUrls){
		if(V.isEmpty(ignoreUrls)){
			return;
		}
		for(String url : ignoreUrls){
			if(V.notEmpty(url) && url.endsWith("*")){
				url = S.replaceAll(url, "*", "");
			}
		}
		this.ignoreUrls = ignoreUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
        // 非控制器请求直接跳出
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
		// 忽略首页
		String requestURI = request.getRequestURI().replace(request.getContextPath(), "");
		if(StringUtils.isBlank(requestURI) || "/".equals(requestURI) || "/error".equals(requestURI)){
			return true;
		}
		// 匹配忽略的路径
		if(ignoreUrls != null){
			for(String url : ignoreUrls){
				if(requestURI.startsWith(url)){
					return true;
				}
			}
		}
		// 检查token
        if(CHECK_TOKEN_METHODS.contains(request.getMethod().toUpperCase())) {
			// 忽略ajax
			if(ignoreAjaxRequest && BaseHelper.isAjaxRequest(request)){
				return true;
			}
			validateToken(request);
		}
		if(GENERATE_TOKEN_METHODS.contains(request.getMethod().toUpperCase())){
			// ajax请求 不新建token
			if(BaseHelper.isAjaxRequest(request)){
				return true;
			}
			generateToken(request);
		}
		// 其他情况忽略
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	/***
	 * 检查token
	 * @param request
	 * @throws Exception
	 */
	private void validateToken(HttpServletRequest request) throws Exception{
		// 非GET 从当前请求中
		String actualToken = request.getParameter(TOKEN_NAME);
		if (actualToken == null) {
			actualToken = request.getHeader(TOKEN_NAME);
		}
		if(V.isEmpty(actualToken)){
			throw new PermissionException(request.getMethod() + " => "+ request.getRequestURI() + "未获取到CSRF Token!");
		}
		// 跟session中比对
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute(SESSION_TOKEN_NAME) != null){
			String[] validTokens = (String[]) session.getAttribute(SESSION_TOKEN_NAME);
			if(actualToken.equals(validTokens[0])){
				validTokens[0] = null;
			}
			else if(actualToken.equals(validTokens[1])){
				validTokens[1] = null;
			}
			else{// token为空 或者 不在缓存之中
				logger.warn(request.getRequestURI() + " 提交附带了无效的CSRF Token: " + actualToken);
				throw new PermissionException("无效的CSRF Token!");
			}
		}
	}

	/***
	 * 生成Token
	 * @param request
	 * @throws Exception
	 */
	private void generateToken(HttpServletRequest request) throws Exception{
		// 跟session中比对
		HttpSession session = request.getSession();
		if(session != null){
			String[] validTokens = (String[]) session.getAttribute(SESSION_TOKEN_NAME);
			// token无效
			if(validTokens == null){
				validTokens = new String[2];
				session.setAttribute(SESSION_TOKEN_NAME, validTokens);
			}
			// 保留最新的一个token
			if(validTokens[1] != null){
				validTokens[0] = validTokens[1];
			}
			// 缓存新的token
			String newToken = S.newUuid();
			validTokens[1] = newToken;
			// 加入request
			request.setAttribute(TOKEN_NAME, newToken);
			logger.debug(request.getMethod() + " => "+ request.getRequestURI() + " 创建了CSRF Token: " + newToken);
		}
	}
}
