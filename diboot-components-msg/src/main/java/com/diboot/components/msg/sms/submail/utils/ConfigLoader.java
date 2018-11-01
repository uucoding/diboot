package com.diboot.components.msg.sms.submail.utils;

import com.diboot.components.msg.sms.submail.config.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 这个类通过加载app_config.properties文件创建配置对象并获取值，包括创建
 * MailConfig，MessageConfig，VoiceConfig,InternationalsmsConfig,MobiledataConfig
 * @author submail
 *
 */
public class ConfigLoader {

	private static Properties pros = null;
	/**
	 * 加载文件时，类载入，静态块内部的操作将被运行一次
	 * */
	static {
		pros = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = ConfigLoader.class.getResourceAsStream("/app_config.properties");
			pros.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(inputStream != null){
				try{
					inputStream.close();
				}
				catch (Exception e){
				}
			}
		}
	}

	/**
	 * enum define two kinds of configuration.
	 * */
	public static enum ConfigType {
		Mail, Message,Voice,Internationalsms,Mobiledata
	};

	/**
	 * 外部类的静态方法，可以通过加载文件创建配置。
	 * 
	 * @param type
	 *            ConfigType
	 * */
	public static AppConfig load(ConfigType type) {
		switch (type) {
		case Mail:
			return createMailConfig();
		case Message:
			return createMessageConfig();
		case Voice:
			return createVoiceConfig();
		case Internationalsms:
			return createInternationalsmsConfig();
		case Mobiledata:
			return createMobiledataConfig();
		default:
			return null;
		}
	}

	private static AppConfig createMailConfig() {
		AppConfig config = new MailConfig();
		config.setAppId(pros.getProperty(MailConfig.APP_ID));
		config.setAppKey(pros.getProperty(MailConfig.APP_KEY));
		config.setSignType(pros.getProperty(MailConfig.APP_SIGNTYPE));
		return config;
	}

	private static AppConfig createMessageConfig() {
		AppConfig config = new MessageConfig();
		config.setAppId(pros.getProperty(MessageConfig.APP_ID));
		config.setAppKey(pros.getProperty(MessageConfig.APP_KEY));
		config.setSignType(pros.getProperty(MessageConfig.APP_SIGNTYPE));
		return config;
	}
	
	private static AppConfig createVoiceConfig() {
		AppConfig config = new VoiceConfig();
		config.setAppId(pros.getProperty(VoiceConfig.APP_ID));
		config.setAppKey(pros.getProperty(VoiceConfig.APP_KEY));
		config.setSignType(pros.getProperty(VoiceConfig.APP_SIGNTYPE));
		return config;
	}
	
	private static AppConfig createInternationalsmsConfig() {
		AppConfig config = new InternationalsmsConfig();
		config.setAppId(pros.getProperty(InternationalsmsConfig.APP_ID));
		config.setAppKey(pros.getProperty(InternationalsmsConfig.APP_KEY));
		config.setSignType(pros.getProperty(InternationalsmsConfig.APP_SIGNTYPE));
		return config;
	}
	
	private static AppConfig createMobiledataConfig() {
		AppConfig config = new MobiledataConfig();
		config.setAppId(pros.getProperty(MobiledataConfig.APP_ID));
		config.setAppKey(pros.getProperty(MobiledataConfig.APP_KEY));
		config.setSignType(pros.getProperty(MobiledataConfig.APP_SIGNTYPE));
		return config;
	}

}
