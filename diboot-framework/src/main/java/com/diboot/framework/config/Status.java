package com.diboot.framework.config;

/**
 * 状态码定义
 * @author Mazc@dibo.ltd
 * @version 2017/8/30
 * Copyright @ www.dibo.ltd
 */
public enum Status {
    OK(0, "操作成功"),  // 请求处理成功

    WARN_PARTIAL_SUCCESS(1001, "部分成功"), // 部分成功
    WARN_PERFORMANCE_ISSUE(1002, "潜在的性能问题"), // 有性能问题

    FAIL_INVALID_PARAM(4000, "请求参数不匹配"),       // 传入参数不对
    FAIL_INVALID_TOKEN(4001, "Token无效或已过期"),    // token无效
    FAIL_NO_PERMISSION(4003, "没有权限执行该操作"),  // 无权查看
    FAIL_NOT_FOUND(4004, "请求资源不存在"),  // 404 页面不存在
    FAIL_VALIDATION(4005, "提交数据校验不通过"),     // 数据校验不通过
    FAIL_OPERATION(4006, "操作执行失败"),     // 操作执行失败
    FAIL_EXCEPTION(5000, "系统异常");       // 系统异常

    private int code;
    private String label;
    Status(int code, String label){
        this.code = code;
        this.label = label;
    }
    public int code(){
        return this.code;
    }
    public String label(){
        return this.label;
    }
    public static int getCode(String value){
        for(Status eu : Status.values()){
            if(eu.name().equals(value)){
                return eu.code();
            }
        }
        return 0;
    }
    public static String getLabel(String value){
        for(Status eu : Status.values()){
            if(eu.name().equals(value)){
                return eu.label();
            }
        }
        return null;
    }

}
