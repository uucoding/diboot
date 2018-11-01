package com.diboot.framework.model;

import javax.validation.constraints.NotNull;

/***
 * RoleMenu-角色菜单权限
 * @author Mazc@dibo.ltd
 */
public class RoleMenu extends BaseModel{
	private static final long serialVersionUID = 1802L;

	@NotNull(message = "角色不能为空！")
    private String role ; // 角色
	
    @NotNull(message = "菜单不能为空！")
    private Long menuId ; // 菜单

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		role = "role",
		menuId = "menuId"
	;}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	@Override
	public String getModelName(){
		return "角色菜单";
	}
}