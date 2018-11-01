package com.diboot.components.msg.wechat;

import com.diboot.components.msg.sms.config.MsgConfig;
import com.diboot.components.utils.HttpUtils;
import com.diboot.framework.utils.JSON;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * 微信消息发送
 * @author Mazc@dibo.ltd
 * @version 2017/9/14
 * Copyright @ www.dibo.ltd
 */
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    /**
     * 消息发送url
     */
    private static String SEND_URL = "https://qyapi.weixin.qq.com/cgi-bin/chat/send?access_token=";
    /**
     * 单人文本消息
     */
    private static String SINGLE_TEXT_BODY = "{\"receiver\":{\"type\":\"single\",\"id\":\"%s\"},\"sender\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
    /**
     * 单人链接消息
     */
    private static String SINGLE_LINK_BODY = "{\"receiver\":{\"type\":\"single\",\"id\":\"%s\"},\"sender\":\"%s\",\"msgtype\":\"link\",\"link\":{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"thumb_media_id\":\"%s\"}}";
    /**
     * 系统发送人
     */
    private static final String SYS_SENDER = MsgConfig.getProperty("wechat.system.sender");
    /**
     * 发送消息的图标
     */
    private static String SYS_MEDIA_ID = MsgConfig.getProperty("wechat.system.mediaid");
    /**
     * 发送文本类型会话消息
     * {"receiver":{"type": "single","id": "lisi"},"sender": "zhangsan","msgtype": "text","text":{"content": "Holiday Request For Pony(http://xxxxx)"}}
     */
    public static boolean sendTextMsg(String to, String content) {
        return sendTextMsg(SYS_SENDER, to, content);
    }

    /**
     * 发送文本类型会话消息
     * {"receiver":{"type": "single","id": "lisi"},"sender": "zhangsan","msgtype": "text","text":{"content": "Holiday Request For Pony(http://xxxxx)"}}
     */
    public static boolean sendTextMsg(String from, String to, String content) {
        // 构造发送url
        String sendMessageUrl = SEND_URL + TokenHelper.getAccessToken();

        //text消息请求(单聊)
        String outputStr = String.format(SINGLE_TEXT_BODY, to, from, content);

        // 发送
        String jsonStr = HttpUtils.httpRequest(sendMessageUrl, HttpMethod.POST.name(), outputStr);
        //{"errcode":0,"errmsg":"ok"}
        if (V.notEmpty(jsonStr)) {
            Map map = JSON.toMap(jsonStr);
            if("0".equalsIgnoreCase(String.valueOf(map.get("errcode")))){
                logger.info("[WeChat]推送会话文本消息成功: touser="+to);
                return true;
            }
            else{
                logger.info("[WeChat]推送会话文本消息失败: touser="+to+", errmsg=" + map.get("errmsg"));
            }
        }
        return false;
    }

    /***
     * 发送链接类型会话消息
     * {"receiver":{"type": "single","id": "lisi"}, "sender": "zhangsan", "msgtype": "link",
     *  "link":{"title": "title01","description":"link消息描述","url":"http://www.qq.com","thumb_media_id": "177fIcVBfOLYa703hzBByU1EH3_sdp4hyyaxN4Gfdc-o66vG7k-lXgEacQqfuCcJ-VbZnPlUKJDF8ig_8Zgh6-g"}
     * }
     * @param to
     * @param title
     * @param description
     * @param url
     * @return
     */
    public static boolean sendLinkMsg(String to, String title, String description, String url) {
        if(V.isEmpty(SYS_MEDIA_ID)){
            SYS_MEDIA_ID = "";
        }
        return sendLinkMsg(SYS_SENDER, to, title, description, url, SYS_MEDIA_ID);
    }

    /***
     * 发送链接类型会话消息
     * {"receiver":{"type": "single","id": "lisi"}, "sender": "zhangsan", "msgtype": "link",
     *  "link":{"title": "title01","description":"link消息描述","url":"http://www.qq.com","thumb_media_id": "177fIcVBfOLYa703hzBByU1EH3_sdp4hyyaxN4Gfdc-o66vG7k-lXgEacQqfuCcJ-VbZnPlUKJDF8ig_8Zgh6-g"}
     * }
     * @param from
     * @param to
     * @param title
     * @param description
     * @param url
     * @param mediaId
     * @return
     */
    public static boolean sendLinkMsg(String from, String to, String title, String description, String url, String mediaId) {
        // 构造发送url
        String sendMessageUrl = SEND_URL + TokenHelper.getAccessToken();

        //text消息请求(单聊)
        String outputStr = String.format(SINGLE_LINK_BODY, to, from, title, description, url, mediaId);

        // 发送 //{"errcode":0,"errmsg":"ok"}
        String jsonStr = HttpUtils.httpRequest(sendMessageUrl, HttpMethod.POST.name(), outputStr);
        if (V.notEmpty(jsonStr)) {
            Map map = JSON.toMap(jsonStr);
            if("0".equalsIgnoreCase(String.valueOf(map.get("errcode")))){
                logger.info("[WeChat]推送会话链接消息成功: touser="+to);
                return true;
            }
            else{
                logger.info("[WeChat]推送会话链接消息失败: touser="+to+", errmsg=" + map.get("errmsg"));
            }
        }
        return false;
    }

}
