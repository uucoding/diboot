package com.diboot.components.msg.wechat;

import com.diboot.components.msg.sms.config.MsgConfig;
import com.diboot.components.utils.HttpUtils;
import com.diboot.framework.utils.JSON;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 微信Token缓存辅助类
 * @author Mazc@dibo.ltd
 * @version 2017/9/14
 * Copyright @ www.dibo.ltd
 */
public class TokenHelper {
    private static final Logger logger = LoggerFactory.getLogger(TokenHelper.class);

    /**
     * 最近的token及最近token更新时间
     */
    private static String LATEST_TOKEN = null;
    private static long LATEST_TOKEN_TIMESTAMP = 0L;

    /**
     * 上次token的时间戳在110分钟内，不需要重新获取token；否则重新获取token
     */
    private static long TOKEN_TIMEOUT = 100 * 60 * 1000;

    private static final String WECHAT_CORPID = MsgConfig.getProperty("wechat.corpID");
    private static final String WECHAT_SECRET = MsgConfig.getProperty("wechat.chat.secret");

    /**
     * 获取Token的请求链接地址
     */
    private static final String GET_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+WECHAT_CORPID+"&corpsecret="+WECHAT_SECRET;

    /**
     * 获取AccessToken
     * @return
     * @throws IOException
     */
    public static String getAccessToken(){
        long curStamp = System.currentTimeMillis();
        if (V.notEmpty(LATEST_TOKEN)) {
            // 上次token的时间戳在100分钟内，不需要重新获取token；否则重新获取token
            if ((curStamp - LATEST_TOKEN_TIMESTAMP) < TOKEN_TIMEOUT) {
                logger.info("[WeChat]获取缓存的token: " + LATEST_TOKEN);
                return LATEST_TOKEN;
            }
        }
        // 重新获取token
        String jsonStr = HttpUtils.getUrl(GET_TOKEN_URL);
        if (V.notEmpty(jsonStr)){
            Map map = JSON.toMap(jsonStr);
            LATEST_TOKEN = (String) map.get("access_token");
            if (V.notEmpty(LATEST_TOKEN)) {
                LATEST_TOKEN_TIMESTAMP = curStamp;
                logger.info("[WeChat]获取信息成功，AccessToken:" + LATEST_TOKEN);
            }
            else{
                String error = "[WeChat]获取AccessToken失败: Code：" + map.get("errcode") + "; " + "错误信息：" + map.get("errmsg") + "; corpID: " + WECHAT_CORPID;
                logger.error(error);
            }
        }
        else {
            logger.error("[WeChat]获取AccessToken失败! 返回null");
        }
        return LATEST_TOKEN;
    }

}
