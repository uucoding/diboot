package com.diboot.framework.service.mapper;

import com.diboot.framework.model.BaseAuthUser;
import org.springframework.stereotype.Component;

/***
 * 用户认证相关Mapper
 * @author Mazc@dibo.ltd
 * @version 2018-06-08
 * Copyright © www.dibo.ltd
 */
@Component
public interface BaseAuthUserMapper extends BaseMapper {
    BaseAuthUser getByOpenid(String openid);
}