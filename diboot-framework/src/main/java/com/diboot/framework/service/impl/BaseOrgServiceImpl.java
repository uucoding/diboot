package com.diboot.framework.service.impl;

import com.diboot.framework.service.BaseOrgService;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.service.mapper.BaseOrgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mazc@dibo.ltd
 * @version 2017/4/17
 * Copyright @ www.dibo.ltd
 */
@Service("baseOrgService")
public class BaseOrgServiceImpl extends BaseServiceImpl implements BaseOrgService {
    private static final Logger logger = LoggerFactory.getLogger(BaseOrgServiceImpl.class);

    @Autowired
    private BaseOrgMapper baseOrgMapper;

    @Override
    protected BaseMapper getMapper(){return baseOrgMapper;}

}
