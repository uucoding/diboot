package com.diboot.framework.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
* 用户认证 Model对象定义
* @author Mazc@dibo.ltd
* @version 2018-06-08
* Copyright © www.dibo.ltd
*/
public class BaseAuthUser extends BaseUser{
	private static final long serialVersionUID = 200L;

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseUser.F{ public static final String
		userType = "userType",
		userId = "userId"
	;}

	/** 用户类型 */
    @NotNull(message = "用户类型不能为空！")
    @Length(max = 64, message = "用户类型长度超出了最大限制！")
    private String userType;

	/** 用户 */
    @NotNull(message = "用户不能为空！")
    private long userId;

    /** 关联用户对象 */
    private BaseUser userModel;

	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public BaseUser getUserModel() {
		return userModel;
	}

	public void setUserModel(BaseUser userModel) {
		this.userModel = userModel;
	}

	@Override
	public String getModelName(){
		return "用户认证";
	}
}
