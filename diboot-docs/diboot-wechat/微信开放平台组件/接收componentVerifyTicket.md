# 接收componentVerifyTicket

## 默认接口
* 微信开放平台在[授权流程技术说明文档](https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1453779503&token=&lang=zh_CN)中第一部分中介绍了推送component_verify_ticket协议。
* 为了简化接收[component_verify_ticket]()参数的过程，我们提供了默认数据接口[/notify/receive_ticket]()，如果将项目中该地址对应的链接配置到开放平台中[授权事件接收URL]()配置中，微信开放平台将会每十分钟向该地址推送具有component_verify_ticket参数的事件。
* 我们的开放平台组件在接收到该推送后，将接收到的[component_verify_ticket]()参数值，新建或更新到数据库中[wx_config_storage]()表内，以供其他应用使用。

## 自定义接口

* 如果使用自定义的接口地址，以及特殊的处理流程，可以参照一下默认代码接收[component_verify_ticket]()参数。

```java
package com.diboot.wechat.open.controller;

import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.utils.S;
import com.diboot.wechat.open.model.WxConfigStorage;
import com.diboot.wechat.open.service.WxConfigStorageService;
import com.diboot.wechat.open.service.WxOpenServiceExt;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notify")
public class NotifyController {
    private static final Logger logger = LoggerFactory.getLogger(NotifyController.class);

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

```
