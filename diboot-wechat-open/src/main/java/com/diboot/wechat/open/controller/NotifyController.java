package com.diboot.wechat.open.controller;

import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.utils.S;
import com.diboot.wechat.open.model.WxConfigStorage;
import com.diboot.wechat.open.service.WxConfigStorageService;
import com.diboot.wechat.open.service.WxOpenServiceExt;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public class NotifyController {
    private static final Logger logger = LoggerFactory.getLogger(NotifyController.class);

    private static final String HOSTNAME = BaseConfig.getProperty("wechat.host.name");

    private static final String CONTEXT = BaseConfig.getProperty("wechat.host.context");

    @Autowired
    private WxOpenServiceExt wxOpenService;

    @Autowired
    private WxConfigStorageService wxConfigStorageService;

    /**
     * 接收componentVerifyTicket
     * @param requestBody
     * @param timestamp
     * @param nonce
     * @param signature
     * @param encType
     * @param msgSignature
     * @return
     * @throws Exception
     */
    @RequestMapping("/receive_ticket")
    public Object receiveTicket(@RequestBody(required = false) String requestBody, @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce, @RequestParam("signature") String signature,
                                @RequestParam(name = "encrypt_type", required = false) String encType,
                                @RequestParam(name = "msg_signature", required = false) String msgSignature) throws Exception{
        logger.info(
                "\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);
        if (!S.equalsIgnoreCase("aes", encType) || !wxOpenService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        // aes加密的消息
        WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody, wxOpenService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
        logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
        try {
            String out = wxOpenService.getWxOpenComponentService().route(inMessage);

            // 将ticket保存到数据库
            WxConfigStorage wxConfigStorage = wxConfigStorageService.getModelByType(WxConfigStorage.TYPE.WX_OPEN.name());
            wxConfigStorage = wxConfigStorage == null ? new WxConfigStorage() : wxConfigStorage;
            wxConfigStorage.setType(WxConfigStorage.TYPE.WX_OPEN.name());
            wxConfigStorage.addToJson(WxConfigStorage.FIELDS.component_verify_ticket.name(), wxOpenService.getWxOpenConfigStorage().getComponentVerifyTicket());
            wxConfigStorage.addToJson(WxConfigStorage.FIELDS.timestamp.name(), System.currentTimeMillis());
            wxConfigStorageService.createOrModify(wxConfigStorage);

            logger.debug("\n组装回复信息：{}", out);
        } catch (WxErrorException e) {
            logger.error("receive_ticket", e);
        }

        return "success";
    }
}
