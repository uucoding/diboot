package com.diboot.wechat.open.service.mapper;

import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.wechat.open.model.WxConfigStorage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/***
 * 微信参数相关Mapper
 * @author Mazc@dibo.ltd
 * @version 2018-06-21
 * Copyright © www.dibo.ltd
 */
@Component
public interface WxConfigStorageMapper extends BaseMapper{
    WxConfigStorage getModelByType(@Param("type") String type);
}