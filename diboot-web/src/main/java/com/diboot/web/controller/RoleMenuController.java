package com.diboot.web.controller;

import com.diboot.framework.model.BaseMenu;
import com.diboot.framework.model.Metadata;
import com.diboot.framework.model.RoleMenu;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.service.RoleMenuService;
import com.diboot.framework.utils.BeanUtils;
import com.diboot.framework.utils.V;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/***
 * 角色权限相关操作Controller
 * @author Mazc
 */
@Controller
@RequestMapping("/rolemenu")
public class RoleMenuController extends BaseCrudController {
	
	@Autowired
	private RoleMenuService roleMenuService;

	@Override
	protected String getViewPrefix() {
		return "rolemenu";
	}

	private static final String INDEX = "redirect:/rolemenu/";

	/***
	 * 根路径/
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/")
	public String root(HttpServletRequest request, ModelMap modelMap) throws Exception {		
		return list(request, modelMap);
	}
	
	/***
	 * 显示首页面
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/list")
	@Override
	public String list(HttpServletRequest request, ModelMap modelMap) throws Exception {		
		// 加载第一页
		return listPaging(1, request, modelMap);
	}
	
	/**
	 * 显示首页-分页
	 * @param pageIndex 分页
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/list/{pageIndex}")
	@Override
	public String listPaging(@PathVariable("pageIndex")int pageIndex, HttpServletRequest request, ModelMap modelMap) throws Exception{
		// 获取所有角色
		List<Map<String, Object>> roleMapList = metadataService.getKeyValuePairListByType(Metadata.TYPE.ROLE.name());
		for(Map<String, Object> roleMap : roleMapList){
			List<String> roles = new ArrayList<>(1);
			roles.add((String)roleMap.get("v"));
			List<BaseMenu> menus = roleMenuService.getMenuListByRoleList(roles);
			roleMap.put("menus", menus);
		}
		modelMap.put("roleMapList", roleMapList);

		return view(request, modelMap, "index");
	}
	
	/***
	 * 显示配置页面
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/config/{role}")
    public String config(@PathVariable("role")String role, HttpServletRequest request, ModelMap modelMap) throws Exception {
		// 获取所有角色
		List<Map<String, Object>> roleMapList = metadataService.getKeyValuePairListByType(Metadata.TYPE.ROLE.name());
		for(Map<String, Object> roleMap : roleMapList){
			if(role.equals(roleMap.get("v"))){
				modelMap.put("roleMap", roleMap);
				break;
			}
		}
		// 获取所有菜单
		List<BaseMenu> menus = roleMenuService.getAllMenus();
		Map menuListMap = BeanUtils.convert2KeyModelListMap(menus, BaseMenu.F.type);
		modelMap.addAttribute("menuListMap", menuListMap);
		
		// 加载当前页
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put(RoleMenu.F.role, role);
		List<RoleMenu> modelList = roleMenuService.getModelList(criteria);
		modelMap.addAttribute("modelList", modelList);
		
		return view(request, modelMap, "config");
    }
	
	/***
	 * 创建的后台处理
	 * @param role
	 * @param request
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/config/{role}")
    public String configAction(@PathVariable("role")String role, HttpServletRequest request, ModelMap modelMap) throws Exception {
        // 先清空
		roleMenuService.deleteRoleMenus(role);
		
		// 批量创建
		boolean success = true;
		String[] menuIdsArray = request.getParameterValues("menuId");
		if(menuIdsArray != null && menuIdsArray.length > 0){
			Set<String> menuIds = new HashSet<String>();
			List<RoleMenu> modelList = new ArrayList<RoleMenu>();
			for(String id : menuIdsArray){
				String[] arr = id.split("_");
				// 添加父菜单，子菜单
				if(!menuIds.contains(arr[0])){
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setRole(role);
					roleMenu.setMenuId(Long.parseLong(arr[0]));
					modelList.add(roleMenu);
					menuIds.add(arr[0]);
				}
				if(arr.length >1 && !menuIds.contains(arr[1])){
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setRole(role);
					roleMenu.setMenuId(Long.parseLong(arr[1]));
					modelList.add(roleMenu);
					menuIds.add(arr[1]);
				}
			}
			// 执行保存操作
			success = roleMenuService.batchCreateModels(modelList);
		}
        
        // 绑定执行结果
        String msg = success?"创建操作成功！":"创建操作失败！";
        addResultMsg(request, success, msg);

        return INDEX;
    }

	/***
	 *
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/role/create")
    public String createRole(HttpServletRequest request, ModelMap modelMap) throws Exception{
		String roleName = request.getParameter("name");
		String role = request.getParameter("role");
		if(V.isEmpty(roleName) || V.isEmpty(role)){
			// 绑定执行结果
			String msg ="创建角色失败: 角色名称和编码不能为空！";
			addResultMsg(request, false, msg);
			return INDEX;
		}
		//转大写
		role = role.toUpperCase();
		// 获取所有角色
		List<Metadata> roleList = metadataService.getChildrenByType(Metadata.TYPE.ROLE.name());
		for(Metadata metadata : roleList){
			if(role.equals(metadata.getItemValue())){
				String msg ="创建角色失败: 角色编码已存在，请重新输入！";
				addResultMsg(request, false, msg);
				return INDEX;
			}
		}
		// 创建新的metadata
		Metadata metadata = new Metadata();
		BeanUtils.copyProperties(roleList.get(roleList.size()-1), metadata);
		metadata.setItemName(roleName);
		metadata.setItemValue(role);
		metadata.setSystem(true);
		metadata.setEditable(true);
		metadata.setId(null);
		boolean success = metadataService.createModel(metadata);

		// 绑定执行结果
		String msg = success?"创建操作成功！":"创建操作失败！";
		addResultMsg(request, success, msg);
		return INDEX;
	}

	@Override
	protected BaseService getService() {
		return roleMenuService;
	}
}