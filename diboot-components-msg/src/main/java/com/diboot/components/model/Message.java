package com.diboot.components.model;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.utils.S;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
* 消息
* @author Mazc@dibo.ltd
* @version 2017-09-14
* Copyright @ www.dibo.ltd
*/
public class Message extends BaseModel {
	private static final long serialVersionUID = -9045277122744155137L;

	/**
	 * 类型
	 */
	public static enum TYPE{
		EMAIL,
		SMS,
		WECHAT,
		SYS,
	}

	/**
	 * 状态
	 */
	public static enum STATUS{
		NEW("待发送"),
		SENT("已发送"),
		DELIVRD("已送达"),
		UNDELIVERED("未送达");

		private String label;
		STATUS(String label) {
			this.label = label;
		}
		public String label() {
			return this.label;
		}
		public static String getLabel(String value) {
			for(STATUS st : STATUS.values()){
				if(st.name().equals(value)){
					return st.label();
				}
			}
			return null;
		}
	}

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		type = "type",
		msgType="msgType",
		tmplId = "tmplId",
		businessType = "businessType",
		businessId = "businessId",
		sender = "sender",
		receiver = "receiver",
		title = "title",
		content = "content",
		url = "url",
		status = "status",
		scheduleTime = "scheduleTime",
		response = "response"
	;}
	
    @NotNull(message = "消息类型不能为空！")
    @Length(max = 50, message = "消息类型长度超出了最大限制！")  
    private String type = "SMS"; // 类型
	
    @NotNull(message = "模板id不能为空！")
    private Long tmplId = 0L; // 模板id

	private String msgType; // 消息类型

    @Length(max = 50, message = "关联业务类型长度超出了最大限制！")  
    private String businessType; // 关联业务类型
	
    private Long businessId; // 关联业务ID
	
    @Length(max = 50, message = "发送人长度超出了最大限制！")  
    private String sender; // 发送人
	
    @NotNull(message = "接收人不能为空！")
    @Length(max = 50, message = "接收人长度超出了最大限制！")  
    private String receiver; // 接收人
	
    @Length(max = 255, message = "标题长度超出了最大限制！")  
    private String title; // 标题
	
    @NotNull(message = "内容不能为空！")
    @Length(max = 512, message = "内容长度超出了最大限制！")  
    private String content; // 内容
	
    @Length(max = 255, message = "链接长度超出了最大限制！")  
    private String url; // 链接
	
    @NotNull(message = "发送状态不能为空！")
    @Length(max = 20, message = "发送状态长度超出了最大限制！")  
    private String status = "NEW"; // 发送状态

    private Date scheduleTime = null; // 指定发送时间

    @Length(max = 50, message = "发送结果长度超出了最大限制！")  
    private String response; // 发送结果

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Long getTmplId() {
		return tmplId;
	}
	public void setTmplId(Long tmplId) {
		this.tmplId = tmplId;
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

    
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}

    
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
		return S.replace(content, "\r\n", "<br>");
	}
    
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getScheduleTime() {
		return this.scheduleTime;
	}

	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getStatusLabel() {
		return STATUS.getLabel(status);
	}
}