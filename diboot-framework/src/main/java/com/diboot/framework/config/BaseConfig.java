package com.diboot.framework.config;

import com.diboot.framework.service.SystemConfigService;
import com.diboot.framework.utils.ContextHelper;
import com.diboot.framework.utils.PropertiesUtils;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * Dibo 系统配置
 * @author Mazc@dibo.ltd
 * @version 2016年12月27日
 * Copyright @ www.dibo.ltd
 */
public class BaseConfig {
	private static final Logger logger = LoggerFactory.getLogger(BaseConfig.class);
	// 缓存数据库配置信息
	private static Map<String, Map> CATEGORY_CONFIGMAP = new ConcurrentHashMap<>();

	/**
	 * 从默认的/指定的 Properties文件获取配置
	 * @param key
	 * @return
	 */
	public static String getProperty(String key, String... propertiesFileName){
		return PropertiesUtils.get(key, propertiesFileName);
	}

	/***
	 *  从默认的/指定的 Properties文件获取boolean值
	 * @param key
	 * @param propertiesFileName
	 * @return
	 */
	public static boolean isTrue(String key, String... propertiesFileName){
		return PropertiesUtils.getBoolean(key, propertiesFileName);
	}

	/***
	 * 获取int类型
	 * @param key
	 * @param propertiesFileName
	 * @return
	 */
	public static int getInteger(String key, String... propertiesFileName){
		return PropertiesUtils.getInteger(key, propertiesFileName);
	}

	/***
	 * 获取截取长度
	 * @return
	 */
	public static int getCutLength(){
		Integer length = PropertiesUtils.getInteger("system.default.cutLength");
		if(length != null){
			return length;
		}
		return 20;
	}

	/***
	 * 获取默认头像
	 * @return
	 */
	public static String getDefaultAvatar() {
		String avatar = getProperty("system.default.avatar");
		if(V.notEmpty(avatar)){
			return avatar;
		}
		return "/static/img/avatar.jpg";
	}
	
	/***
	 * 获取默认头像
	 * @return
	 */
	public static int getPageSize() {
		String pageSize = getProperty("system.default.pageSize");
		if(V.notEmpty(pageSize)){
			return Integer.parseInt(pageSize);
		}
		return 10;
	}

	/***
	 * 获取默认显示页码数量
	 * @return
	 */
	public static int getShowPagesNum() {
		String displayNum = getProperty("system.default.showPageNum");
		if(V.notEmpty(displayNum)){
			return Integer.parseInt(displayNum);
		}
		return 10;
	}

	/***
	 * 从数据库获取某类业务对应的配置信息
	 * @param category 类别
	 * @param subcategory 子类别
	 * @return
	 */
	public static Map getSystemConfigMap(String category, String subcategory){
		String cacheKey = category+","+subcategory;
		if(!CATEGORY_CONFIGMAP.containsKey(cacheKey)){
			SystemConfigService systemConfigService = (SystemConfigService) ContextHelper.getBean("systemConfigService");
			if(systemConfigService != null){
				Map map = systemConfigService.getConfigMap(category, subcategory);
				if(map != null){
					CATEGORY_CONFIGMAP.put(cacheKey, map);
				}
			}
		}
		return CATEGORY_CONFIGMAP.get(cacheKey);
	}

}