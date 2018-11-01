package com.diboot.web.cache;

import com.diboot.framework.model.BaseMenu;
import com.diboot.framework.model.BaseTreeModel;
import com.diboot.framework.service.RoleMenuService;
import com.diboot.framework.utils.ContextHelper;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Dibo 常用数据缓存 
 * @author Mazc@dibo.ltd
 * @version 2016年12月7日
 * Copyright @ www.dibo.ltd
 */
@Component
public class AppCache{
	private static final Logger logger = LoggerFactory.getLogger(AppCache.class);

	// 数据库菜单
	private static List<BaseMenu> allMenus = new ArrayList<>();

	// 所有菜单链接URL
	private static List<String> allMenuLinks = new ArrayList<>();

	/***
	 * 初始化元数据
	 */
	public static void init() {
		if(allMenus.isEmpty()){
			// 查询所有数据库中的菜单
			loadAllDbMenus();
		}
	}

	/***
	 * 加载数据库中的所有菜单
	 */
	private static void loadAllDbMenus() {
		try{
			RoleMenuService roleMenuService = (RoleMenuService) ContextHelper.getBean("roleMenuService");
			if(roleMenuService != null){
				Map<String, Object> criteria = new HashMap<>();
				criteria.put(BaseMenu.F.type, BaseMenu.TYPE.PC.name());
				criteria.put(BaseMenu.F.application, BaseMenu.APPLIACTION.MS.name());
				allMenus = roleMenuService.getAllMenus(criteria);
				// 缓存所有菜单链接
				cacheMenuLinks(allMenus);
			}
		}
		catch (Exception e){
			logger.warn("加载数据库菜单异常: " + e.getMessage());
		}
	}

	/***
	 * 缓存菜单链接
	 * @param menus
	 */
	private static void cacheMenuLinks(List<BaseMenu> menus){
		if(V.isEmpty(menus)){
			return;
		}
		// 缓存所有菜单链接
		for(BaseMenu menu : menus){
			if(V.notEmpty(menu.getLink()) && !allMenuLinks.contains(menu.getLink())){
				allMenuLinks.add(menu.getLink());
			}
			if(V.notEmpty(menu.getChildren())){
				for(BaseTreeModel child : menu.getChildren()){
					BaseMenu childMenu = (BaseMenu) child;
					if(V.notEmpty(childMenu.getLink()) && !allMenuLinks.contains(childMenu.getLink())){
						allMenuLinks.add(childMenu.getLink());
					}
				}
			}
		}
	}

	/***
	 * 获取所有菜单，含数据库菜单及插件菜单
	 * @return
	 */
	public static List<BaseMenu> getAllMenus(){
		init();
		// 此处过滤掉已安装菜单
		return allMenus;
	}

	/***
	 * 获取菜单链接
	 * @return
	 */
	public static List<String> getAllMenuLinks(){
		init();
		return allMenuLinks;
	}

}