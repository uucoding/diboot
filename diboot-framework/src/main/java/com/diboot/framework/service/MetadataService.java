package com.diboot.framework.service;

import com.diboot.framework.model.Metadata;

import java.util.List;
import java.util.Map;

/**
 * 元数据相关操作Service
 * @author Mazc@dibo.ltd
 * @version 2017/4/18
 * Copyright @ www.dibo.ltd
 */
public interface MetadataService extends BaseService {
    /**
     * 获取元数据model及子项
     * @param id
     * @return
     */
    @Override
    Metadata getModel(Object id);

    /**
     * 获取某类型元数据的子项列表
     * @param type
     * @return
     */
    List<Metadata> getChildrenByType(String type);

    /**
     * 获取某种类型的元数据的子项K-V对
     * @param type
     * @return
     */
    List<Map<String, Object>> getKeyValuePairListByType(String type);

    /**
     * 获取名称类型对的列表
     * @return
     */
    List<Map<String, Object>> getNameTypePairList();

    /**
     * 获取到值和名称的map
     * @param type
     * @return
     */
    Map<Object, String> getValueKeyMapByType(String type);

}
