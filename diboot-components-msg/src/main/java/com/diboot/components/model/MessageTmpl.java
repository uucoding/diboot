package com.diboot.components.model;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.utils.S;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
* 消息模板
* @author Mazc@dibo.ltd
* @version 2017-09-14
* Copyright @ www.dibo.ltd
*/
public class MessageTmpl extends BaseModel {
	private static final long serialVersionUID = -8481654766854879458L;

	public static enum METADATA_TYPE{
		MESSAGE_TMPL_CODE,          // 消息模板
		MESSAGE_TMPL_VARIBLES       // 消息模板变量
	}

	/**
	 * 短信验证码
	 */
	public static final String VERIFICATION_CODE = "VC";

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		type = "type",
		code = "code",
		msgType = "msgType",
		businessType = "businessType",
		businessId = "businessId",
		title = "title",
		content = "content",
		comment = "comment",
		system = "system"
	;}
	
    @NotNull(message = "类型不能为空！")
    @Length(max = 50, message = "类型长度超出了最大限制！")  
    private String type = Message.TYPE.SMS.name(); // 类型
	
    @NotNull(message = "模板编码不能为空！")
    @Length(max = 32, message = "模板编码长度超出了最大限制！")
    private String code; // 模板编码

	@Length(max = 50, message = "消息类型长度超出了最大限制！")
	private String msgType; // 消息类型

	@Length(max = 50, message = "关联业务类型长度超出了最大限制！")
	private String businessType; // 适用业务类型

	private Long businessId; // 适用业务ID
	
    @Length(max = 255, message = "标题长度超出了最大限制！")  
    private String title; // 标题

	@Length(max = 255, message = "消息标题长度超出了最大限制！")
	private String msgTitle; // 消息标题
	
    @NotNull(message = "内容不能为空！")
    @Length(max = 512, message = "内容长度超出了最大限制！")  
    private String content; // 内容
	
    @Length(max = 255, message = "备注长度超出了最大限制！")  
    private String comment; // 备注

	private boolean system = false; //是否为系统模板
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getShortContent() {
    	return S.cut(content);
    }
	public String getContentHtml() {
		return S.parseBR(content);
	}
    
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isSystem() {
		return system;
	}

	public void setSystem(boolean system) {
		this.system = system;
	}
}