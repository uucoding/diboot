package com.diboot.components.service.mapper;

import com.diboot.framework.service.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/***
 * 消息相关Mapper
 * @author Mazc@dibo.ltd
 * @version 2017-09-14
 * Copyright @ www.dibo.ltd
 */
@Mapper
public interface MessageMapper extends BaseMapper {
    /**
     * 查询验证码
     * @param map
     * @return
     */
    int findVerifyCode(Map<String, Object> map);
}