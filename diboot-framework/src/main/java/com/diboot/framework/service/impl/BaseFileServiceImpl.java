package com.diboot.framework.service.impl;

import com.diboot.framework.service.BaseFileService;
import com.diboot.framework.service.mapper.BaseFileMapper;
import com.diboot.framework.service.mapper.BaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mazc@dibo.ltd
 * @version 2017/4/17
 * Copyright @ www.dibo.ltd
 */
@Service("baseFileService")
public class BaseFileServiceImpl extends BaseServiceImpl implements BaseFileService {
    private static final Logger logger = LoggerFactory.getLogger(BaseFileServiceImpl.class);

    @Autowired
    private BaseFileMapper baseFileMapper;

    @Override
    protected BaseMapper getMapper(){return baseFileMapper;}

}
