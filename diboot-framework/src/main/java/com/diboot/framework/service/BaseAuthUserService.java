package com.diboot.framework.service;

import com.diboot.framework.model.BaseAuthUser;
import org.springframework.stereotype.Component;

/***
 * 用户认证相关操作Service
 * @author Mazc@dibo.ltd
 * @version 2018-06-08
 * Copyright © www.dibo.ltd
*/
public interface BaseAuthUserService extends BaseService {

    BaseAuthUser getModelByOpenid(String openid);
}