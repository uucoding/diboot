package com.diboot.wechat.open.service;

import com.diboot.framework.service.BaseService;
import com.diboot.wechat.open.model.WxAuthOpen;
import org.springframework.stereotype.Component;

/***
 * 服务号授权相关操作Service
 * @author Yangz@dibo.ltd
 * @version 2018-06-21
 * Copyright © www.dibo.ltd
*/
@Component
public interface WxAuthOpenService extends BaseService {

    /**
     * 通过appid获取WxAuthOpen
     * @param appid
     * @return
     */
    WxAuthOpen getModelByAppId(String appid);

    /**
     * 创建或更新
     * @param model
     */
    boolean createOrModify(WxAuthOpen model);
}