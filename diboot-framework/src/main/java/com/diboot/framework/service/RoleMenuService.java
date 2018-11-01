package com.diboot.framework.service;

import com.diboot.framework.model.BaseMenu;

import java.util.List;
import java.util.Map;

/***
 * 菜单相关操作Service
 * @author Mazc@dibo.ltd
 */
public interface RoleMenuService extends BaseService{

	/***
	 * 获取所有菜单
	 * @return
	 * @throws Exception
	 */
	<T extends BaseMenu> List<T> getAllMenus();

	/***
	 * 获取所有指定类型的菜单
	 * @return
	 * @throws Exception
	 */
	<T extends BaseMenu> List<T> getAllMenus(Map<String, Object> criteria);

	/**
	 * 获取某角色可访问的菜单列表
	 * @return
	 * @throws Exception
	 */
	<T extends BaseMenu> List<T> getMenuListByRoleList(List<String> roleList);

	/**
	 * 获取某角色可访问的菜单列表
	 * @return
	 * @throws Exception
	 */
	<T extends BaseMenu> List<T> getMenuListByRoleList(List<String> roleList, Map<String, Object> criteria);

	/***
	 * 创建菜单
	 * @param menu
	 * @return
	 */
	boolean createMenu(BaseMenu menu);

	/***
	 * 更新菜单
	 * @param menu
	 * @return
	 */
	boolean updateMenu(BaseMenu menu);

	/***
	 * 删除角色的菜单权限
	 * @param role
	 * @throws Exception
	 */
	void deleteRoleMenus(String role);

}