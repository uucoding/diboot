package com.diboot.wechat.open.model;

import com.diboot.framework.model.BaseModel;

/**
* 微信参数 Model对象定义
* @author Mazc@dibo.ltd
* @version 2018-06-21
* Copyright © www.dibo.ltd
*/
public class WxConfigStorage extends com.diboot.framework.model.BaseModel{
	private static final long serialVersionUID = 5817601963909417534L;

	public static enum TYPE{
		WX_OPEN
	}

	public static enum FIELDS{
		component_verify_ticket,
		timestamp
	}

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{
	}


	@Override
	public String getModelName(){
		return "微信参数";
	}
}