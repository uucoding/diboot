package com.diboot.wechat.cp.factory;

import com.diboot.framework.model.BaseOrg;
import com.diboot.framework.model.SystemConfig;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import com.diboot.wechat.cp.config.Cons;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.WxCpInMemoryConfigStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/***
 * 企业微信Service工厂类
 */
@Component
public class WxCpServiceFactory extends BaseWxServiceFactory{
    private static final Logger logger = LoggerFactory.getLogger(WxCpServiceFactory.class);

    /***
     * 获取默认的企业微信Service
     * @param relObjId
     * @return
     */
    public WxCpService getOrgDefaultCpService(Long relObjId){
        return getCpService(BaseOrg.class.getSimpleName(), relObjId, Cons.WX_CONFIG_CATEGORY.DEFAULT.name());
    }

    /***
     * 获取应用对应的service实例
     * @param relObjType
     * @param relObjId
     * @param category
     * @return
     */
    public WxCpService getCpService(String relObjType, Long relObjId, String category){
        String key = S.join(new String[]{relObjType, String.valueOf(relObjId), category}, ".");
        WxCpService service = CONFIG_CPSERVICE_CACHE.get(key);
        if(service == null){
            SystemConfig config = getSystemConfig(relObjType, relObjId, Cons.WX_CONFIG_TYPE.WX_CP_APP.name(), category);
            if(config != null){
                service = getWxCpServiceImpl(config);
                if(service != null){
                    CONFIG_CPSERVICE_CACHE.put(key, service);
                }
            }
        }
        return service;
    }

    /***
     * 获取应用对应的service实例
     * @param category
     * @return
     */
    public WxCpService getCpService(String category){
        String relObjType = BaseOrg.class.getSimpleName();
        Long relObjId = 0L;
        return getCpService(relObjType, relObjId, category);
    }

    /***
     * 获取企业微信Service实现类
     * @param systemConfig
     * @return
     */
    private WxCpService getWxCpServiceImpl(SystemConfig systemConfig){
        // 初始化实例
        String corpId = getStringFromJson(systemConfig.getFromJson(Cons.WX_CP_CONFIG_KEY.CorpId.name()));
        String secret = getStringFromJson(systemConfig.getFromJson(Cons.WX_CP_CONFIG_KEY.Secret.name()));
        String token = getStringFromJson(systemConfig.getFromJson(Cons.WX_CP_CONFIG_KEY.Token.name()));
        String encodingSecretKey = getStringFromJson(systemConfig.getFromJson(Cons.WX_CP_CONFIG_KEY.EncodingAESKey.name()));
        String agentIdStr = getStringFromJson(systemConfig.getFromJson(Cons.WX_CP_CONFIG_KEY.AgentId.name()));
        if(V.isEmpty(corpId) || V.isEmpty(secret) || V.isEmpty(agentIdStr)){
            logger.warn("企业微信配置信息不完整，请检查！");
            return null;
        }

        // 设置企业微信应用参数
        WxCpInMemoryConfigStorage config = new WxCpInMemoryConfigStorage();
        config.setCorpId(corpId.trim());
        config.setCorpSecret(secret.trim());
        config.setAgentId(Integer.parseInt(agentIdStr));
        config.setToken(V.notEmpty(token)? token.trim() : null);
        config.setAesKey(V.notEmpty(encodingSecretKey)? encodingSecretKey.trim() : null);

        // 实例化
        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(config);

        return wxCpService;
    }

}
