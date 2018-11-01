package com.diboot.framework.controller;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.OperationLog;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 增删改查通用管理功能-父类
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
@Controller
public abstract class BaseCrudController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseCrudController.class);

	private static final String LATEST_LIST_PAGE = "latestListPage";

	/**
	 * 获取service实例
	 * @return
	 */
	protected abstract BaseService getService();

	/***
	 * 根路径/
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	protected String index(HttpServletRequest request, ModelMap modelMap) throws Exception {
		return list(request, modelMap);
	}

	/***
	 * 根路径/ home被替换为index方法
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@Deprecated
	protected String home(HttpServletRequest request, ModelMap modelMap) throws Exception {
		return list(request, modelMap);
	}
	
	/***
	 * 显示管理首页面
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	protected String list(HttpServletRequest request, ModelMap modelMap) throws Exception {		
		// 加载第一页
		int pageIndex = getInteger(request, PARAM_PAGE, 1);
		return listPaging(pageIndex, request, modelMap);
	}
	
	/**
	 * 显示首页-分页
	 * @param pageIndex 分页
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	protected String listPaging(int pageIndex, HttpServletRequest request, ModelMap modelMap) throws Exception{
		// 封装并自动生成
		Map<String, Object> criteria = super.buildQueryCriteria(request, pageIndex);
		// 是否已经存在指定条件，如存在则附加
		super.appendAdditionalCriteria(criteria, modelMap);
		super.bindCriteria(modelMap, criteria);
		
		// 获取记录总数，用于前端显示分页
		int totalCount = getService().getModelListCount(criteria);
		Integer pageSize = getCustomPageSize(request);

		String baseUrl = getUrlPrefix() + "/list";
		if(baseUrl.startsWith("/")){
			baseUrl = baseUrl.substring(1);
		}
		// 构建分页信息
		Map<String, Object> pagination = pageSize == null? super.buildPagination(baseUrl, criteria, totalCount, pageIndex)
				: super.buildPagination(baseUrl, criteria, totalCount, pageIndex, pageSize);
		modelMap.addAttribute("pagination", pagination);
		
		// 加载当前页
		if(modelMap.get(MODEL_LIST) == null){
			List<? extends BaseModel> modelList = pageSize == null? getService().getModelList(criteria, pageIndex) : getService().getModelList(criteria, pageIndex, pageSize);
			modelMap.addAttribute(MODEL_LIST, modelList);
		}
		// 附加更多属性
		attachMore(request, modelMap);

		// 记住当前列表页的查询参数，便于还原查询条件等
		HttpSession session = request.getSession(false);
		String latestListPage = request.getRequestURI();
		if (request.getQueryString() != null){
			latestListPage += "?" + request.getQueryString();
		}
		session.setAttribute(LATEST_LIST_PAGE, latestListPage);
		// 返回结果
		return view(request, modelMap, "list");
	}
	
	/***
	 * 显示查看详细页面
	 * @param id
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	protected String viewPage(Object id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		BaseModel model = (BaseModel) modelMap.get(MODEL);
		if(modelMap.get(MODEL) == null){
			model = getService().getModel(id);
			modelMap.put(MODEL, model);
		}
		attachMore4View(id, model, modelMap);

		//  记住view页面的返回地址
		HttpSession session = request.getSession(false);
		String latestListPage = String.valueOf(session.getAttribute(LATEST_LIST_PAGE));
		if (V.notEmpty(latestListPage) && latestListPage.contains(getUrlPrefix())){
			modelMap.addAttribute(LATEST_LIST_PAGE, latestListPage);
		}

		// 异步创建访问日志
		if(isEnabledAccessLog){
			asyncLogger.saveOperationLog(BaseHelper.getCurrentUser(), OperationLog.OPERATION.VIEW, model);
		}
		return view(request, modelMap, "view");
    }
	
	/***
	 * 显示创建页面
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	protected String createPage(HttpServletRequest request, ModelMap modelMap) throws Exception {
		// 附加更多属性
		attachMore(request, modelMap);
		return view(request, modelMap, "create");
    }
	
	/***
	 * 创建的后台处理
	 * @param model
	 * @param result
	 * @return view
	 * @throws Exception
	 */
	protected String create(BaseModel model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
        // Model属性值验证结果
		if(result != null && result.hasErrors()) {
			super.bindErrors(modelMap, result);
			modelMap.addAttribute(MODEL, model);
            return createPage(request, modelMap);
        }
    	// 保存之前
		beforeCreate(request, model, modelMap);
		if(modelMap.get("errors") != null){
			modelMap.addAttribute(MODEL, model);
			return createPage(request, modelMap);
		}
        // 默认设置createBy
		BaseUser user = BaseHelper.getCurrentUser();
        if(user != null){
        	model.setCreateBy((Long)user.getId());
        	model.setCreatorName(user.getRealname());
		}
        // 执行保存操作
        boolean success = getService().createModel(model);
        if(success){
        	afterCreated(request, model, modelMap);
			// 异步创建操作日志
			if(isEnabledOperationLog){
				asyncLogger.saveOperationLog(user, OperationLog.OPERATION.CREATE, model);
			}
        }
        else{
        	logger.warn("创建操作未成功，model="+model.getClass().getSimpleName());
        }
        // 绑定执行结果
        String msg = success?"创建操作成功！":"创建操作失败！";
        if(modelMap.get("promptMsg") != null){
			msg += modelMap.get("promptMsg");
		}
        super.addResultMsg(request, success, msg);
        
        // 继续创建
        if(success && super.isContinue(request)){
        	return createPage(request, modelMap);
        }// 指定跳转url
        else if(modelMap.get("redirect") != null){
    		return redirectTo(modelMap.get("redirect"));
        }
        else{// 默认返回查看页面
        	return redirectTo(getUrlPrefix()+"/view/"+model.getPk());
        }
    }
    
	/***
	 * 显示更新页面
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	protected String updatePage(Object id, HttpServletRequest request, ModelMap modelMap) throws Exception {
    	if(modelMap.get(MODEL) == null){
            BaseModel model = getService().getModel(id);
    		modelMap.put(MODEL, model);
		}
		// 附加更多属性
		attachMore(request, modelMap);
		return view(request, modelMap, "update");
    }
	
	/***
	 * 更新的后台处理
	 * @param model
	 * @param result
	 * @return view
	 * @throws Exception
	 */
	protected String update(Object id, BaseModel model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception{
		// Model属性值验证结果
		if(result != null && result.hasErrors()) {
			super.bindErrors(modelMap, result);
			modelMap.addAttribute(MODEL, model);
            return updatePage(id, request, modelMap);  
        }
        // 更新之前的操作
		beforeUpdate(request, model, modelMap);
		if(modelMap.get("errors") != null){
			return updatePage(id, request, modelMap);
		}
		// 设置更新人
		BaseUser user = BaseHelper.getCurrentUser();
		// 执行保存操作
		boolean success = getService().updateModel(model);
        if(success){
        	afterUpdated(request, model, modelMap);
			// 异步创建操作日志
			if(isEnabledOperationLog){
				asyncLogger.saveOperationLog(user, OperationLog.OPERATION.UPDATE, model);
			}
        }
        else{
        	logger.warn("更新操作未成功，model="+model.getClass().getSimpleName()+", id="+id);
        }
        // 绑定执行结果
        String msg = success?"更新操作成功！":"更新操作失败！";
		if(modelMap.get("promptMsg") != null){
			msg += modelMap.get("promptMsg");
		}
        super.addResultMsg(request, success, msg);
        
        // 默认跳转到查看页面
        if(modelMap.get("redirect") != null){// 指定跳转url
        	return redirectTo(modelMap.get("redirect"));
        }
        else{
        	return redirectTo(getUrlPrefix()+"/view/"+id);
        }
	}
	
	/***
	 * 删除的后台处理
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> delete(Object id, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>(8);
		if(id == null) {
			map.put("error", "请选择需要删除的条目！");
	        // 绑定执行结果
	        return map;
        }
		// 是否有权限删除
		BaseModel model = getService().getModel(id);
		// 执行删除操作
		boolean success = false;
		String error = beforeDelete(model);
		if(error == null){
			success = getService().deleteModel(id);
			if(success){
				map.put("success", success);
				// 执行删除后的动作
				afterDeleted(request, model);
				// 异步创建操作日志
				if(isEnabledOperationLog){
					BaseUser user = BaseHelper.getCurrentUser();
					asyncLogger.saveOperationLog(user, OperationLog.OPERATION.DELETE, model);
				}
				logger.info("删除操作成功，model="+model.getClass().getSimpleName()+", id="+id);
			}
			else{
				map.put("error", "删除操作失败！");
				logger.warn("删除操作未成功，model="+model.getClass().getSimpleName()+", id="+id);
			}
		}
		else{
			map.put("error", "删除操作失败: "+error);
		}
		// 返回json
        return map;
	}

	/***
	 * 获取跳转URL（附带URL查询参数）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected String getRedirectUrlWithQueryParams(HttpServletRequest request, String... targetUrl) throws Exception{
		String url = getUrlPrefix()+"/list/";
		if(targetUrl != null && targetUrl.length > 0){
			url = targetUrl[0];
		}
		StringBuilder sb = new StringBuilder(REDIRECT_TO).append("/").append(url);
		String queryParams = super.getString(request, "queryParams");
		if(V.notEmpty(queryParams)){
			sb.append("?");
			queryParams = queryParams.replace("?", "");
			String[] params = queryParams.split("&");
			for(int i = 0; i<params.length; i++){
				String p = params[i];
				String[] pv = p.split("=");
				if(i > 0){
					sb.append("&");
				}
				sb.append(pv[0]).append("=");
				if(pv.length > 1){
					sb.append(URLEncoder.encode(pv[1], "utf-8"));
				}
			}
		}
		return sb.toString();
	}

	/***
	 * 获取当前自定义的每页数目
	 * @param request
	 * @return
	 */
	protected Integer getCustomPageSize(HttpServletRequest request){
		Integer pageSize = null;
		// 构建分页信息
		if(V.notEmpty(request.getParameter(PARAM_PAGESIZE))){
			pageSize = Integer.parseInt(request.getParameter(PARAM_PAGESIZE));
			super.addIntoSession(request, PARAM_PAGESIZE, pageSize);
		}
		else if(getFromSession(request, PARAM_PAGESIZE) != null){
			pageSize = (Integer)getFromSession(request, PARAM_PAGESIZE);
		}
		return pageSize;
	}

	//============= 供子类继承重写的方法 =================
	/***
	 * 是否有删除权限，如不可删除返回无权
	 * @param model
	 * @return
	 */
	protected String beforeDelete(BaseModel model){
		return "您无权删除该数据！";
	}

	/***
	 * 附加更多属性
	 * @param map
	 * @throws Exception
	 */
	protected void attachMore(HttpServletRequest request, ModelMap map) throws Exception{
	}

	/***
	 * 附加更多属性: view查看详细页面
	 * @param id
	 * @param model
	 * @param modelMap
	 * @throws Exception
	 */
	protected void attachMore4View(Object id, BaseModel model, ModelMap modelMap) throws Exception {
	}

	/***
	 * 保存之前的逻辑处理
	 * @param request
	 * @param model
	 * @param modelMap
	 * @throws Exception
	 */
	protected void beforeCreate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
	}
	
	/***
	 * 保存之后的逻辑处理
	 * @param request
	 * @param model
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	protected String afterCreated(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
		return null;
	}
	
	/***
	 * 更新之前的逻辑处理
	 * @param request
	 * @param model
	 * @param modelMap
	 * @throws Exception
	 */
	protected void beforeUpdate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
	}
	
	/***
	 * 更新后的逻辑处理
	 * @param request
	 * @param model
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	protected String afterUpdated(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
		return null;		
	}
	
	/***
	 * 删除后的逻辑处理
	 * @param request
	 * @param model
	 */
	protected void afterDeleted(HttpServletRequest request, BaseModel model) {
	}
}
