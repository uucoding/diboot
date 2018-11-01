package com.diboot.components.config;

import com.diboot.framework.config.BaseCons;

import java.util.HashMap;
import java.util.Map;

/***
 * 消息常量定义
 */
public class MsgCons extends BaseCons {

    /***
     * 消息类型: 即对应的系统配置的类别
     */
    public static final String CATEGORY = "MSG";

    /***
     * 消息子类型: 即对应的系统配置子类别定义
     */
    public static enum SUBCATEGORY{
        EMAIL,
        SMS_ETON,
        SMS_SUBMAIL
    }

    /***
     * 微信消息类型
     */
    public static enum MP_MSG_TYPE {
        TEXT,
        IMAGE,
        VOICE,
        VIDEO,
        MUSIC,
        NEWS
    }

    /***
     * 微信消息类型描述
     */
    public static final Map<String, String> MP_MSG_TYPE_LABELS = new HashMap<String, String>(){{
        put(MP_MSG_TYPE.TEXT.name(), "文本消息");
        put(MP_MSG_TYPE.IMAGE.name(), "图片消息");
        put(MP_MSG_TYPE.VOICE.name(), "语音消息");
        put(MP_MSG_TYPE.VIDEO.name(), "视频消息");
        put(MP_MSG_TYPE.MUSIC.name(), "音乐消息");
        put(MP_MSG_TYPE.NEWS.name(), "图文消息");
    }};

    /***
     * 微信消息KEY
     */
    public static final Map<String, String[]> MP_MSG_KEYS = new HashMap<String, String[]>(){{
        put(MP_MSG_TYPE.TEXT.name(), null);
        put(MP_MSG_TYPE.IMAGE.name(), new String[]{"MEDIA_ID"});
        put(MP_MSG_TYPE.VOICE.name(), new String[]{"MEDIA_ID"});
        put(MP_MSG_TYPE.VIDEO.name(), new String[]{"MEDIA_ID", "THUMB_MEDIA_ID"});
        put(MP_MSG_TYPE.MUSIC.name(), new String[]{"THUMB_MEDIA_ID", "MUSIC_URL", "HQ_MUSIC_URL"});
        put(MP_MSG_TYPE.NEWS.name(), new String[]{"link", "picUrl"});
    }};
}