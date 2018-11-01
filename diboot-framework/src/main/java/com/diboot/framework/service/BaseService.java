package com.diboot.framework.service;

import com.diboot.framework.model.BaseModel;

import java.util.List;
import java.util.Map;

/**
 * @author Mazc@dibo.ltd
 * @version 2017/5/5
 * Copyright @ www.dibo.ltd
 */
public interface BaseService{

    /**
     * 获取Model
     * @param pk 主键id/uuid
     * @return
     * @throws Exception
     */
    <T extends BaseModel>T getModel(Object pk);

    /**
     * 创建Model
     * @param model
     * @return
     * @throws Exception
     */
    boolean createModel(BaseModel model);

    /***
     * 批量创建Model
     * @param modelList
     * @return
     * @throws Exception
     */
    <T extends BaseModel> boolean batchCreateModels(List<T> modelList);

    /**
     * 更新Model
     * @param model Model类
     * @param updateFields 指定更新字段
     * @return
     * @throws Exception
     */
    boolean updateModel(BaseModel model, String... updateFields);

    /***
     * 创建或更新Model（model.isNew()==true则新建，==false则更新）
     * @param model
     * @return
     */
    boolean createOrUpdateModel(BaseModel model);

    /**
     * 根据主键删除Model
     * @param pk 主键id/uuid
     * @return
     * @throws Exception
     */
    boolean deleteModel(Object pk);

    /**
     * 按条件删除model
     * @param criteria
     * @return
     * @throws Exception
     */
    boolean deleteModels(Map<String, Object> criteria);

    /**
     * 获取符合条件的model记录总数
     * @param criteria
     * @return
     * @throws Exception
     */
    int getModelListCount(Map<String, Object> criteria);

    /**
     * 获取model列表
     * @param criteria
     * @param page
     * @return
     * @throws Exception
     */
    <T extends BaseModel> List<T> getModelList(Map<String, Object> criteria, int... page);

    /**
     * 获取指定数量的model记录
     * @param criteria
     * @param limitCount
     * @return
     * @throws Exception
     */
    <T extends BaseModel> List<T> getLimitModelList(Map<String, Object> criteria, int limitCount);

    /**
     * 获取符合查询条件的一个Model
     * @param criteria 查询条件
     * @return
     * @throws Exception
     */
    <T extends BaseModel>T getSingleModel(Map<String, Object> criteria);

    /**
     * 获取指定属性的Map列表
     * @param criteria
     * @param loadFields
     * @param page
     * @return
     */
    List<Map<String, Object>> getMapList(Map<String, Object> criteria, String[] loadFields, int... page);

    /***
     * 获取键值对的列表，用于构建select下拉选项等
     * @param criteria
     * @param keyField
     * @param valueField
     * @return
     */
    List<Map<String, Object>> getKeyValuePairList(Map<String, Object> criteria, String keyField, String valueField);

}