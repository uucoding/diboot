package com.diboot.framework.service.impl;

import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.service.OperationLogService;
import com.diboot.framework.service.mapper.OperationLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mazc@dibo.ltd
 * @version 2017/4/17
 * Copyright @ www.dibo.ltd
 */
@Service
public class OperationLogServiceImpl extends BaseServiceImpl implements OperationLogService {
    private static final Logger logger = LoggerFactory.getLogger(OperationLogServiceImpl.class);

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    protected BaseMapper getMapper(){return operationLogMapper;}

}
