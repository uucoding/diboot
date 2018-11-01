package com.diboot.wechat.open.service.impl;

import com.diboot.framework.service.impl.BaseServiceImpl;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.wechat.open.model.WxAuthOpen;
import com.diboot.wechat.open.service.WxAuthOpenService;
import com.diboot.wechat.open.service.mapper.WxAuthOpenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
* 服务号授权相关操作Service
* @author Yangz@dibo.ltd
* @version 2018-06-21
* Copyright © www.dibo.ltd
*/
@Service
public class WxAuthOpenServiceImpl extends BaseServiceImpl implements WxAuthOpenService {
	private static final Logger logger = LoggerFactory.getLogger(WxAuthOpenServiceImpl.class);
	
	@Autowired
	WxAuthOpenMapper wxAuthOpenMapper;

	@Override
	public WxAuthOpen getModelByAppId(String appid){
		return wxAuthOpenMapper.getModelByAppId(appid);
	}

	@Override
	public boolean createOrModify(WxAuthOpen model){
		WxAuthOpen oldModel = wxAuthOpenMapper.getModelByAppId(model.getAppid());
		if (oldModel == null){
			// 如果数据库中没有历史记录则新建
			return this.createModel(model);
		}

		// 否则更新历史记录
		oldModel.setAuthType(model.getAuthType());
		oldModel.setAuthorizerRefreshToken(model.getAuthorizerRefreshToken());
		oldModel.setEnabled(model.isEnabled());
		oldModel.setExtdata(model.getExtdata());

		return this.updateModel(oldModel);
	}
	
	@Override
	protected BaseMapper getMapper() {
		return wxAuthOpenMapper;
	}
}