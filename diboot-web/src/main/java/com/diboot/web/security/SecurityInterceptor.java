package com.diboot.web.security;

import com.diboot.framework.exception.PermissionException;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.security.BaseRTMInterceptor;
import com.diboot.framework.utils.V;
import com.diboot.web.cache.AppCache;
import com.diboot.web.config.Cons;
import com.diboot.web.utils.AppHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 默认拦截器
 * @author Mazc@dibo.ltd
 * @version 2016年12月6日
 * Copyright @ www.dibo.ltd
 */
public class SecurityInterceptor extends BaseRTMInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		// 请求url地址
		String requestURI = request.getRequestURI().replace(request.getContextPath(), "");
		// 判断是否有权限访问URL
		if("/".equals(requestURI) || V.isEmpty(requestURI) || requestURI.startsWith(Cons.URL_WELCOME)){
			return true;
		}
		// 该URL是否需要校验权限
		boolean requireAccessPermission = false;
		for(String url : AppCache.getAllMenuLinks()){
			if(requestURI.startsWith(url)){
				requireAccessPermission = true;
				break;
			}
		}
		// 不需要校验权限
		if(!requireAccessPermission){
			return true;
		}
		// 校验权限
		BaseUser user = AppHelper.getCurrentUser();
		if(user != null && !user.canAccessUrl(requestURI)){
			logger.warn("用户 "+user.getUsername()+" 试图访问非授权的页面: " + requestURI);
			throw new PermissionException("用户试图访问非授权的页面:" + requestURI);
		}

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