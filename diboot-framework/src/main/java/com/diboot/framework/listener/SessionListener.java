package com.diboot.framework.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

import java.util.concurrent.atomic.AtomicLong;

/***
 * Dibo Session Listener 
 * @author Mazc@dibo.ltd
 * @version 2017年3月7日
 * Copyright 2017 www.dibo.ltd
 */
public class SessionListener extends SessionListenerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);

	/***
	 * 在线人数
 	 */
	private static AtomicLong count = new AtomicLong(0);

	@Override
	public void onStart(Session session) {
		count.incrementAndGet();
		logger.info("Session ["+session.getId()+"] 创建！ count="+count);
		super.onStart(session);
	}

	@Override
	public void onStop(Session session) {
		count.decrementAndGet();
		if(count.get() < 0){
			count.set(0);
		}
		logger.info("Session ["+session.getId()+"] 失效！ count="+count);
		super.onStop(session);
	}

}