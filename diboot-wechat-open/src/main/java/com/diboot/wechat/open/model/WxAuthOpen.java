package com.diboot.wechat.open.model;

import com.diboot.framework.model.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
* 服务号授权 Model对象定义
* @author Yangz@dibo.ltd
* @version 2018-06-21
* Copyright © www.dibo.ltd
*/
public class WxAuthOpen extends com.diboot.framework.model.BaseModel{
	private static final long serialVersionUID = -3063345465794911481L;


	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		relObjType = "relObjType",
		relObjId = "relObjId",
		authType = "authType",
		appid = "appid",
		nickname = "nickname",
		principalName = "principalName",
		headImg = "headImg",
		originalId = "originalId",
		serviceTypeInfo = "serviceTypeInfo",
		verifyTypeInfo = "verifyTypeInfo",
		qrcodeUrl = "qrcodeUrl",
		wechatAlias = "wechatAlias",
		authorizerRefreshToken = "authorizerRefreshToken",
		enabled = "enabled"
	;}

	/** 关联类型 */
    @Length(max = 100, message = "关联类型长度超出了最大限制！")
    private String relObjType;

	/** 关联数据 */
    private Long relObjId;

	/** 授权类型 */
    private Integer authType = 1;

	/** appId */
    @NotNull(message = "appId不能为空！")
    @Length(max = 50, message = "appId长度超出了最大限制！")
    private String appid;

	/** 名称 */
    private String nickname;

	/** 主体名称 */
    private String principalName;

	/** 头像 */
    private String headImg;

	/** 原始ID */
    private String originalId;

	/** 账号类型 */
    private Integer serviceTypeInfo;

	/** 认证类型 */
    private Integer verifyTypeInfo;

	/** 二维码 */
    private String qrcodeUrl;

	/** 所设置公众号 */
    private String wechatAlias;

    private boolean enabled = true;

	/** 刷新token */
    @Length(max = 64, message = "刷新token长度超出了最大限制！")
    private String authorizerRefreshToken;


	public String getRelObjType() {
		return relObjType;
	}
	public void setRelObjType(String relObjType) {
		this.relObjType = relObjType;
	}

	public Long getRelObjId() {
		return relObjId;
	}
	public void setRelObjId(Long relObjId) {
		this.relObjId = relObjId;
	}

	public Integer getAuthType() {
		return authType;
	}
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public Integer getServiceTypeInfo() {
		return serviceTypeInfo;
	}

	public void setServiceTypeInfo(Integer serviceTypeInfo) {
		this.serviceTypeInfo = serviceTypeInfo;
	}

	public Integer getVerifyTypeInfo() {
		return verifyTypeInfo;
	}

	public void setVerifyTypeInfo(Integer verifyTypeInfo) {
		this.verifyTypeInfo = verifyTypeInfo;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public String getWechatAlias() {
		return wechatAlias;
	}

	public void setWechatAlias(String wechatAlias) {
		this.wechatAlias = wechatAlias;
	}

	public String getAuthorizerRefreshToken() {
		return authorizerRefreshToken;
	}

	public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
		this.authorizerRefreshToken = authorizerRefreshToken;
	}

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
	public String getModelName(){
		return "服务号授权";
	}
}
