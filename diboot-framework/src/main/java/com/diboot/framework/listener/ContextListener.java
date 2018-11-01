package com.diboot.framework.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/***
 * Dibo Context Listener 
 * @author Mazc@dibo.ltd
 * @version 2017年3月21日
 * Copyright 2017 www.dibo.ltd
 */
public class ContextListener implements ServletContextListener{
	private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("server initialized ...");		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("server destroyed ...");
	}

}