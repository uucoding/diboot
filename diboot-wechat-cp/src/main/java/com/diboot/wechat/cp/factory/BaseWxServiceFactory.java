package com.diboot.wechat.cp.factory;

import com.diboot.framework.model.SystemConfig;
import com.diboot.framework.service.SystemConfigService;
import com.diboot.framework.utils.Query;
import com.diboot.framework.utils.V;
import me.chanjar.weixin.cp.api.WxCpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * 企业微信Service工厂类
 */
@Component
public class BaseWxServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(BaseWxServiceFactory.class);

    // 配置-service实例的缓存
    protected Map<String, WxCpService> CONFIG_CPSERVICE_CACHE = new ConcurrentHashMap<>();

    @Autowired
    SystemConfigService systemConfigService;

    /***
     * 获取SystemConfig配置信息
     * @param relObjType
     * @param relObjId
     * @param category
     * @param subcategory
     * @return
     */
    protected SystemConfig getSystemConfig(String relObjType, Long relObjId, String category, String subcategory){
        // 查询
        Query query = new Query()
                .add(SystemConfig.F.relObjType, relObjType)
                .add(SystemConfig.F.relObjId, relObjId)
                .add(SystemConfig.F.category, category)
                .add(SystemConfig.F.subcategory, category);
        List<SystemConfig> configList = systemConfigService.getLimitModelList(query.build(), 1);
        if(V.notEmpty(configList)){
            return configList.get(0);
        }

        logger.warn("企业微信应用" + category + "的配置为空，请检查system_config表中关于企业微信相关的配置信息");
        return null;
    }

    /***
     * 从JSON字符串中获取String
     * @param obj
     * @return
     */
    protected String getStringFromJson(Object obj){
        if(obj == null){
            return null;
        }
        return String.valueOf(obj);
    }

}
