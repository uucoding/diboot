package com.diboot.framework.model;

import java.util.List;

/***
 * Dibo Model父类
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
public class BaseTreeModel<T extends BaseTreeModel> extends BaseModel{
	private static final long serialVersionUID = 205L;

	/**
	 * 支持子类构建树形结构
 	 */
	private Long parentId = 0L;
	/**
	 * 上级名称
 	 */
	private String parentName;

	private List<T> children;

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
			parentId = "parentId",
			parentName = "parentName"
	;}

	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<T> getChildren() {
		return children;
	}
	public void setChildren(List<T> children) {
		this.children = children;
	}

	/***
	 * 是否为最上级
	 * @return
	 */
	public boolean isTopLevel(){
		return parentId == null || parentId.longValue() == 0;
	}
}
