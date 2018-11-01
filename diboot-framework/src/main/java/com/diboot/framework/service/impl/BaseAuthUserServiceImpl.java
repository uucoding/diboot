package com.diboot.framework.service.impl;

import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.model.BaseAuthUser;
import com.diboot.framework.service.BaseAuthUserService;
import com.diboot.framework.service.mapper.BaseAuthUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
* 用户认证相关操作Service
* @author Mazc@dibo.ltd
* @version 2018-06-08
* Copyright © www.dibo.ltd
*/
@Service("baseAuthUserService")
public class BaseAuthUserServiceImpl extends BaseServiceImpl implements BaseAuthUserService {
	private static final Logger logger = LoggerFactory.getLogger(BaseAuthUserServiceImpl.class);
	
	@Autowired
	BaseAuthUserMapper baseAuthUserMapper;

	@Override
	public BaseAuthUser getModelByOpenid(String openid){
		return baseAuthUserMapper.getByOpenid(openid);
	}
	
	@Override
	protected BaseMapper getMapper() {
		return baseAuthUserMapper;
	}
}