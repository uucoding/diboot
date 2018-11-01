package com.diboot.framework.service.impl;

import com.diboot.framework.model.BaseMenu;
import com.diboot.framework.service.RoleMenuService;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.service.mapper.RoleMenuMapper;
import com.diboot.framework.utils.BeanUtils;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 菜单相关操作Service实现类
 * @author Mazc@dibo.ltd
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends BaseServiceImpl implements RoleMenuService {
	private static final Logger logger = LoggerFactory.getLogger(RoleMenuServiceImpl.class);
	
	@Autowired
	RoleMenuMapper roleMenuMapper;

	@Override
	protected BaseMapper getMapper() {
		return roleMenuMapper;
	}

	@Override
	public <T extends BaseMenu> List<T> getMenuListByRoleList(List<String> roleList) {
		return getMenuListByRoleList(roleList, null);
	}

	@Override
	public <T extends BaseMenu> List<T> getMenuListByRoleList(List<String> roleList, Map<String, Object> criteria) {
		if(V.isEmpty(roleList)){
			return null;
		}
		List menus = roleMenuMapper.getMenuListByRoleList(roleList, criteria);
		return BeanUtils.buildTreeModels(menus);
	}

	@Override
	public <T extends BaseMenu> List<T> getAllMenus(){
		List allMenus = roleMenuMapper.getAllMenus(new HashMap(4));
		// build成树形结构
		allMenus = BeanUtils.buildTreeModels(allMenus);
		return allMenus;
	}

	@Override
	public <T extends BaseMenu> List<T> getAllMenus(Map<String, Object> criteria){
		List allMenus = roleMenuMapper.getAllMenus(criteria);
		// build成树形结构
		allMenus = BeanUtils.buildTreeModels(allMenus);
		return allMenus;
	}

	@Override
	public boolean createMenu(BaseMenu menu) {
		return roleMenuMapper.createMenu(menu) > 0;
	}

	@Override
	public boolean updateMenu(BaseMenu menu) {
		return roleMenuMapper.updateMenu(menu) > 0;
	}

	@Override
	public void deleteRoleMenus(String role){
		roleMenuMapper.deleteRoleMenus(role);
	}

}