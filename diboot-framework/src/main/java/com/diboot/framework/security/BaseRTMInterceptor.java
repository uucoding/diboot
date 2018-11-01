package com.diboot.framework.security;

import com.diboot.framework.utils.BaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/***
 * 请求时间监控拦截器
 * @author Mazc@dibo.ltd
 * @version 2016年12月6日
 * Copyright @ www.dibo.ltd
 */
public class BaseRTMInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(BaseRTMInterceptor.class);

	private static final String START_TIME = "r_t";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							 Object handler) throws Exception {
		// 判断是否有权限访问URL
		if(logger.isDebugEnabled()){
			request.setAttribute(START_TIME, System.currentTimeMillis());
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
						   Object handler, ModelAndView mv) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
								Object handler, Exception exception)
					throws Exception {
		// 记录日志
		if(logger.isDebugEnabled()){
			String requestUri = BaseHelper.getRequestMappingURI(request);
			if(request.getAttribute(START_TIME) != null && !requestUri.startsWith("/diboot/")){
				long startTime = (Long)request.getAttribute(START_TIME);
				long t = System.currentTimeMillis() - startTime;
				logger.info(request.getMethod() + " => " + request.getRequestURL() + " takes  [" +  t + "] ms");
				if(t > 3000){
					logger.warn(request.getMethod() + " URL " + request.getRequestURL() + " need to be optimized.");
				}
			}
		}
	}
}