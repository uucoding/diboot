package com.diboot.web.controller;

import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseOrg;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.service.BaseService;
import com.diboot.common.model.Organization;
import com.diboot.common.service.OrganizationService;
import com.diboot.web.utils.AppHelper;
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
 * 单位相关操作Controller
 * @author Mazc@dibo.ltd
 * @version 2017-05-14
 * Copyright @ www.dibo.ltd
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseCrudController {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
	private OrganizationService organizationService;

	@Override
	protected String getViewPrefix() {
		return "organization";
	}
	
	/***
	 * 根路径/
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/")
	@Override
	public String index(HttpServletRequest request, ModelMap modelMap) throws Exception {
		return list(request, modelMap);
	}
	
	/***
	 * 显示首页面
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
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/list/{pageIndex}")
	@Override
	public String listPaging(@PathVariable("pageIndex")int pageIndex, HttpServletRequest request, ModelMap modelMap) throws Exception{
		return super.listPaging(pageIndex, request, modelMap);
	}

	/***
	 * 显示查看详细页面
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/view/{id}")
	@Override
    public String viewPage(@PathVariable("id")Object id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		String viewPage = super.viewPage(id, request, modelMap);
		if("body".equalsIgnoreCase(request.getParameter("load"))){
			return super.view(request, modelMap, "_viewBody");
		}
		return viewPage;
    }
	
	/***
	 * 显示创建页面
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
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/create")
    public String create(@Valid BaseOrg model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
        return super.create(model, result, request, modelMap);
    }
	
	/***
	 * 显示更新页面
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/update/{id}")
	@Override
    public String updatePage(@PathVariable("id")Object id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		return super.updatePage(id, request, modelMap);
    }
	
	/***
	 * 更新的后台处理
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id")Object id, @Valid BaseOrg model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception{
		return super.update(id, model, result, request, modelMap);
	}
	
	/***
	 * 删除的后台处理
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete/{id}")
	@ResponseBody
	@Override
	public Map<String, Object> delete(@PathVariable("id")Object id, HttpServletRequest request) throws Exception{
		return super.delete(id, request);
	}

	@Override
	protected String beforeDelete(BaseModel model) {
		BaseUser currentUser = AppHelper.getCurrentUser();
    	boolean canDelete = (currentUser != null && (currentUser.isAdmin() || currentUser.getRealname().equals(model.getCreateBy())));
    	return canDelete? null : Status.FAIL_NO_PERMISSION.label();
	}

	@Override
	protected void attachMore(HttpServletRequest request, ModelMap modelMap) throws Exception{
		// 添加parentId下拉选项
		List<Map<String, Object>> options = organizationService.getKeyValuePairList(null, Organization.F.id, Organization.F.shortName);
		modelMap.put("options", options);
	}

	@Override
	protected BaseService getService() {
		return organizationService;
	}	
}