package com.diboot.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;

/***
 * Dibo Context Listener 
 * @author Mazc@dibo.ltd
 * @version 2017年3月21日
 * Copyright 2017 www.dibo.ltd
 */
public class ContextListener extends com.diboot.framework.listener.ContextListener {
	private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent e) {
		super.contextInitialized(e);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent e) {
		super.contextDestroyed(e);
	}

}