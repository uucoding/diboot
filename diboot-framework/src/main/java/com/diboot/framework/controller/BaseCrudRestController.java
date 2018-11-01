package com.diboot.framework.controller;

import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.model.OperationLog;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.utils.BaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 增删改查通用管理功能-父类
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
@RestController
public abstract class BaseCrudRestController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseCrudRestController.class);

	/**
	 * 获取service实例
	 * @return
	 */
	protected abstract BaseService getService();

	/***
	 * 获取某资源的集合
	 * @param request
	 * @param modelMap
	 * @return JsonResult
	 * @throws Exception
	 */
	protected JsonResult getModelList(HttpServletRequest request, ModelMap modelMap) throws Exception {
		// 加载第一页
		int pageIndex = getInteger(request, PARAM_PAGE, 1);
		// 封装并自动生成
		Map<String, Object> criteria = super.buildQueryCriteria(request, pageIndex);
		// 是否已经存在指定条件，如存在则附加
		super.appendAdditionalCriteria(criteria, modelMap);
		super.bindCriteria(modelMap, criteria);
		
		// 获取记录总数，用于前端显示分页
		int totalCount = getService().getModelListCount(criteria);

		// 构建分页信息
		Integer pageSize = getInteger(request, PARAM_PAGESIZE, BaseConfig.getPageSize());
		Map<String, Object> pagination = pageSize == null? super.buildPagination(getUrlPrefix(), criteria, totalCount, pageIndex)
				: super.buildPagination(getUrlPrefix(), criteria, totalCount, pageIndex, pageSize);
		modelMap.addAttribute("pagination", pagination);

		if(modelMap.get("modelList") == null){
			List<? extends BaseModel> modelList = pageSize == null? getService().getModelList(criteria, pageIndex) : getService().getModelList(criteria, pageIndex, pageSize);
			modelMap.addAttribute(MODEL_LIST, modelList);
		}
		// 附加更多属性
		attachMore(request, modelMap);

		return new JsonResult(Status.OK, modelMap.get(MODEL_LIST));
	}
	
	/***
	 * 根据id获取某资源对象
	 * @param id
	 * @param modelMap
	 * @return JsonResult
	 * @throws Exception
	 */
	protected JsonResult getModel(Object id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		BaseModel model = null;
		if(modelMap.get(MODEL) == null){
			model = getService().getModel(id);
			modelMap.put(MODEL, model);
		}
		else{
			model = (BaseModel) modelMap.get(MODEL);
		}
		attachMore4View(id, model, modelMap);
		// 异步创建访问日志
		if(isEnabledAccessLog){
			asyncLogger.saveOperationLog(BaseHelper.getCurrentUser(), OperationLog.OPERATION.VIEW, model);
		}
		return new JsonResult(Status.OK, model);
    }

	/***
	 * 创建资源对象
	 * @param model
	 * @param result
	 * @return JsonResult
	 * @throws Exception
	 */
	protected JsonResult createModel(BaseModel model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
        // Model属性值验证结果
		if(result != null && result.hasErrors()) {
            return new JsonResult(Status.FAIL_INVALID_PARAM, super.getBindingError(result));
        }
    	// 保存之前
		beforeCreate(request, model, modelMap);
		if(modelMap.get(ERROR) != null){
			return new JsonResult(Status.FAIL_VALIDATION, (String) modelMap.get(ERROR));
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
			// 组装返回结果
			Map<String, Object> data = new HashMap<String, Object>(4);
			data.put(BaseModel.F.id, model.getPk());
			return new JsonResult(Status.OK, data);
        }
        else{
        	logger.warn("创建操作未成功，model="+model.getClass().getSimpleName());
			// 组装返回结果
			return new JsonResult(Status.FAIL_OPERATION);
        }
    }

	/***
	 * 根据ID更新资源对象
	 * @param model
	 * @param result
	 * @return JsonResult
	 * @throws Exception
	 */
	protected JsonResult updateModel(BaseModel model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception{
		// Model属性值验证结果
		if(result.hasErrors()) {
			return new JsonResult(Status.FAIL_INVALID_PARAM, super.getBindingError(result));
		}
        // 更新之前的操作
		beforeUpdate(request, model, modelMap);
		if(modelMap.get("error") != null){
			return new JsonResult(Status.FAIL_VALIDATION, (String) modelMap.get("error"));
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
			// 组装返回结果
			Map<String, Object> data = new HashMap<String, Object>(4);
			data.put(BaseModel.F.id, model.getId());
			return new JsonResult(Status.OK, data);
        }
        else{
        	logger.warn("更新操作未成功，model="+model.getClass().getSimpleName()+", id="+model.getPk());
			// 返回操作结果
			return new JsonResult(Status.FAIL_OPERATION);
        }
	}
	
	/***
	 * 根据id删除资源对象
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected JsonResult deleteModel(Object id, HttpServletRequest request) throws Exception{
		if(id == null) {
			return new JsonResult(Status.FAIL_INVALID_PARAM, "请选择需要删除的条目！");
        }
		// 是否有权限删除
		BaseModel model = getService().getModel(id);
		// 执行删除操作
		boolean success = false;
		String error = beforeDelete(model);
		if(error == null){
			success = getService().deleteModel(id);
			if(success){
				// 执行删除后的动作
				afterDeleted(request, model);
				// 异步创建操作日志
				if(isEnabledOperationLog){
					BaseUser user = BaseHelper.getCurrentUser();
					asyncLogger.saveOperationLog(user, OperationLog.OPERATION.DELETE, model);
				}
				logger.info("删除操作成功，model="+model.getClass().getSimpleName()+", id="+id);
				Map<String, Object> map = new HashMap<String, Object>(4);
				map.put(BaseModel.F.id, id);
				return new JsonResult(Status.OK, map);
			}
			else{
				logger.warn("删除操作未成功，model="+model.getClass().getSimpleName()+", id="+id);
				return new JsonResult(Status.FAIL_OPERATION);
			}
		}
		else{
			// 返回json
			return new JsonResult(Status.FAIL_OPERATION, error);
		}
	}

	//============= 供子类继承重写的方法 =================
	/***
	 * 是否有删除权限，如不可删除返回无权
	 * @param model
	 * @return
	 */
	protected String beforeDelete(BaseModel model){
		return Status.FAIL_NO_PERMISSION.label();
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
	 * @param map
	 * @throws Exception
	 */
	protected void attachMore4View(Object id, BaseModel model, ModelMap map) throws Exception {
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