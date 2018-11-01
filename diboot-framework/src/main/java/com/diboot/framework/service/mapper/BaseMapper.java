package com.diboot.framework.service.mapper;

import com.diboot.framework.model.BaseModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/***
 * Dibo Mapper接口父类
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
@Component
public interface BaseMapper {
	/**
	 * 根据主键获取Model
	 * @param id
	 * @param <T>
	 * @return
	 */
	<T extends BaseModel>T get(Object id);

	/**
	 * 创建Model
	 * @param model
	 * @return
	 */
	int create(BaseModel model);

	/**
	 * 批量创建
	 * @param modelList
	 * @return
	 */
	int batchCreate(List<? extends BaseModel> modelList);

	/**
	 * 更新Model
	 * @param model
	 * @param fields
	 * @return
	 */
	int update(@Param("m") BaseModel model, @Param("f") Map<String, Boolean> fields);

	/**
	 * 根据主键删除Model
	 * @param id
	 * @return
	 */
	int delete(Object id);

	/**
	 * 删除models
	 * @param criteria
	 * @return
	 */
	int deleteModels(@Param("c") Map<String, Object> criteria);

	/**
	 * 获取列表总数
	 * @param criteria
	 * @return
	 */
	int getListCount(@Param("c") Map<String, Object> criteria);

	/**
	 * 获取model列表
	 * @param criteria
	 * @param <T>
	 * @return
	 */
	<T extends BaseModel> List<T> getList(@Param("c") Map<String, Object> criteria);

	/**
	 * 获取属性map
	 * @param criteria
	 * @param fields
	 * @return
	 */
	List<Map<String,Object>> getMapList(@Param("c") Map<String, Object> criteria, @Param("f") Map<String, Boolean> fields);

}