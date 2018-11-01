package com.diboot.components.msg.sms.submail.lib.base;

import com.diboot.components.msg.sms.submail.entity.DataStore;

/**
 * 包装类 ADDRESSBOOKMail、ADDRESSBOOKMessage、MAILSend、MAILXSend、MESSAGEXsend等父类
 * @author submail
 *
 */
public abstract class BaseSenderWapper {

	protected DataStore requestData = new DataStore();

	public abstract ISender getSender();
}
