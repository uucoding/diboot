package com.diboot.framework.service.impl;

import com.diboot.framework.service.TraceLogService;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.service.mapper.TraceLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mazc@dibo.ltd
 * @version 2017/4/17
 * Copyright @ www.dibo.ltd
 */
@Service
public class TraceLogServiceImpl extends BaseServiceImpl implements TraceLogService {
    private static final Logger logger = LoggerFactory.getLogger(TraceLogServiceImpl.class);

    @Autowired
    private TraceLogMapper traceLogMapper;

    @Override
    protected BaseMapper getMapper(){return traceLogMapper;}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int clearLogOutdateLogsBefore(String outdate) {
        return traceLogMapper.clearLogOutdateLogsBefore(outdate);
    }
}
