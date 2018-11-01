package com.diboot.framework.utils;

import com.diboot.framework.config.BaseConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

/***
 * Dibo String 操作类 
 * @author Mazc@dibo.ltd
 * @version 2016年11月24日
 * Copyright @ www.dibo.ltd
 */
public class S extends StringUtils{
	// 默认分隔符
	private static final String SEPARATOR = ",";

	/***
	 * 裁剪字符串，显示前部分+...
	 * @param input
	 * @return
	 */
	public static String cut(String input){
		return cut(input, BaseConfig.getCutLength());
	}

	/***
	 * 裁剪字符串，显示前部分+...
	 * @param input
	 * @return
	 */
	public static String cut(String input, int cutLength){
		return substring(input, 0, cutLength);
	}

	/***
	 * 转换换行符, 用于textarea内容输出为Html等
	 * @param content
	 * @return
	 */
	public static String parseBR(String content){
		return replace(content, "\r\n", "<br>");
	}

	/**
	 * html文本转换为text
	 * @param html
	 * @return
	 */
	public static String html2text(String html) {
		String textStr = "";
		Pattern pattern;
		java.util.regex.Matcher matcher;
		try {
			String regEx_html = "<[^>]+>";
			pattern = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(html);
			html = matcher.replaceAll("");
			textStr = html;
		} catch (Exception e) {
			return null;
		}
		//剔除空格行
		textStr = textStr.replaceAll("[ ]+", " ");
		textStr = textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
		return textStr;
	}

	/***
	 * 将list拼接成string，默认分隔符:,
	 * @param stringList
	 * @return
	 */
	public static String join(List<String> stringList){
		return StringUtils.join(stringList, SEPARATOR);
	}

	/***
	 * 将list拼接成string，默认分隔符:,
	 * @param stringArray
	 * @return
	 */
	public static String join(String[] stringArray){
		return StringUtils.join(stringArray, SEPARATOR);
	}

	/***
	 * 将list拼接成string，默认分隔符<br> 用于前端换行显示
	 * @param stringList
	 * @return
	 */
	public static String joinWithBr(List<String> stringList){
		return StringUtils.join(stringList, "<br>");
	}

	/***
	 * 将list拼接成string，默认分隔符<br> 用于前端换行显示
	 * @param stringArray
	 * @return
	 */
	public static String joinWithBr(String[] stringArray){
		return StringUtils.join(stringArray, "<br>");
	}

	public static String[] split(String joinedStr){
		if(joinedStr == null){
			return null;
		}
		return joinedStr.split(SEPARATOR);
	}

	/***
	 * 转换为String数组（避免转型异常）
	 * @param stringList
	 * @return
	 */
	public static String[] toStringArray(List<String> stringList){
		String[] array = new String[stringList.size()];
		for(int i=0; i<stringList.size(); i++){
			array[i] = stringList.get(i);
		}
		return array;
	}

	/***
	 * 转换为Long类型（判空，避免NPE）
	 * @param strValue
	 * @return
	 */
	public static Long toLong(String strValue){
		return toLong(strValue, null);
	}

	/***
	 * 转换为Long类型（判空，避免NPE）
	 * @param strValue 字符类型值
	 * @param defaultLong 默认值
	 * @return
	 */
	public static Long toLong(String strValue, Long defaultLong){
		if(V.isEmpty(strValue)){
			return defaultLong;
		}
		return Long.parseLong(strValue);
	}

	/***
	 * 转换为Integer类型(判空，避免NPE)
	 * @param strValue
	 * @return
	 */
	public static Integer toInt(String strValue){
		return toInt(strValue, null);
	}

	/***
	 * 转换为Integer类型(判空，避免NPE)
	 * @param strValue
	 * @param defaultInt 默认值
	 * @return
	 */
	public static Integer toInt(String strValue, Integer defaultInt){
		if(V.isEmpty(strValue)){
			return defaultInt;
		}
		return Integer.parseInt(strValue);
	}

	/***
	 * 字符串转换为boolean
	 * @param strValue
	 * @return
	 */
	public static boolean toBoolean(String strValue){
		return toBoolean(strValue, false);
	}

	/***
	 * 字符串转换为boolean
	 * @param strValue
	 * @param defaultBoolean
	 * @return
	 */
	public static boolean toBoolean(String strValue, boolean defaultBoolean){
		if(V.notEmpty(strValue)){
			return V.isTrue(strValue);
		}
		return defaultBoolean;
	}

	/***
	 * 将多个空格替换为一个
	 * @param input
	 * @return
	 */
	public static String removeDuplicateBlank(String input){
		if(V.isEmpty(input)){
			return input;
		}
		return input.trim().replaceAll(" +", " ");
	}


	/**
	 * 从URL参数中获取某个参数值
	 * @param url
	 * @return
	 */
	public static String getParamValueFromUrl(String url, String paramName){
		if(V.isEmpty(url) || V.isEmpty(paramName) || !url.contains(paramName+"=")){
			return null;
		}
		paramName += "=";
		int startIndex = url.indexOf(paramName)+paramName.length();
		String paramValue = url.substring(startIndex);
		if(paramValue.contains("&")){
			paramValue = paramValue.substring(0, paramValue.indexOf("&"));
		}
		return paramValue;
	}
    
    /**
     * 获得随机串
     * @return
     */
    public static String newUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

	/***
	 * 生成指定位数的数字/验证码
	 */
	private static final String NUMBER_SET = "12345678901";
	private static Random random = new Random();
    public static String newRandomNum(int length){
		StringBuilder sb = new StringBuilder();
		sb.append(NUMBER_SET.charAt(random.nextInt(9)));
    	for(int i=1; i<length; i++){
    		sb.append(NUMBER_SET.charAt(random.nextInt(10)));
		}
		return sb.toString();
	}
	
	/***
	 * 将首字母转为小写
	 * @return
	 */
	public static String uncapFirst(String input){
		if(input != null){
			return String.valueOf(input.charAt(0)).toLowerCase() + input.substring(1);
		}
		return null;
	}
	
	/***
	 * 将首字母转为大写
	 * @return
	 */
	public static String capFirst(String input){
		if(input != null){
			return String.valueOf(input.charAt(0)).toUpperCase() + input.substring(1);
		}
		return null;
	}

	/***
	 * 批量替换关键字
	 * @param text
	 * @param searchList
	 * @param replacementList
	 * @return
	 */
	public static String replaceEach(String text, List<String> searchList, List<String> replacementList){
		if(V.isEmpty(searchList) || V.isEmpty(replacementList)){
			return text;
		}
		String[] searchArray = searchList.toArray(new String[searchList.size()]);
		String[] replacementArray = replacementList.toArray(new String[replacementList.size()]);
		return replaceEach(text, searchArray, replacementArray);
	}

	/**
	 * 将XSS漏洞相关的半角字符转换为全角字符
	 * @param text
	 * @return
	 */
	public static String xssEncode(String text) {
		if (V.isEmpty(text)) {
			return text;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch (c) {
				case '>':
					sb.append("＞");// 转义大于号
					break;
				case '<':
					sb.append("＜");// 转义小于号
					break;
				case '\'':
					sb.append("‘");// 转义单引号
					break;
				case '\"':
					sb.append("“");// 转义双引号
					break;
				case '&':
					sb.append('＆'); // 转义&
					break;
				case '\\':
					sb.append('＼');
					break;
				case '#':
					sb.append('＃');
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}

}
