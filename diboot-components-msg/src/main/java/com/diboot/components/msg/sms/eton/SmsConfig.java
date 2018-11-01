package com.diboot.components.msg.sms.eton;

import com.diboot.components.config.MsgCons;
import com.diboot.components.msg.sms.config.MsgConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/***
 * 短信配置
 */
public class SmsConfig {
	private static final Logger logger = LoggerFactory.getLogger(SmsConfig.class);

	/**
	 * 系统配置文件
	 */
	private static Map<SETTINGS, String> smsSettings = new HashMap<SETTINGS, String>();

	/**
	 * 移通短信设置
	 */
	public static enum SETTINGS{
		ETON_MTURL,
		ETON_SPID,
		ETON_SPPWD,
		ETON_SA,
		ETON_SIGNCODE
	}

	/**
	 * 获取系统参数
	 * @return
	 */
	public static Map<SETTINGS, String> getEtonSettings(){
		//如果未初始化，则初始化SystemProp
		if(smsSettings.isEmpty()){
			smsSettings.put(SETTINGS.ETON_MTURL, MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.SMS_ETON,"sms.eton.mturl"));
			smsSettings.put(SETTINGS.ETON_SPID, MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.SMS_ETON,"sms.eton.spid"));
			smsSettings.put(SETTINGS.ETON_SPPWD, MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.SMS_ETON,"sms.eton.password"));
			smsSettings.put(SETTINGS.ETON_SA, MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.SMS_ETON,"sms.eton.sa"));
			smsSettings.put(SETTINGS.ETON_SIGNCODE, MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.SMS_ETON,"sms.eton.signcode"));
		}
		
		if(smsSettings.get(SETTINGS.ETON_SPID) == null){
			logger.warn("未获取到移通短信配置信息，如需要短信功能请检查配置参数: msg.sms.eton.mturl, sms.eton.spid, sms.eton.sppwd .");
		}
		else{
			logger.debug(smsSettings.toString());
		}
		return smsSettings;
	}
	
}