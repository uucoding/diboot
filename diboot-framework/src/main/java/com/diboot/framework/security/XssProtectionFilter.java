package com.diboot.framework.security;

import com.diboot.framework.utils.BaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * XSS保护Filter
 * @author Mazc@dibo.ltd
 * @version 2017年11月25日
 * Copyright@www.Dibo.ltd
 */
public class XssProtectionFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(XssProtectionFilter.class);

	/**
	 * 是否忽略json格式
	 */
	private boolean ignoreAjaxRequest = true;

	/***
	 * 设置是否忽略json，默认为true
	 * @param ignoreAjaxRequest
	 */
	public void setIgnoreAjaxRequest(boolean ignoreAjaxRequest){
		this.ignoreAjaxRequest = ignoreAjaxRequest;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("XSS过滤器已启用!");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 不忽略json 或者 不是Ajax请求
		if(!ignoreAjaxRequest || !BaseHelper.isAjaxRequest((HttpServletRequest) request)){
			HttpServletRequestWrapper xssRequest = new CustomHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(xssRequest, response);
		}
		else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}
	
}
