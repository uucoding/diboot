package com.diboot.framework.controller;

import com.diboot.framework.async.AsyncLogger;
import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.service.BaseFileService;
import com.diboot.framework.service.MetadataService;
import com.diboot.framework.service.impl.BaseServiceImpl;
import com.diboot.framework.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/***
 * Controller的父类
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
@Controller
public class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/***
	 * URL跳转的前缀
 	 */
	protected static final String REDIRECT_TO = "redirect:";
	/***
	 * 分页参数名
	 */
	protected static final String PARAM_PAGE = "page";
	/**
	 * 分页参数名
	 */
	protected static final String PARAM_PAGESIZE = "pageSize";
	/***
	 * 排序字段参数名
	 */
	protected static final String PARAM_ORDERBY = "_orderBy";
	/***
	 * model对象变量名常量
	 */
	protected static final String MODEL = "model";
	/**
	 * modelList对象变量名常量
	 */
	protected static final String MODEL_LIST = "modelList";

	/**
	 * 是否启用追踪日志
	 */
	protected boolean isEnabledOperationLog = BaseConfig.isTrue("diboot.log.operation-enabled");

	/***
	 * 是否启用访问日志
	 */
	protected boolean isEnabledAccessLog = BaseConfig.isTrue("diboot.log.access-enabled");

	/**
	 * 错误关键字
	 */
	protected static final String ERROR = "error";

	/**
	 * 日志记录Logger
	 */
	@Autowired
	protected AsyncLogger asyncLogger;
	/***
	 * 元数据Service
	 */
	@Autowired
	protected MetadataService metadataService;
	/***
	 * 文件上传Service
	 */
	@Autowired
	protected BaseFileService baseFileService;

	/***
	 * 获取view视图页面的path前缀（文件夹路径）
	 * @return
	 */
	protected String getViewPrefix(){
		return null;
	}

	/***
	 * 获取URL的前缀 /xx，默认=/+getViewPrefix
	 * @return
	 */
	protected String getUrlPrefix(){
		String viewPrefix = getViewPrefix();
		if(V.notEmpty(viewPrefix)){
			return viewPrefix.startsWith("/")? viewPrefix : "/"+viewPrefix;
		}
		return null;
	}

	/***
	 * 跳转到URL
	 * @param url
	 * @return
	 */
	protected String redirectTo(Object url){
		return REDIRECT_TO + url;
	}

	/***
	 * view路径+文件名
	 * @param fileName
	 * @return
	 */
	public String view(HttpServletRequest request, ModelMap model, String fileName){
		// 绑定操作结果
		bindResultMsg(request, model);
		// 更新缓存
		HttpSession session = request.getSession(false);
		if(session != null){
			BaseUser user = BaseHelper.getCurrentUser();
			if(user != null){
				if(session.getAttribute("user") == null){
					session.setAttribute("user", user);					
				}
			}
		}
		return getViewPrefix()+"/"+fileName;
	}

	/***
	 * 获取操作结果信息
	 * @param request
	 * @param model
	 */
	public void bindResultMsg(HttpServletRequest request, ModelMap model){
		// 将闪现信息从Session中读取
		HttpSession session = request.getSession(false);
		if(session != null){
			String[] msg = (String[]) session.getAttribute("result");
			if(msg != null){
				model.put("result", msg);
				session.removeAttribute("result");
			}
		}
	}

	/***
	 * 添加值到缓存
	 * @param request
	 * @param attrName
	 * @param value
	 */
	protected void addIntoSession(HttpServletRequest request, String attrName, Object value){
		HttpSession session = request.getSession(false);
		if(session != null){
			session.setAttribute(attrName, value);
		}
	}

	/***
	 * 从缓存获取对象
	 * @param request
	 * @param attrName
	 * @return
	 */
	protected Object getFromSession(HttpServletRequest request, String attrName) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return session.getAttribute(attrName);
		}
		return null;
	}

	/***
	 * 添加操作结果信息
	 * @param request
	 * @param success
	 * @param msg
	 */
	public void addResultMsg(HttpServletRequest request, boolean success, String msg){
		// 将闪现信息缓存到Session
		addIntoSession(request, "result", new String[]{String.valueOf(success), msg});
	}
	
	/**
	 * 绑定指定的一个错误信息
	 * @return
	 */
	public void bindError(ModelMap model, String errorMsg){
		model.put("errors", errorMsg);	
	}

	/**
	 * 添加附加的提示信息，加在操作成功/失败的提示后面
	 * @return
	 */
	public void addAdditionalPromptMsg(ModelMap model, String promptMsg){
		model.put("promptMsg", promptMsg);
	}

	/**
	 * 解析所有的验证错误信息
	 * @param result
	 * @return
	 */
	public void bindErrors(ModelMap model, BindingResult result){
		if(result == null || !result.hasErrors()){
			return;
		}
		List<ObjectError> errors = result.getAllErrors();
		StringBuilder sb = new StringBuilder("<ul>");
		for(ObjectError error : errors){
			sb.append("<li>").append(error.getDefaultMessage().replaceAll("\"", "'")).append("</li>");
		}
		sb.append("</ul>");
		model.put("errors", sb.toString());
	}
	
	/**
	 * 解析所有的验证错误信息，转换为JSON
	 * @param result
	 * @return
	 */
	public String getBindingError(BindingResult result){
		if(result == null || !result.hasErrors()){
			return null;
		}
		List<ObjectError> errors = result.getAllErrors();
		List<String> allErrors = new ArrayList<String>(errors.size());
		for(ObjectError error : errors){
			allErrors.add(error.getDefaultMessage().replaceAll("\"", "'"));
		}
		return S.join(allErrors);
	}
	
	/**
	 * 封装错误信息
	 * @param errorMsg
	 * @return
	 */
	public Map<String, Object> buildJsonError(String errorMsg){
		Map<String, Object> map = new HashMap<String, Object>(4);
		List<String> allErrors = new ArrayList<String>();
		allErrors.add(errorMsg);
		map.put("errors", allErrors);
		
		return map;
	}
	
	/***
	 * new查询条件
	 * @param key
	 * @param value
	 * @return
	 */
	public Map<String, Object> newCriteria(String key, Object value) {
		Map<String, Object> criteria = new HashMap<String, Object>(8);
		criteria.put(key, value);
		return criteria;
	}
	
	/***
	 * 添加查询条件到ModelMap
	 * @param modelMap
	 * @param criteria
	 * @return
	 */
	public void bindCriteria(ModelMap modelMap, Map<String, Object> criteria) {
		modelMap.addAttribute("criteria", criteria);
	}

	/***
	 * 构建分页信息
	 * @param baseUrl
	 * @param criteria
	 * @param totalCount
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> buildPagination(String baseUrl, Map<String, Object> criteria, int totalCount, int currentPage, int... pageSize) {
		// 获取记录总数，用于前端显示分页
		Map<String, Object> result = new HashMap<String, Object>(16);
		result.put("baseUrl", baseUrl);
		result.put("current", currentPage);
		result.put("totalCount", totalCount);
		result.put("params", criteria2paramString(criteria));
		int pageNumber = BaseConfig.getPageSize();
		if(pageSize != null && pageSize.length > 0){
			pageNumber = pageSize[0];
		}
		result.put(PARAM_PAGESIZE, pageNumber);

		int totalPages = (int)Math.ceil((float)totalCount/pageNumber);
		result.put("totalPages", totalPages);
		
		// 计算需要显示的页码
		int showPageCount = BaseConfig.getShowPagesNum();
		// 总页数不满  全部显示
		int pageStart = 1, pageEnd = totalPages;
		if(totalPages > showPageCount){
			pageStart = currentPage - showPageCount/2 - 1;
			if(pageStart < 1){
				pageStart = 1;
			}
			pageEnd = currentPage + showPageCount/2;
			if((pageEnd - pageStart) < showPageCount){
				pageEnd = pageEnd + (showPageCount - (pageEnd - pageStart)) - 1;
			}
			if(pageEnd > totalPages){
				pageStart = pageStart - (pageEnd -totalPages);
				pageEnd = totalPages;
			}
		}
		List<Integer> diaplayPages = new ArrayList<Integer>();
		for (int i = pageStart; i <= pageEnd; i++) {
			diaplayPages.add(i);
		}
		result.put("pages", diaplayPages);
        
		return result;
	}
	
	/***
	 * 查询条件转换为url参数字符串
	 * @param criteria
	 * @return
	 */
	private String criteria2paramString(Map<String, Object> criteria){
		String result = "";
		if(criteria != null && !criteria.isEmpty()){
			for(Map.Entry<String, Object> entry : criteria.entrySet()){
				if(BaseServiceImpl.OFFSET.equals(entry.getKey()) || BaseServiceImpl.COUNT.equals(entry.getKey()) || entry.getKey().startsWith(Query.C.ORDERBY_.name())){
					continue;
				}
				String value = String.valueOf(entry.getValue());
				if(StringUtils.isNotBlank(value)){
					if(result == ""){
						result = "?" + entry.getKey()+"="+value;
					}
					else{
						result += "&" + entry.getKey()+"="+value;						
					}
				}
			}
		}
		return result;
	}

	/***
	 * 是否继续创建
	 * @param request
	 * @return
	 */
	public boolean isContinue(HttpServletRequest request){
		return "true".equals(request.getParameter("continue"));
	}

	/***
	 * 构建查询条件Model
	 * @param request
	 * @return
	 */
	public Map<String, Object> buildQueryCriteria(HttpServletRequest request, int pageIndex) throws Exception{
		Map<String, Object> result = getParamsMap(request);
		return result;
	}

	/***
	 * 附加更多条件到modelMap中的criteria
	 * @param criteria
	 * @param modelMap
	 */
	protected void appendAdditionalCriteria(Map<String, Object> criteria, ModelMap modelMap){
		// 是否已经存在指定条件，如存在则附加
		if(modelMap.get("criteria") != null){
			Map<String, Object> additionalCriteria = (Map<String, Object>) modelMap.get("criteria");
			if(additionalCriteria != null && !additionalCriteria.isEmpty()){
				for(Map.Entry<String, Object> entry : additionalCriteria.entrySet()){
					criteria.put(entry.getKey(), entry.getValue());
				}
			}
		}
	}

	/***
	 * 获取请求参数Map
	 * @param request
	 * @return
	 */
	public Map<String, Object> getParamsMap(HttpServletRequest request) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>(16);
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()){
			String paramName = (String) paramNames.nextElement();
			// 拦截掉伪造的排序条件
			if(paramName.startsWith(Query.C.ORDERBY_.name())){
				continue;
			}
			String[] values = request.getParameterValues(paramName);
			if(V.notEmpty(values)){
				if(values.length == 1){
					if(V.notEmpty(values[0])){
						String paramValue = java.net.URLDecoder.decode(values[0], "utf-8");
						result.put(paramName, paramValue);

						// 针对OrderBy排序字段特殊处理
						if(PARAM_ORDERBY.equals(paramName) && V.notEmpty(paramValue)){
							String fieldName = paramValue, type = "ASC";
							if(paramValue.contains(":")){
								String[] orderCondition = paramValue.split(":");
								if("ASC".equalsIgnoreCase(orderCondition[1]) || "DESC".equalsIgnoreCase(orderCondition[1])){
									type = orderCondition[1].toUpperCase();
								}
								fieldName = orderCondition[0];
							}
							// 查询条件
							result.put(Query.C.ORDERBY_+fieldName, type);
						}
					}
				}
				else{
					String[] valueArray = new String[values.length];
					for(int i=0; i<values.length; i++){
						valueArray[i] = java.net.URLDecoder.decode(values[i], "utf-8");
					}
					// 多个值需传递到后台SQL的in语句
					result.put(paramName, valueArray);
				}
			}
		}

		return result;
	}

	/***
	 * 打印所有参数信息
	 * @param request
	 */
	public void dumpParams(HttpServletRequest request){
		BaseHelper.dumpParams(request);
	}
	
	/**
	 * 从request获取Long参数
	 * @param request
	 * @param param
	 * @return
	 */
	public Long getLong(HttpServletRequest request, String param){
		return S.toLong(request.getParameter(param));
	}

	/**
	 * 从request获取Long参数
	 * @param request
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public Long getLong(HttpServletRequest request, String param, Long defaultValue){
		return S.toLong(request.getParameter(param), defaultValue);
	}
	
	/**
	 * 从request获取Int参数
	 * @param request
	 * @param param
	 * @return
	 */
	public Integer getInteger(HttpServletRequest request, String param){
		return S.toInt(request.getParameter(param));
	}

	/**
	 * 从request获取Int参数
	 * @param request
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public Integer getInteger(HttpServletRequest request, String param, Integer defaultValue){
		return S.toInt(request.getParameter(param), defaultValue);
	}

	/***
	 * 从request中获取boolean值
	 * @param request
	 * @param param
	 * @return
	 */
	public boolean getBoolean(HttpServletRequest request, String param){
		return S.toBoolean(request.getParameter(param));
	}

	/***
	 * 从request中获取boolean值
	 * @param request
	 * @param param
	 * @param defaultBoolean
	 * @return
	 */
	public boolean getBoolean(HttpServletRequest request, String param, boolean defaultBoolean){
		return S.toBoolean(request.getParameter(param), defaultBoolean);
	}

	/**
	 * 从request获取Double参数
	 * @param request
	 * @param param
	 * @return
	 */
	public Double getDouble(HttpServletRequest request, String param){
		if(V.notEmpty(request.getParameter(param))){
			return Double.parseDouble(request.getParameter(param));
		}
		return null;
	}

	/**
	 * 从request获取Double参数
	 * @param request
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public Double getDouble(HttpServletRequest request, String param, Double defaultValue){
		if(V.notEmpty(request.getParameter(param))){
			return Double.parseDouble(request.getParameter(param));
		}
		return defaultValue;
	}

	/**
	 * 从request获取String参数
	 * @param request
	 * @param param
	 * @return
	 */
	public String getString(HttpServletRequest request, String param){
		if(V.notEmpty(request.getParameter(param))){
			return request.getParameter(param);
		}
		return null;
	}

	/**
	 * 从request获取String参数
	 * @param request
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public String getString(HttpServletRequest request, String param, String defaultValue){
		if(V.notEmpty(request.getParameter(param))){
			return request.getParameter(param);
		}
		return defaultValue;
	}

	/**
	 * 从request获取String[]参数
	 * @param request
	 * @param param
	 * @return
	 */
	public String[] getStringArray(HttpServletRequest request, String param){
		if(request.getParameterValues(param) != null){
			return request.getParameterValues(param);
		}
		return null;
	}

	/***
	 * 从request里获取String列表
	 * @param request
	 * @param param
	 * @return
	 */
	public List<String> getStringList(HttpServletRequest request, String param){
		String[] strArray = getStringArray(request, param);
		if(V.isEmpty(strArray)){
			return null;
		}
		return new ArrayList<>(Arrays.asList(strArray));
	}

	/***
	 * 从request里获取Long列表
	 * @param request
	 * @param param
	 * @return
	 */
	public List<Long> getLongList(HttpServletRequest request, String param){
		String[] strArray = getStringArray(request, param);
		if(V.isEmpty(strArray)){
			return null;
		}
		List<Long> longList = new ArrayList<>();
		for(String str : strArray){
			if(V.notEmpty(str)){
				longList.add(Long.parseLong(str));
			}
		}
		return longList;
	}
}