package com.diboot.framework.service.impl;

import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.service.TimerTaskService;
import com.diboot.framework.service.mapper.TimerTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
* 定时任务相关操作Service
* @author Mazc@dibo.ltd
* @version 2018-01-20
* © 2017 www.dibo.ltd
*/
@Service("timerTaskService")
public class TimerTaskServiceImpl extends BaseServiceImpl implements TimerTaskService{
	private static final Logger logger = LoggerFactory.getLogger(TimerTaskServiceImpl.class);
	
	@Autowired
	TimerTaskMapper timerTaskMapper;
	
	@Override
	protected BaseMapper getMapper() {
		return timerTaskMapper;
	}
}