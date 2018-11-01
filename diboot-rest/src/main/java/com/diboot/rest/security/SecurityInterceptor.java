package com.diboot.rest.security;

import com.diboot.framework.security.BaseRTMInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * Dibo 拦截器 
 * @author Mazc@dibo.ltd
 * @version 2016年12月6日
 * Copyright @ www.dibo.ltd
 */
public class SecurityInterceptor extends BaseRTMInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
						   Object handler, ModelAndView mv) throws Exception {
        super.postHandle(request, response, handler, mv);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
								Object handler, Exception exception)
					throws Exception {
		super.afterCompletion(request, response, handler, exception);
	}
}