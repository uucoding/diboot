package com.diboot.wechat.open.model;

import com.diboot.framework.model.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
* 服务号用户 Model对象定义
* @author Yangz@dibo.ltd
* @version 2018-06-24
* Copyright © www.dibo.ltd
*/
public class WxMember extends com.diboot.framework.model.BaseUser{
	private static final long serialVersionUID = -5877139861324834877L;

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		openid = "openid",
		nickname = "nickname",
		avatar = "avatar",
		gender = "gender",
		language = "language",
		country = "country",
		province = "province",
		city = "city",
		subscribeTime = "subscribeTime",
		remark = "remark",
		groupId = "groupId",
		tagIds = "tagIds",
		subscribeScene = "subscribeScene",
		qrScene = "qrScene",
		qrSceneStr = "qrSceneStr",
		orgId = "orgId",
		wxAuthOpenId = "wxAuthOpenId",
		organizationShortName = "organizationShortName",
		wxAuthOpenAppid = "wxAuthOpenAppid",
		unionid = "unionid"
	;}

	/** openId */
    @NotNull(message = "openId不能为空！")
    @Length(max = 32, message = "openId长度超出了最大限制！")
    private String openid;

	/** 昵称 */
    @Length(max = 100, message = "昵称长度超出了最大限制！")
    private String nickname;

	/** 头像 */
    @Length(max = 255, message = "头像长度超出了最大限制！")
    private String avatar;

	/** 性别 */
    @Length(max = 1, message = "性别长度超出了最大限制！")
    private String gender;

	/** 语言 */
    @Length(max = 10, message = "语言长度超出了最大限制！")
    private String language;

	/** 国家 */
    @Length(max = 100, message = "国家长度超出了最大限制！")
    private String country;

	/** 省份 */
    @Length(max = 100, message = "省份长度超出了最大限制！")
    private String province;

	/** 城市 */
    @Length(max = 100, message = "城市长度超出了最大限制！")
    private String city;

	/** 关注时间 */
    @NotNull(message = "关注时间不能为空！")
    private Date subscribeTime = null;

	/** remark */
    @Length(max = 255, message = "remark长度超出了最大限制！")
    private String remark;

	/** 分组 */
    private Integer groupId;

	/** 标签 */
    @Length(max = 255, message = "标签长度超出了最大限制！")
    private String tagIds;

	/** 关注方式 */
    @Length(max = 50, message = "关注方式长度超出了最大限制！")
    private String subscribeScene;

	/** 二维码扫码场景 */
    @Length(max = 100, message = "二维码扫码场景长度超出了最大限制！")
    private String qrScene;

	/** 二维码扫码场景描述 */
    @Length(max = 255, message = "二维码扫码场景描述长度超出了最大限制！")
    private String qrSceneStr;


	/** 单位 */
	private Long orgId = null;
	
	/** 微信认证 */
	private Long wxAuthOpenId = null;
	
	// 关联字段 organization.short_name
	private String organizationShortName;
	// 关联字段 wx_auth_open.appid
	private String wxAuthOpenAppid;
	/** unionId */
	@Length(max = 32, message = "unionId长度超出了最大限制！")
	private String unionid = null;
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getTagIds() {
		return tagIds;
	}
	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getSubscribeScene() {
		return subscribeScene;
	}
	public void setSubscribeScene(String subscribeScene) {
		this.subscribeScene = subscribeScene;
	}

	public String getQrScene() {
		return qrScene;
	}
	public void setQrScene(String qrScene) {
		this.qrScene = qrScene;
	}

	public String getQrSceneStr() {
		return qrSceneStr;
	}
	public void setQrSceneStr(String qrSceneStr) {
		this.qrSceneStr = qrSceneStr;
	}

	public Long getOrgId(){
		return orgId;
	}
	public void setOrgId(Long orgId){
		this.orgId = orgId;
	}
	public Long getWxAuthOpenId(){
		return wxAuthOpenId;
	}
	public void setWxAuthOpenId(Long wxAuthOpenId){
		this.wxAuthOpenId = wxAuthOpenId;
	}
	public String getOrganizationShortName(){
		return this.organizationShortName;
	}
	public void setOrganizationShortName(String organizationShortName){
		this.organizationShortName = organizationShortName;
	}
	public String getWxAuthOpenAppid(){
		return this.wxAuthOpenAppid;
	}
	public void setWxAuthOpenAppid(String wxAuthOpenAppid){
		this.wxAuthOpenAppid = wxAuthOpenAppid;
	}
	public String getUnionid(){
		return unionid;
	}
	public void setUnionid(String unionid){
		this.unionid = unionid;
	}
	@Override
	public String getModelName(){
		return "服务号用户";
	}
}
