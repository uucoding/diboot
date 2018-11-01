package com.diboot.wechat.open.service.impl;

import com.diboot.framework.service.impl.BaseServiceImpl;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.wechat.open.model.WxConfigStorage;
import com.diboot.wechat.open.service.WxConfigStorageService;
import com.diboot.wechat.open.service.mapper.WxConfigStorageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
* 微信参数相关操作Service
* @author Mazc@dibo.ltd
* @version 2018-06-21
* Copyright © www.dibo.ltd
*/
@Service
public class WxConfigStorageServiceImpl extends BaseServiceImpl implements WxConfigStorageService{
	private static final Logger logger = LoggerFactory.getLogger(WxConfigStorageServiceImpl.class);
	
	@Autowired
	WxConfigStorageMapper wxConfigStorageMapper;

	@Override
	public WxConfigStorage getModelByType(String type){
		return wxConfigStorageMapper.getModelByType(type);
	}

	@Override
	public boolean createOrModify(WxConfigStorage model){
		WxConfigStorage oldModel = this.getModelByType(model.getType());
		if (oldModel == null){
			return this.createModel(model);
		} else {
			oldModel.setExtdata(model.getExtdata());
			return this.updateModel(oldModel);
		}
	}

	@Override
	public String getOpenComponentVerifyTicket(){
		WxConfigStorage model = this.getModelByType(WxConfigStorage.TYPE.WX_OPEN.name());
		if (model == null){
			return null;
		}
		String ticket = String.valueOf(model.getFromJson(WxConfigStorage.FIELDS.component_verify_ticket.name()));
		return ticket;
	}
	
	@Override
	protected BaseMapper getMapper() {
		return wxConfigStorageMapper;
	}
}