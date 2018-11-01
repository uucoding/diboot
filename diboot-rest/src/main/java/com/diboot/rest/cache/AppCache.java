package com.diboot.rest.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/***
 * Dibo 常用数据缓存 
 * @author Mazc@dibo.ltd
 * @version 2016年12月7日
 * Copyright @ www.dibo.ltd
 */
@Component
public class AppCache implements ApplicationListener<ContextRefreshedEvent>{
	private static final Logger logger = LoggerFactory.getLogger(AppCache.class);

	/***
	 * 初始化元数据
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent ctx) {

	}
}