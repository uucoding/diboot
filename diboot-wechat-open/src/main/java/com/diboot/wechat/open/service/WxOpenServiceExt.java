package com.diboot.wechat.open.service;

import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.utils.Query;
import com.diboot.framework.utils.V;
import com.diboot.wechat.open.model.WxAuthOpen;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Service
public class WxOpenServiceExt extends WxOpenServiceImpl {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private WxOpenMessageRouter wxOpenMessageRouter;

    private static final String appId = BaseConfig.getProperty("wechat.open.appId");
    private static final String secret = BaseConfig.getProperty("wechat.open.secret");
    private static final String token = BaseConfig.getProperty("wechat.open.token");
    private static final String aesKey = BaseConfig.getProperty("wechat.open.aesKey");

    @Autowired
    private WxConfigStorageService wxConfigStorageService;

    @Autowired
    private WxAuthOpenService wxAuthOpenService;

    @PostConstruct
    public void init() {
        WxOpenInMemoryConfigStorage configStorage = new WxOpenInMemoryConfigStorage();
        configStorage.setComponentAppId(appId);
        configStorage.setComponentAppSecret(secret);
        configStorage.setComponentToken(token);
        configStorage.setComponentAesKey(aesKey);

        // 设置authorizer_refresh_token
        Query query = new Query(WxAuthOpen.F.enabled, true);
        List<WxAuthOpen> wxAuthOpens = wxAuthOpenService.getModelList(query.build());
        if (V.notEmpty(wxAuthOpens)){
            for (WxAuthOpen wxAuthOpen : wxAuthOpens){
                if (V.notEmpty(wxAuthOpen.getAppid()) && V.notEmpty(wxAuthOpen.getAuthorizerRefreshToken())){
                    configStorage.setAuthorizerRefreshToken(wxAuthOpen.getAppid(), wxAuthOpen.getAuthorizerRefreshToken());
                }
            }
        }

        // 如果数据库中存储着有效的componentVerifyTicket，则赋值改ticket
        String componentVerifyTicket = wxConfigStorageService.getOpenComponentVerifyTicket();
        if (V.notEmpty(componentVerifyTicket)){
            configStorage.setComponentVerifyTicket(componentVerifyTicket);
        }

        setWxOpenConfigStorage(configStorage);
        wxOpenMessageRouter = new WxOpenMessageRouter(this);
        wxOpenMessageRouter.rule().handler(new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
                logger.info("\n接收到 {} 公众号请求消息，内容：{}", wxMpService.getWxMpConfigStorage().getAppId(), wxMpXmlMessage);
                return null;
            }
        }).next();
    }
    public WxOpenMessageRouter getWxOpenMessageRouter(){
        return wxOpenMessageRouter;
    }

    /***
     * 刷新authorizer_refresh_token
     * @param appId
     */
    public void refreshAuthorizerRefreshToken(String appId){
        WxAuthOpen wxAuthOpen = wxAuthOpenService.getModelByAppId(appId);
        if (wxAuthOpen != null){
            this.getWxOpenConfigStorage().setAuthorizerRefreshToken(appId, wxAuthOpen.getAuthorizerRefreshToken());
        }
    }
}
