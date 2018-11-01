package com.diboot.framework.service.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Mazc@dibo.ltd
 * @version 2017/4/18
 * Copyright @ www.dibo.ltd
 */
@Component
public interface MetadataMapper extends BaseMapper{

    /**
     * 获取名称类型对的列表
     * @return
     */
    List<Map<String, Object>> getNameTypePairList();

}
