package com.diboot.framework.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
* 定时任务
* @author Mazc@dibo.ltd
* @version 2018-01-20
* © 2017 www.dibo.ltd
*/
public class TimerTask extends BaseModel{
	private static final long serialVersionUID = 2001L;

	/***
	 * 状态
	 */
	public static enum STATUS{
		NEW("待处理"),
		DOING("处理中"),
		SUCCESS("成功"),
		FAIL("失败");
		private String label;
		STATUS(String label){
			this.label = label;
		}
		public String label(){
			return this.label;
		}
		public static String getLabel(String value){
			for(STATUS op : STATUS.values()){
				if(op.name().equals(value)){
					return op.label();
				}
			}
			return null;
		}
	}

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		businessType = "businessType",
		businessId = "businessId",
		type = "type",
		executor = "executor",
		scheduleTime = "scheduleTime",
		status = "status",
		beginTime = "beginTime",
		endTime = "endTime",
		progress = "progress",
		comment = "comment"
	;}
	
    @Length(max = 50, message = "业务类型长度超出了最大限制！")
    private String businessType; // 业务类型
	
    private Long businessId; // 业务id

    @Length(max = 50, message = "任务类型长度超出了最大限制！")
    private String type; // 任务类型

	@NotNull(message = "执行者不能为空！")
	private String executor;

    @NotNull(message = "定时时间不能为空！")
    private Date scheduleTime; // 定时时间
	
    @NotNull(message = "状态不能为空！")
    @Length(max = 20, message = "状态长度超出了最大限制！")
    private String status = "NEW"; // 状态
	
    private Date beginTime; // 开始时间
	
    private Date endTime; // 结束时间
	
    private Double progress; // 进度
	
    @Length(max = 100, message = "备注长度超出了最大限制！")
    private String comment; // 备注

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

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public Date getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getProgress() {
		return progress;
	}
	public void setProgress(Double progress) {
		this.progress = progress;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	// 获取状态的描述
	public String getStatusLabel() {
		return STATUS.getLabel(status);
	}

	// 返回Model名称
	@Override
	public String getModelName(){
		return "定时任务";
	}
}