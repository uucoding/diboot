package com.diboot.framework.config;

/**
 * 基础常量定义
 * @author Mazc@dibo.ltd
 * @version 2017/8/12
 * Copyright @ www.dibo.ltd
 */
public class BaseCons {
    /**
     * 默认字符集UTF-8
     */
    public static final String CHARSET_UTF8 = "UTF-8";
    /***
     * ISO-8859-1
     */
    public static final String CHARSET_ISO8859_1 = "ISO8859-1";
    /**
     * url常量
     */
    public static final String URL_LOGIN = "/login";

    public static final String URL_CHANGEPWD = "/changepwd";

    public static final String URL_LOGOUT = "/logout";

    public static final String URL_KEEP_ALIVE = "/ping";

    public static final String URL_WELCOME = "/welcome";

    /**
     * 分隔符
     */
    public static final String SEPARATOR = "__";

    /***
     * 登录失败的最大尝试次数
     */
    public static final int MAX_LOGIN_RETRY_TIMES = 5;

    /***
     * 最大导出数量
     */
    public static final int MAX_SIZE_EXPORT = 1000;

    /***
     * option选项的key
     */
    public static final String KEY = "k";
    /***
     * option选项的value
     */
    public static final String VALUE = "v";

    /**
     * 操作状态元数据
     */
    public static enum OPERATE_STATUS{
        S("成功"),    //成功
        F("失败");    //失败

        private String label;
        OPERATE_STATUS(String label){
            this.label = label;
        }
        public String label(){
            return this.label;
        }
        public static String getLabel(String value){
            for(OPERATE_STATUS op : OPERATE_STATUS.values()){
                if(op.name().equals(value)){
                    return op.label();
                }
            }
            return null;
        }
    }
}
