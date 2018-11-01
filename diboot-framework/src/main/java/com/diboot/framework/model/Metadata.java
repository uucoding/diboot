package com.diboot.framework.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 元数据定义
 * @author Mazc@dibo.ltd
 * @version 2017/4/17
 * Copyright @ www.dibo.ltd
 */
public class Metadata extends BaseTreeModel<Metadata>{
    private static final long serialVersionUID  = 1301L;

    @NotNull(message = "元数据类型不能为空！")
    @Length(max = 50, message = "元数据类型长度超出了最大限制！")
    private String type; // 元数据类型

    @Length(max = 255, message = "元数据项名称长度超出了最大限制！")
    private String itemName; // 元数据项名称

    @Length(max = 255, message = "元数据项编码长度超出了最大限制！")
    private String itemValue; // 元数据项编码

    @Length(max = 255, message = "备注长度超出了最大限制！")
    private String comment; // 备注

    private int sortId = 1; // 排序号
    private boolean system = true; // 是否为系统预置
    private boolean editable = true; // 是否可编辑

    /***
     * 元数据类型
     */
    public static enum TYPE{
        ROLE // 角色
    }

    /**
     * 构建查询条件所需参数定义
     */
    public static class F extends BaseTreeModel.F{ public static final String
        type = "type",
        itemName = "itemName",
        itemValue = "itemValue",
        sortId = "sortId",
        system = "system",
        editable = "editable"
    ;}

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemValue() {
        return itemValue;
    }
    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public int getSortId() {
        return sortId;
    }
    public void setSortId(int sortId) {
        this.sortId = sortId;
    }
    public boolean isSystem() {
        return system;
    }
    public void setSystem(boolean system) {
        this.system = system;
    }
    public boolean isEditable() {
        return editable;
    }
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public String getModelName(){
        return "原数据";
    }
}
