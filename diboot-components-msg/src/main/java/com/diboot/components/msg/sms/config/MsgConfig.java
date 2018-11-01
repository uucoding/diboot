package com.diboot.components.msg.sms.config;

import com.diboot.components.config.MsgCons;
import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.utils.V;

import java.util.Map;

/**
 * 系统配置
 * @author Mazc@dibo.ltd
 * @version 2018/1/31
 * Copyright © www.dibo.ltd
 */
public class MsgConfig extends BaseConfig {

    // 默认配置文件
    private static final String CONFIG_FILE_NAME = "msg.properties";

    /***
     * 从msg.properties配置文件中获取消息组件相关配置
     * @param key
     * @return
     */
    public static String getProperty(String key){
        return getProperty(key, CONFIG_FILE_NAME);
    }

    /***
     * 从msg.properties配置文件和数据库查询配置值
     * @param subcategory
     * @param key
     * @return
     */
    public static String getValueFromFileAndDb(MsgCons.SUBCATEGORY subcategory, String key){
        String value = getProperty(key, CONFIG_FILE_NAME);
        if(value == null){
            Map configMap = getSystemConfigMap(MsgCons.CATEGORY, subcategory.name());
            if(V.notEmpty(configMap)){
                value = String.valueOf(configMap.get(key));
            }
        }
        return value;
    }

}
