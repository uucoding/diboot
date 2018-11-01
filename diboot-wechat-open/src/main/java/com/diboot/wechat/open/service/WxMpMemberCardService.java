package com.diboot.wechat.open.service;

import com.diboot.wechat.open.model.WxMemberCardInfo;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpCardService;

public interface WxMpMemberCardService extends WxMpCardService {
    final String CARD_CREATE = "https://api.weixin.qq.com/card/create";
    final String CARD_UPDATE = "https://api.weixin.qq.com/card/update";
    final String CARD_MSG_CONTENT = "https://api.weixin.qq.com/card/mpnews/gethtml";

    /***
     * 创建会员卡
     * @param model
     * @return
     * @throws WxErrorException
     */
    boolean createCard(WxMemberCardInfo model) throws WxErrorException;

    /***
     * 更新会员卡
     * @param model
     * @return
     * @throws WxErrorException
     */
    boolean updateCard(WxMemberCardInfo model) throws WxErrorException;

    /***
     * 获取会员卡消息推送的消息内容，群发卡券时使用
     * @param cardId
     * @return
     * @throws WxErrorException
     */
    String getMessageContent(String cardId) throws WxErrorException;

}
