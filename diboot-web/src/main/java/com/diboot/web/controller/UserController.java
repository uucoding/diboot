package com.diboot.web.controller;

import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.Metadata;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import com.diboot.common.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/***
 * 用户相关操作Controller
 * @author Mazc
 * @version 2016-12-09
 * Copyright @ www.dibo.ltd
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseCrudController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
    @Autowired
	private UserService userService;

	@Override
	protected String getViewPrefix() {
		return "user";
	}
	
	/***
	 * 默认路径/
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
		Map criteria = super.buildQueryCriteria(request, pageIndex);
		super.appendAdditionalCriteria(criteria, modelMap);
		Integer pageSize = super.getCustomPageSize(request);
		List<? extends BaseModel> modelList = pageSize == null? userService.getUserListWithRoles(criteria, pageIndex) : userService.getUserListWithRoles(criteria, pageIndex, pageSize);
		modelMap.addAttribute("modelList", modelList);

		return super.listPaging(pageIndex, request, modelMap);
	}
	
	/***
	 * 显示查看详细页面
	 * @param id
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/view/{id}")
    public String viewPage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		// 获取用户及角色
		BaseUser user = userService.getUserWithRoles(id);
		modelMap.addAttribute("model", user);
		return super.viewPage(id, request, modelMap);
    }
	
	/***
	 * 显示创建页面
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/create")
	@Override
    public String createPage(HttpServletRequest request, ModelMap modelMap) throws Exception {
		return super.createPage(request, modelMap);
    }
	
	/***
	 * 创建的后台处理
	 * @param model
	 * @param result
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/create")
    public String create(@Valid BaseUser model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
	    return super.create(model, result, request, modelMap);
    }
	
	/***
	 * 显示更新页面
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		// 获取用户及角色
		BaseUser user = userService.getUserWithRoles(id);
		modelMap.addAttribute("model", user);

		return super.updatePage(id, request, modelMap);
    }
	
	/***
	 * 更新的后台处理
	 * @param model
	 * @param result
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id")Long id, @Valid BaseUser model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception{
		return super.update(id, model, result, request, modelMap);
	}
	
	/***
	 * 删除的后台处理
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete/{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id")Long id, HttpServletRequest request) throws Exception{
		return super.delete(id, request);
	}

	@Override
	protected String beforeDelete(BaseModel model) {
		BaseUser user = BaseHelper.getCurrentUser();
		boolean canDelete = user.isAdmin() && !model.getId().equals(user.getId());
		return canDelete? null : Status.FAIL_NO_PERMISSION.label();
	}

	/***
	 * 附加更多信息
	 * @param request
	 * @param modelMap
	 * @throws Exception
	 */
	@Override
	protected void attachMore(HttpServletRequest request, ModelMap modelMap) throws Exception {
		List<Metadata> roleList = metadataService.getChildrenByType(Metadata.TYPE.ROLE.name());
		modelMap.addAttribute("roles", roleList);
	}

    /**
     * 新建/更新之前的校验
     */
    @Override
    protected void beforeCreate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
        BaseUser user = (BaseUser)model;
        // 检查角色是否为空
    	String[] roleList = request.getParameterValues("roleList");
        if(V.isEmpty(roleList)){
            super.bindError(modelMap, "用户角色不能为空，请选择！");
            return;
        }
        // 设置角色
        user.setRoles(S.join(roleList));
        // 检查用户名是否重复
        BaseUser dbusr = userService.getUserByUsername(user.getUsername());
        if(dbusr != null){
            if(model.isNew() || !(dbusr.getId().equals(user.getId()))){
                super.bindError(modelMap, "该用户名已存在，请重新输入！");
                return;
            }
        }
    }

    @Override
    protected void beforeUpdate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
        beforeCreate(request, model, modelMap);
    }

    @Override
    protected String update(Object id, BaseModel model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
        return super.update(id, model, result, request, modelMap);
    }

    @Override
	protected BaseService getService() {
		return userService;
	}	
}