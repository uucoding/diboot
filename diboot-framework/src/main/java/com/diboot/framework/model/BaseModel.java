package com.diboot.framework.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.diboot.framework.utils.JSON;
import com.diboot.framework.utils.V;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/***
 * Dibo Model父类
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
public abstract class BaseModel implements Serializable{
	private static final long serialVersionUID = 203L;

	/***
	 * ID类型
	 */
	public static enum PK_TYPE {
		// 数据库自增ID
		DBAI,
		// 系统生成有序ID
		SYSGI,
		// 系统生成无序UUID
		UUID,
		//联合主键
		COMP
	}

	private Long id; // Long型id

	private String uuid; // String全局id

	private Date createTime; // 创建时间

	private Date updateTime; // 修改时间

	private Object createBy; // 创建人

    private boolean active = true; // 是否有效

	private Map<String, Object> extdataMap;

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F{ public static final String
		id = "id",
		uuid = "uuid",
		createTime = "createTime",
		createBy = "createBy",
		updateTime = "updateTime",
		creatorName = "creatorName",
		active = "active",
		extdata = "extdata" // 扩展数据字段，关联字段存入该扩展字段
	;}

	/***
	 * 获取Model的Long型主键
	 * @return
	 */
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/***
	 * 获取String类型主键
	 * @return
	 */
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Object getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Object createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public String getExtdata() {
    	if(V.isEmpty(this.extdataMap)){
    		return null;
		}
		return JSON.toJSONString(this.extdataMap);
	}

	public void setExtdata(String extdata) {
		this.extdataMap = JSON.toLinkedHashMap(extdata);
	}

	/**
	 * 获取extdata转换后的map对象
	 * @return
	 */
	@JSONField(serialize = false)
	public LinkedHashMap<String, Object> getExtdataMap(){
		return (LinkedHashMap)this.extdataMap;
	}

	/***
	 * 将属性及值转换为json字段串保存至extdata
	 * @param key
	 * @param value
	 */
	public void addToJson(String key, Object value){
		if(V.isEmpty(this.extdataMap)){
			this.extdataMap = new LinkedHashMap<>(8);
		}
		this.extdataMap.put(key, value);
	}

	/***
	 *  从extdata的json字段串中提取某属性的值
	 * @param key
	 * @return
	 */
	public Object getFromJson(String key){
		if(V.isEmpty(this.extdataMap)){
			return null;
		}
		return this.extdataMap.get(key);
	}

	/**
	 * 存储于extData的扩展属性
	 * @return
	 */
	public String getCreatorName() {
		Object creatorName = getFromJson(F.creatorName);
		if(creatorName != null){
			return (String) creatorName;
		}
		return null;
	}
	public void setCreatorName(String creatorName) {
		addToJson(F.creatorName, creatorName);
	}

	/**
	 * 是否为未保存(未分配id)的新对象
 	 */
	@JSONField(serialize = false)
	public boolean isNew(){
		if(!PK_TYPE.UUID.equals(getPkType())){
			return this.id == null;
		}
		return V.isEmpty(this.uuid);
	}

	/***
	 * 获取主键
	 * @return
	 */
	public Object getPk(){
		if(!PK_TYPE.UUID.equals(getPkType())){
			return getId();
		}
		return getUuid();
	}

	/***
	 * 获取主键，转换成字符串，避免JS数字只支持17位的精度丢失的坑
	 * @return
	 */
	public String getPkString(){
		if(!PK_TYPE.UUID.equals(getPkType())){
			if(getId() != null){
				return String.valueOf(getId());
			}
			else{
				return null;
			}
		}
		return getUuid();
	}

	/***
	 * ID生成类型，用于保存前生成所需id
	 * 默认数据库自增类型
	 * @return
	 */
	@JSONField(serialize = false)
	public PK_TYPE getPkType(){
		return PK_TYPE.DBAI;
	}

	/***
	 * 获取Model的显示名称
	 * @return
	 */
	public String getModelName(){
		// 默认返回model的class名称
		return this.getClass().getSimpleName();
	}

	/***
	 * model对象转为map
	 * @return
	 */
	public Map<String, Object> toMap(){
		String jsonStr = JSON.stringify(this);
		return JSON.toMap(jsonStr);
	}

	/**
	 * model对象转为String
	 * @return
	 */
	@Override
	public String toString(){
		return this.getClass().getSimpleName()+ "("+getModelName()+")" + getPk();
	}

}
