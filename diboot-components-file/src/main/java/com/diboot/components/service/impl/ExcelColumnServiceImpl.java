package com.diboot.components.service.impl;

import com.diboot.components.service.ExcelColumnService;
import com.diboot.components.service.mapper.ExcelColumnMapper;
import com.diboot.framework.service.impl.BaseServiceImpl;
import com.diboot.framework.service.mapper.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
* Excel列定义相关操作Service
* @author Mazc@dibo.ltd
* @version 2017-09-18
* Copyright @ www.dibo.ltd
*/
@Service
public class ExcelColumnServiceImpl extends BaseServiceImpl implements ExcelColumnService {
	private static final Logger logger = LoggerFactory.getLogger(ExcelColumnServiceImpl.class);
	
	@Autowired
    ExcelColumnMapper excelColumnMapper;
	
	@Override
	protected BaseMapper getMapper() {
		return excelColumnMapper;
	}
}