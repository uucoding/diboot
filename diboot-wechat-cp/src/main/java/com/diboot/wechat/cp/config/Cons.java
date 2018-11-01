package com.diboot.wechat.cp.config;

import com.diboot.framework.config.BaseCons;

/***
 * 常量
 */
public class Cons extends BaseCons {

    /***
     * 微信配置的类型
     */
    public enum WX_CONFIG_TYPE{
        WX_CP_APP
    }

    /***
     * 微信配置的应用类别
     */
    public enum WX_CONFIG_CATEGORY{
        DEFAULT, //默认
        MSG //消息
    }

    public enum WX_CP_CONFIG_KEY{
        CorpId,
        AgentId,
        Secret,
        Token,
        EncodingAESKey
    }
}
