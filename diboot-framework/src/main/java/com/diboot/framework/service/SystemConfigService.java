package com.diboot.framework.service;

import java.util.Map;

/***
 * 系统配置相关操作Service
 * @author Mazc@dibo.ltd
 * @version 2018-06-04
 * Copyright © www.dibo.ltd
*/
public interface SystemConfigService extends BaseService {

    /***
     * 获取指定类型的配置Map
     * @param category 类别
     * @param subcategory 子类别
     * @return 配置参数和值的Map
     */
    Map<String, Object> getConfigMap(String category, String subcategory);

}