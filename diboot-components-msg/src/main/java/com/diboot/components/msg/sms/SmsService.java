package com.diboot.components.msg.sms;

/***
 * Dibo 短信发送接口 
 * @author Mazc@dibo.ltd
 * @version 2017年1月13日
 * Copyright 2017 www.dibo.ltd
 */
public interface SmsService {

	/***
	 * 发送短信
	 * @param phone
	 * @param content
	 * @return
	 * @throws Exception
	 */
	String send(String phone, String content) throws Exception;

	/***
	 * 发送短信 - 带签名
	 * @param signCode
	 * @param phone
	 * @param content
	 * @return
	 * @throws Exception
	 */
	String send(String phone, String content, String signCode) throws Exception;
	
}
