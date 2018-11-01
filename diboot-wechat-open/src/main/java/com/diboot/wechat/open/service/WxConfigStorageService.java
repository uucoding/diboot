package com.diboot.wechat.open.service;


import com.diboot.framework.service.BaseService;
import com.diboot.wechat.open.model.WxConfigStorage;
import org.springframework.stereotype.Component;

/***
 * 微信参数相关操作Service
 * @author Mazc@dibo.ltd
 * @version 2018-06-21
 * Copyright © www.dibo.ltd
*/
@Component
public interface WxConfigStorageService extends BaseService{

    /**
     * 通过type获取wxConfigStorage
     * @param type
     * @return
     */
    WxConfigStorage getModelByType(String type);

    /**
     * 创建或更新
     * @param model
     * @return
     */
    boolean createOrModify(WxConfigStorage model);

    /**
     * 获取开放平台componentVerifyTicket参数
     * @return
     */
    String getOpenComponentVerifyTicket();
}