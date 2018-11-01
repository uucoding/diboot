package com.diboot.framework.model;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;


/**
 * @author Mazc@dibo.ltd
 * @version 2017/4/17
 * Copyright @ www.dibo.ltd
 */
public class OperationLog extends BaseModel{
    private static final long serialVersionUID  = 1501L;
    /***
     * 用户操作类型
     */
    public static enum OPERATION{
        VIEW("查看"), // 查看数据
        CREATE("创建"), //添加数据
        UPDATE("修改"), //修改数据
        DELETE("删除"), //删除数据
        TRIGGER("触发"), // 触发了某些操作
        ERROR("错误"), // 发生错误
        REQUEST("请求"); // 发出请求

        private String label;
        OPERATION(String label){
            this.label = label;
        }
        public String label(){
            return this.label;
        }
        public static String getLabel(String value){
            for(OPERATION op : OPERATION.values()){
                if(op.name().equals(value)){
                    return op.label();
                }
            }
            return null;
        }
    };

    @NotNull(message = "应用不能为空！")
    @Length(max = 16, message = "应用长度超出了最大限制！")
    private String source = "MS"; // 来源应用

    @NotNull(message = "用户ID不能为空！")
    private Object userId; // 用户ID

    @NotNull(message = "用户类型不能为空！")
    @Length(max = 16, message = "用户类型长度超出了最大限制！")
    private String userType = BaseUser.class.getSimpleName(); // 用户类型

    @Length(max = 32, message = "操作长度超出了最大限制！")
    private String operation; // 操作

    @Length(max = 32, message = "操作对象长度超出了最大限制！")
    private String relObjType; // 操作对象

    private String relObjId; // 操作对象ID

    @Length(max = 10240, message = "关联数据长度超出了最大限制！")
    private String relObjData; // 关联数据

    @Length(max = 255, message="请求URL超长")
    private String requestUrl;

    @Length(max = 255, message = "系统备注长度超出了最大限制！")
    private String comment; // 系统备注

    /**
     * 构建查询条件所需参数定义
     */
    public static class F extends BaseModel.F{ public static final String
        source = "source",
        userType = "userType",
        userId = "userId",
        operation = "operation",
        relObjType = "relObjType",
        relObjId = "relObjId",
        relObjData = "relObjData",
        requestUrl = "requestUrl"
    ;}

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public Object getUserId() {
        return userId;
    }
    public void setUserId(Object userId) {
        this.userId = userId;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getRelObjType() {
        return relObjType;
    }
    public void setRelObjType(String relObjType) {
        this.relObjType = relObjType;
    }
    public String getRelObjId() {
        return relObjId;
    }
    public void setRelObjId(String relObjId) {
        this.relObjId = relObjId;
    }
    public String getRelObjData() {
        return relObjData;
    }
    public void setRelObjData(String relObjData) {
        this.relObjData = relObjData;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getRequestUrl() {
        return requestUrl;
    }
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getOperationLabel() {
        return OPERATION.getLabel(operation);
    }
    @Override
    public String getModelName(){
        return "操作日志";
    }
}
