package com.diboot.framework.model;

import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;

/***
 * Dibo 用户账号抽象类
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
public class BaseUser extends BaseModel{
	private static final long serialVersionUID = 206L;

	/** 角色字段的关联元数据 */
	public static final String METATYPE_ROLE = "ROLE";
	/** 职位字段的关联元数据 */
	public static final String METATYPE_POSITION = "POSITION";
	/** 性别字段的关联元数据 */
	public static final String METATYPE_GENDER = "GENDER";

	/***
	 * 系统角色定义
	 */
	public static enum ROLE{
		ADMIN
	}

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseTreeModel.F{ public static final String
			orgId = "orgId",
			departmentId = "departmentId",
			username = "username",
			password = "password",
			realname = "realname",
			roles = "roles",
			position = "position",
			gender = "gender",
			phone = "phone",
			telephone = "telephone",
			wechat = "wechat",
			openid = "openid",
			email = "email",
			enabled = "enabled",
			salt = "salt",
			accessToken = "accessToken",
			expiredTime = "expiredTime",
			failCount = "failCount"
	;}

    @NotNull(message = "单位不能为空！")
	private Long orgId = 0L; // 单位id

	private Long departmentId = 0L; // 部门id

    @Length(max = 50, message = "用户名长度超出了最大限制！")  
    private String username; // 用户名

    @Length(max = 50, message = "密码长度超出了最大限制！")  
    private String password; // 密码
	
    @Length(max = 255, message = "姓名长度超出了最大限制！")  
    private String realname; // 姓名

	private String gender; // 性别

	/** 职位 */
    private String position;

    private String avatar; // 头像
    
    private String wechat; //微信号

	private String openid; //微信openid

    @Email(message="email格式有误，请检查！")
    private String email;
    
	@Length(max = 20, message = "手机号码长度超出了最大限制！")  
    private String phone; // 手机号码

	/** 座机号码 */
	private String telephone;

    @NotNull(message = "状态不能为空！")
    private boolean enabled = true; // 状态

	private String salt; // 加密盐

	private String accessToken; //请求token

    private Date expiredTime; // 过期时间

	private int failCount = 0; // 登录失败次数

    private List<? extends BaseMenu> menus; //用户可访问菜单

	private String roles; //用户角色

	private String roleNames; //角色名称

	private Set<String> accessiableUrls;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		if(StringUtils.isBlank(avatar)){
			return BaseConfig.getDefaultAvatar();
		}
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/***
	 * 是否过期
	 * @return
	 */
	public boolean isExpired(){
		if(this.expiredTime != null){
			return this.expiredTime.before(new Date());
		}
		return false;
	}

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Deprecated
	public String getAccessToken() {
		return accessToken;
	}

	@Deprecated
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRoles() {
		return roles;
	}
	/***
     * 设置角色
     * @param roles
     */
    public void setRoles(String roles) {
        this.roles = roles;
    }

	/***
	 * 获取角色信息
	 * @return
	 */
	public List<String> getRoleList(){
		if(roles != null){
			return Arrays.asList(roles.split(","));
		}
		return null;
	}

	/***
	 * 获取角色信息
	 * @return
	 */
	public void setRoleList(List<String> roleList){
		if(roleList != null){
			setRoles(S.join(roleList));
		}
	}

	/**
	 * 角色名称
	 * @return
	 */
	public String getRoleNames(){
		return this.roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public List<? extends BaseMenu> getMenus() {
		return menus;
	}
	
	public void setMenus(List<? extends BaseMenu> menus) {
		this.menus = menus;
		if(V.isEmpty(menus)){
			return;
		}
		// 重新组装菜单
		accessiableUrls = new HashSet<>();
		for(BaseMenu m : menus){
			if(StringUtils.isNotBlank(m.getLink())){
				accessiableUrls.add(m.getLink());
			}
			if(m.getChildren() != null){
				for(BaseMenu cm : m.getChildren()){
					if(StringUtils.isNotBlank(cm.getLink())){
						accessiableUrls.add(cm.getLink());
					}
				}
			}
		}
	}
	
	/***
	 * 是否有权限访问
	 * @param uri
	 * @return
	 */
	public boolean canAccessUrl(String uri){
	    if(V.notEmpty(accessiableUrls)){
            for(String path : accessiableUrls){
                if(uri.startsWith(path)){
                    return true;
                }
            }
        }
		return false;
	}

    /***
     * 是否是管理员
     * @return
     */
    public boolean isAdmin(){
        if(V.notEmpty(getRoleList())){
            return getRoleList().contains(ROLE.ADMIN.name());
        }
        return false;
    }

	@Override
	public String getModelName(){
		return "用户";
	}
}