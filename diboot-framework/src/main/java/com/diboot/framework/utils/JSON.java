package com.diboot.framework.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/***
 * JSON操作辅助类
 * @author Mazc@dibo.ltd
 */
public class JSON extends JSONObject{
	private static final Logger logger = LoggerFactory.getLogger(JSON.class);

	private static SerializeConfig serializeConfig = new SerializeConfig();
	static {
		serializeConfig.put(Date.class, new SimpleDateFormatSerializer(D.FORMAT_DATETIME_Y4MDHM));
	}

	public static String stringify(Object object){
		return toJSONString(object, serializeConfig);
	}

	/***
	 * 将JSON字符串转换为java对象
	 * @param jsonStr
	 * @return
	 */
	public static Map toMap(String jsonStr){
		return parseObject(jsonStr);
	}

	/***
	 * 将JSON字符串转换为java对象
	 * @param jsonStr
	 * @return
	 */
	public static LinkedHashMap toLinkedHashMap(String jsonStr){
		if(V.isEmpty(jsonStr)){
			return null;
		}
		return toJavaObject(jsonStr, LinkedHashMap.class);
	}

	/***
	 * 将JSON字符串转换为JSONObject对象
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject toObject(String jsonStr){
		return parseObject(jsonStr);
	}

	/***
	 * 将JSON字符串转换为java对象
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T toJavaObject(String jsonStr, Class<T> clazz){
		return JSONObject.parseObject(jsonStr, clazz);
	}

}