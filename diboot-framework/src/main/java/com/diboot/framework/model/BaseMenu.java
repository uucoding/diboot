package com.diboot.framework.model;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

/***
 * Dibo Menu-菜单
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
public class BaseMenu extends BaseTreeModel<BaseMenu>{
	private static final long serialVersionUID = 202L;

	public BaseMenu(){
	}
	public BaseMenu(Long id, String name, String icon, String link){
		setId(id);
		this.name = name;
		this.icon = icon;
		this.link = link;
	}
	public BaseMenu(Long id, String type, String name, String icon, String link){
		setId(id);
		this.name = name;
		this.type = type;
		this.icon = icon;
		this.link = link;
	}

	/***
	 * 菜单类型枚举
	 */
	public static enum TYPE{
		PC, //PC
		APP, //APP
		WECHAT, //微信
		API //接口
	}

	/***
	 * 应用枚举
	 */
	public static enum APPLIACTION{
		MS, //后台管理系统
	}

	@NotNull
	private String type = TYPE.PC.name(); //菜单类型

	@NotNull
	private String application = APPLIACTION.MS.name(); //应用

    @NotNull(message = "菜单名称不能为空！")
    @Length(max = 255, message = "菜单名称长度超出了最大限制！")  
    private String name ; // 菜单名称
	
    @Length(max = 255, message = "图标长度超出了最大限制！")  
    private String icon ; // 图标
	
    @Length(max = 255, message = "链接长度超出了最大限制！")  
    private String link ; // 链接
    
    private Integer sortId = 999; // 排序号

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseTreeModel.F{ public static final String
		name = "name",
		application = "application",
		type = "type",
		icon = "icon",
		link = "link",
		sortId = "sortId"
	;}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
    public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Integer getSortId() {
		return sortId;
	}
	public void setSortId(Integer sortId) {
		if(sortId != null){
			this.sortId = sortId;
		}
	}

	@Override
	public String getModelName(){
		return "菜单";
	}
}