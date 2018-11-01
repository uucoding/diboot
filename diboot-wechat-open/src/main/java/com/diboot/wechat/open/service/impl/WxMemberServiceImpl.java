package com.diboot.wechat.open.service.impl;

import com.diboot.framework.model.BaseAuthUser;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.service.BaseAuthUserService;
import com.diboot.framework.service.impl.BaseServiceImpl;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.wechat.open.model.WxMember;
import com.diboot.wechat.open.service.WxMemberService;
import com.diboot.wechat.open.service.mapper.WxMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***
* 服务号用户相关操作Service
* @author Yangz@dibo.ltd
* @version 2018-06-24
* Copyright © www.dibo.ltd
*/
@Service
public class WxMemberServiceImpl extends BaseServiceImpl implements WxMemberService{
	private static final Logger logger = LoggerFactory.getLogger(WxMemberServiceImpl.class);
	
	@Autowired
	WxMemberMapper wxMemberMapper;

	@Autowired
	BaseAuthUserService baseAuthUserService;

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public boolean createModel(BaseModel model) {
		WxMember wxMember = (WxMember) model;
		boolean success = wxMemberMapper.create(wxMember) > 0;
		if (success) {
			success = createOrModifyAuthUser(wxMember);
		}

		return success;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	public boolean updateModel(BaseModel model, String... fields) {
		WxMember wxMember = (WxMember) model;
		boolean success = super.updateModel(wxMember, fields);

		if (success){
			success = createOrModifyAuthUser(wxMember);
		}

		return success;
	}

	private boolean createOrModifyAuthUser(WxMember wxMember){
		BaseAuthUser authUser = baseAuthUserService.getModelByOpenid(wxMember.getOpenid());
		if (authUser == null){
			authUser = new BaseAuthUser();
			setAuthUserInfo(authUser, wxMember);
			boolean success = baseAuthUserService.createModel(authUser);
			return success;
		} else {
			setAuthUserInfo(authUser, wxMember);
			boolean success = baseAuthUserService.updateModel(authUser);
			return success;
		}
	}

	/**
	 * 设置authUser信息
	 * @param authUser
	 * @param wxMember
	 */
	private void setAuthUserInfo(BaseAuthUser authUser, WxMember wxMember){
		authUser.setOpenid(wxMember.getOpenid());
		authUser.setUserType(WxMember.class.getSimpleName());
		authUser.setUserId(wxMember.getId());
	}
	
	@Override
	protected BaseMapper getMapper() {
		return wxMemberMapper;
	}
}