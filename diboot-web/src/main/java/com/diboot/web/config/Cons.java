package com.diboot.web.config;

import com.diboot.framework.config.BaseCons;

import java.util.HashMap;
import java.util.Map;

/***
 * Dibo 常量定义
 * @author Mazc@dibo.ltd
 * @version 2016年12月5日
 * Copyright @ www.dibo.ltd
 */
public class Cons extends BaseCons{

	/**
	 * 修改密码
	 */
	public static final String URL_CHANGEPWD = "/user/changepwd";

	/**
	 * 管理员角色ID
	 */
	public static final String ROLE_ADMIN = "ADMIN";
	
	/***
	 * 登录失败最大尝试次数
	 */
	public static final int MAX_LOGIN_FAILED_TIMES = 8;

	public static enum WX_CP_APP {
		USER_CENTER,
		MSG,
		REPAIR,
		SERVICE
	}

	public static final Map<String, String> WX_CP_APP_LABEL = new HashMap<String, String>(){{
		put(WX_CP_APP.USER_CENTER.name(), "个人中心");
		put(WX_CP_APP.MSG.name(), "消息中心");
		put(WX_CP_APP.REPAIR.name(), "运维中心");
		put(WX_CP_APP.SERVICE.name(), "运维管理");
	}};

}