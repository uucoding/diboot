package com.diboot.wechat.open.service.mapper;

import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.wechat.open.model.WxAuthOpen;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/***
 * 服务号授权相关Mapper
 * @author Yangz@dibo.ltd
 * @version 2018-06-21
 * Copyright © www.dibo.ltd
 */
@Component
public interface WxAuthOpenMapper extends BaseMapper{
    WxAuthOpen getModelByAppId(@Param("appid") String appid);
}