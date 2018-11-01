package com.diboot.web.controller;

import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.model.SystemConfig;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.service.SystemConfigService;
import com.diboot.web.utils.AppHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/***
 * 系统配置相关操作Controller
 * @author Mazc@dibo.ltd
 * @version 2017-05-14
 * Copyright @ www.dibo.ltd
 */
@Controller
@RequestMapping("/systemConfig")
public class SystemConfigController extends BaseCrudController {
	private static final Logger logger = LogManager.getLogger(SystemConfigController.class);

	@Autowired
	private SystemConfigService systemConfigService;

	@Override
	protected String getViewPrefix() {
		return "systemConfig";
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
    public String viewPage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		String viewPage = super.viewPage(id, request, modelMap);
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
    public String create(@Valid SystemConfig model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
        return super.create(model, result, request, modelMap);
    }

	/***
	 * 显示更新页面
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		return super.updatePage(id, request, modelMap);
    }

	/***
	 * 显示查看详细
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/load/{id}")
	@ResponseBody
	public JsonResult loadDetail(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		SystemConfig model = systemConfigService.getModel(id);
		return new JsonResult(model);
	}

	/***
	 * 更新的后台处理
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/save")
	@ResponseBody
	public JsonResult saveConfig(@ModelAttribute SystemConfig model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception{
        model.setCreateBy(AppHelper.getCurrentUserId());
	    boolean success = systemConfigService.createOrUpdateModel(model);
        if(success){
        	Map data = new HashMap();
        	data.put(SystemConfig.F.id, model.getId());
            return new JsonResult(Status.OK, data);
        }
        else{
            return new JsonResult(Status.FAIL_OPERATION);
        }
	}

	/***
	 * 删除的后台处理
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete/{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id")Long id, HttpServletRequest request) throws Exception{
		return super.delete(id, request);
	}

	@Override
	protected void beforeCreate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
	}

	@Override
	protected void beforeUpdate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
	}

	@Override
	protected String beforeDelete(BaseModel model) {
		BaseUser currentUser = AppHelper.getCurrentUser();
    	boolean canDelete = (currentUser != null && (currentUser.isAdmin() || currentUser.getRealname().equals(model.getCreateBy())));
    	return canDelete? null : Status.FAIL_NO_PERMISSION.label();
	}

	@Override
	protected void attachMore4View(Object id, BaseModel model, ModelMap modelMap) throws Exception {
	}

	@Override
	protected void attachMore(HttpServletRequest request, ModelMap modelMap) throws Exception{
	}

	@Override
	protected BaseService getService() {
		return systemConfigService;
	}	
}