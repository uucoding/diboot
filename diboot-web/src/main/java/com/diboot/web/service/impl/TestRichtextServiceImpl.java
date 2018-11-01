package com.diboot.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.service.impl.BaseServiceImpl;

import com.diboot.web.service.TestRichtextService;
import com.diboot.web.service.mapper.TestRichtextMapper;

/***
* 测试富文本相关操作Service
* @author Mazc
* @version 2018-08-21
* Copyright © www.dibo.ltd
*/
@Service
public class TestRichtextServiceImpl extends BaseServiceImpl implements TestRichtextService{
	private static final Logger logger = LoggerFactory.getLogger(TestRichtextServiceImpl.class);
	
	@Autowired
	TestRichtextMapper testRichtextMapper;
	
	@Override
	protected BaseMapper getMapper() {
		return testRichtextMapper;
	}
}