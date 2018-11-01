package com.diboot.framework.model;

import com.diboot.framework.config.Status;
import com.diboot.framework.utils.V;

import java.io.Serializable;

/**
 * JSON返回结果
 * @author Mazc@dibo.ltd
 * @version 2017/8/30
 * Copyright @ www.dibo.ltd
 */
public class JsonResult implements Serializable {
    private static final long serialVersionUID = 1001L;

    private int code; // 状态码

    private String msg; // 消息内容

    private Object data; //返回结果数据

    /**
     * 默认成功，无返回数据
      */
    public JsonResult(){
        this.code = Status.OK.code();
        this.msg = Status.OK.label();
        this.data = null;
    }

    public JsonResult(Status status, String... additionalMsg){
        this.code = status.code();
        this.msg = status.label();
        if(V.notEmpty(additionalMsg)){
            this.msg += ": " + additionalMsg[0];
        }
        this.data = null;
    }

    /**
     * 默认成功，有返回数据
      */
    public JsonResult(Object data, String... additionalMsg){
        this.data = data;
        this.code = Status.OK.code();
        this.msg = Status.OK.label();
        if(V.notEmpty(additionalMsg)){
            this.msg += ": " + additionalMsg[0];
        }
    }

    /**
     * 通用
      */
    public JsonResult(Status status, Object data, String... additionalMsg){
        this.code = status.code();
        this.msg = status.label();
        if(V.notEmpty(additionalMsg)){
            this.msg += ": " + additionalMsg[0];
        }
        this.data = data;
    }

    /***
     * 自定义JsonResult
     * @param code
     * @param label
     * @param data
     */
    public JsonResult(int code, String label, Object data){
        this.code = code;
        this.msg = label;
        this.data = data;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getData() {
        return data;
    }

}