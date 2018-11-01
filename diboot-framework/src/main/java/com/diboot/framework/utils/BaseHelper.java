package com.diboot.framework.utils;

import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/***
 * 应用通用帮助类
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
public class BaseHelper {
	private static final Logger logger = LoggerFactory.getLogger(BaseHelper.class);
	
	/**
	 * 得到当前登录的用户名
	 * @return 
	 */
	public static <T extends BaseUser>T getCurrentUser(){
		try{
			Subject subject = SecurityUtils.getSubject();
			if(subject != null && subject.isAuthenticated()){
				return (T)subject.getPrincipal();
			}
		}
		catch (Exception e){
			logger.warn("获取用户信息异常", e);
		}
		return null;
	}

	/**
	 * 得到当前登录的用户id
	 * @return 
	 */
	public static Long getCurrentUserId(){
		BaseUser user = getCurrentUser();
		if(user != null){
			return (Long)user.getId();
		}
		if(logger.isDebugEnabled()){
			logger.warn("无法获取当前用户Id!");
		}
		return null;
	}
	
	/**
	 * 是否来自微信
	 * @param request
	 * @return
	 */
	public static boolean isFromWechat(HttpServletRequest request){
		String userAgent = request.getHeader("user-agent").toLowerCase();
		return V.notEmpty(userAgent) && userAgent.contains("micromessenger");
	}

	/***
	 * 是否为AJAX请求
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
		String header = request.getHeader("X-Requested-With");
		if (V.notEmpty(header) && "XMLHttpRequest".equals(header)){
			return true;
		}
		return false;
	}

	/**
	 * 获取Session中的对象
	 * @param request
	 * @param key
	 * @return
	 */
	public static Object getSessionObj(HttpServletRequest request, String key){
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute(key) != null){
			return session.getAttribute(key);
		}
		return null;
	}

	/***
	 * 获取请求URI (去除contextPath)
	 * @param request
	 * @return
	 */
	public static String getRequestMappingURI(HttpServletRequest request){
		String contextPath = request.getContextPath();
		if(V.notEmpty(contextPath)){
			return S.replaceFirst(request.getRequestURI(), contextPath, "");
		}
		return request.getRequestURI();
	}

	/***
	 * 返回json格式错误信息
	 * @param response
	 * @param jsonResult
	 */
	public static void responseJson(HttpServletResponse response, JsonResult jsonResult){
		// 处理异步请求
		PrintWriter pw = null;
		try {
			response.setStatus(HttpStatus.OK.value());
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			pw = response.getWriter();
			pw.write(JSON.stringify(jsonResult));
			pw.flush();
		}
		catch (IOException e) {
			logger.error("处理异步请求异常", e);
		}
		finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	/***
	 * 转换key-value对的List<Map>为Map
	 * @param mapList
	 * @return
	 */
	public static Map<Object, String> convert2VKMap(List<Map<String, Object>> mapList) {
		Map<Object, String> map = new HashMap(mapList.size());
		if(V.notEmpty(mapList)) {
			for(Map<String, Object> kvmap : mapList){
				map.put(kvmap.get("v"), String.valueOf(kvmap.get("k")));
			}
		}
		return map;
	}


	/***
	 * 打印所有参数信息
	 * @param request
	 */
	public static void dumpParams(HttpServletRequest request){
		Map<String, String[]> params = request.getParameterMap();
		if(params != null && !params.isEmpty()){
			for(Map.Entry<String, String[]> entry : params.entrySet()){
				String[] values = entry.getValue();
				if(values != null && values.length > 0){
					logger.debug(entry.getKey() + "=" + S.join(values));
				}
			}
		}
	}

	/***
	 * 将请求参数值转换为Map
	 * @param request
	 * @return
	 */
	public static Map<String, Object> convertParams2Map(HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>(16);
		if(request == null){
			return result;
		}
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()){
			String paramName = (String) paramNames.nextElement();
			String[] values = request.getParameterValues(paramName);
			if(V.notEmpty(values)){
				if(values.length == 1){
					result.put(paramName, values[0]);
				}
				else{
					// 多个值需传递到后台SQL的in语句
					result.put(paramName, values);
				}
			}
		}
		return result;
	}

	/***
	 * 格式化查询统计的结果，便于chart图表显示
	 * @param dataList
	 * @return
	 */
	public static Map<String, Object> formatQueryResult4Chart(List<LinkedHashMap<String, Object>> dataList){
		Map<String, Object> map = new HashMap<>();
		if(V.isEmpty(dataList)){
			return map;
		}
		List<Map<String, Object>> rowList = new ArrayList<>();
		// 获取标题行
		List<String> headers = extractHeadersFromQueryResult(dataList);
		// 标题行
		map.put("labelList", headers);
		for(String name : headers){
			Map<String, Object> objMap = new HashMap<>();
			objMap.put("name", name);
			List<Object> values = new ArrayList<>(dataList.size());
			for(LinkedHashMap<String, Object> rowData : dataList){
				Object value = rowData.get(name);
				if(value == null){
					value = "-";
				}
				values.add(value);
			}
			objMap.put("values", values);
			rowList.add(objMap);
		}
		// 数据
		map.put("dataList", rowList);
		return map;
	}

	/***
	 * 从结果集中提取标题行
	 * @param resultSetMapList
	 * @return
	 */
	public static List<String> extractHeadersFromQueryResult(List<LinkedHashMap<String,Object>> resultSetMapList) {
		if(V.isEmpty(resultSetMapList)){
			return null;
		}
		// 获取标题行
		List<String> headers = new ArrayList<>();
		int maxCount = resultSetMapList.size() > 10? 10 : resultSetMapList.size();
		int fullColumnIndex = 0;
		for(int i=0; i<maxCount; i++) {
			if(resultSetMapList.get(i).size() > fullColumnIndex){
				fullColumnIndex = i;
			}
		}
		// 完整列的行
		LinkedHashMap<String, Object> fullColumnRow = resultSetMapList.get(fullColumnIndex);
		for(Map.Entry<String, Object> entry : fullColumnRow.entrySet()){
			String name = entry.getKey();
			if(V.isEmpty(name)){
				name = "-";
			}
			headers.add(name);
		}
		return headers;
	}

	/***
	 * 构建异常信息
	 * @param request
	 * @return
	 */
	public static String buildExceptionInfo(HttpServletRequest request){
		String requestUrl = getRequestMappingURI(request);
		if(V.notEmpty(request.getQueryString())){
			requestUrl += "?"+ request.getQueryString();
		}
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
		}
		Object exception = request.getAttribute("javax.servlet.error.exception");
		StringBuilder sb = new StringBuilder();
		sb.append(request.getMethod() + " URL ").append(requestUrl).append(" Exception: ")
				.append(statusCode).append(" - ").append(request.getAttribute("javax.servlet.error.message"))
				.append(" - ").append(exception);
		String info = sb.toString();
		logger.error("发生异常: " + info);

		request.setAttribute("statusCode", statusCode);
		return info;
	}

}