package com.diboot.rest.model;

import java.util.Date;
import java.util.List;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
* 学生 Model对象定义
* @author Mazc
* @version 2018-08-28
* Copyright © www.dibo.ltd
*/
public class Student extends com.diboot.framework.model.BaseModel{
	private static final long serialVersionUID = 1208096692005502841L;

	/** gendar 字段的关联元数据 */
	public static final String METATYPE_GENDAR = "GENDER";

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		name = "name",
		gendar = "gendar",
		village = "village",
		phone = "phone",
		wechat = "wechat",
		teacherId = "teacherId",
		entryDate = "entryDate",
		examTime = "examTime",
		avatar = "avatar",
		description = "description",
		teacherName = "teacherName"

	;}

	/** 姓名 */
    @NotNull(message = "姓名不能为空！")
    @Length(max = 100, message = "姓名长度超出了最大限制！")
    private String name = "张三";

	/** 性别 */
    @Length(max = 100, message = "性别长度超出了最大限制！")
    private String gendar;

	/** 特长生 */
    private boolean village = false;

	/** 电话 */
    @Length(max = 15, message = "电话长度超出了最大限制！")
    private String phone;

	/** 微信 */
    @Length(max = 100, message = "微信长度超出了最大限制！")
    private String wechat;

	/** 班主任 */
    private Long teacherId;

	/** 入职日期 */
    private Date entryDate;

	/** 测评时间 */
    @NotNull(message = "测评时间不能为空！")
    private Date examTime = null;

	/** 头像 */
    @Length(max = 512, message = "头像长度超出了最大限制！")
    private String avatar;

	/** 描述 */
    @Length(max = 2048, message = "描述长度超出了最大限制！")
    private String description;

    private String descriptionMd;


	// 外键关联字段定义
	/** 关联字段 teacher.name */
	private String teacherName;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGendar() {
		return gendar;
	}
	public void setGendar(String gendar) {
		this.gendar = gendar;
	}
	public boolean isVillage() {
		return village;
	}
	public void setVillage(boolean village) {
		this.village = village;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public Date getExamTime() {
		return examTime;
	}
	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String[] getAvatarList(){
		if (V.notEmpty(avatar)){
			return S.split(avatar, ',');
		}
		return null;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDescriptionMd(String descriptionMd) {
		this.descriptionMd = descriptionMd;
	}
	public String getDescriptionMd() {
		return descriptionMd;
	}
	public String getDescriptionText(){
		if (V.notEmpty(description)){
			return S.cut(S.html2text(description));
		}
		return description;
	}

	// 外键关联字段get/set

	public String getTeacherName(){
		return this.teacherName;
	}
    public void setTeacherName(String teacherName){
		this.teacherName = teacherName;
    }
	@Override
	public String getModelName(){
		return "学生";
	}
}