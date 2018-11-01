package com.diboot.framework.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

/***
 * 自定义request包装类
 * @author Mazc@dibo.ltd
 * @version 2017年11月25日
 * Copyright@www.Dibo.ltd
 */
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private static final Logger logger = LoggerFactory.getLogger(CustomHttpServletRequestWrapper.class);

	private static Pattern[] patterns = new Pattern[]{
		// Script fragments
		Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
		// src='...' 暂时允许 <img src=''>
		//Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		//Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		// lonely script tags
		Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
		Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		// eval(...)
		Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		// expression(...)
		Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		// javascript:...
		Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
		// vbscript:...
		Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
		// onload(...)=...
		Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
	};

	/**
	 * 原始request
	 */
	HttpServletRequest originalRequest = null;  
	
	public CustomHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		originalRequest = request;
	}
	
	/***
	 * 获取原始request（未转码）
	 * @return
	 */
	public HttpServletRequest getOriginalRequest(){
		return originalRequest;
	}

    
    /** 
     * 覆盖getParameterMap方法，将参数值做xss转码
    @Override 
    public Map<String, String[]> getParameterMap() {
    	Map<String, String[]> map = super.getParameterMap();
    	if(V.notEmpty(map)){
    		for(Map.Entry<String, String[]> entry : map.entrySet()){
    			String[] values = entry.getValue();
    			if(V.notEmpty(values)){
    				for(int i=0; i<values.length; i++){
    					values[i] = S.xssEncode(values[i]);
    				}
    			}
    			entry.setValue(values);
    		}
    	}
        return map;
    }*/

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = stripXSS(values[i]);
		}
		return encodedValues;
	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		return stripXSS(value);
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		return stripXSS(value);
	}

	private String stripXSS(String value) {
		if (value != null) {
			// value = ESAPI.encoder().canonicalize(value);
			for (Pattern scriptPattern : patterns){
				value = scriptPattern.matcher(value).replaceAll("");
			}
		}
		return value;
	}

}
