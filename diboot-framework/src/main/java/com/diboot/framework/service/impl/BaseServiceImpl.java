package com.diboot.framework.service.impl;

import com.diboot.framework.async.AsyncLogger;
import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.TraceLog;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/***
 * CRUD通用接口实现类
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
@Service
public abstract class BaseServiceImpl implements BaseService {
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	private AsyncLogger asyncLogger;

	public static final String OFFSET = "OFFSET";
	public static final String COUNT = "COUNT";

    /***
     * ID生成器
     */
	private static IdGenerator idGenerator;
	protected IdGenerator getIdGenerator(){
		if(idGenerator == null){
			idGenerator = new IdGenerator();
		}
		return idGenerator;
	}

    /***
     * 获取异步日志logger
      */
	private AsyncLogger getAsyncLogger(){
		if(!AsyncLogger.isEnabledTraceLog){
			return null;
		}
		if(asyncLogger == null){
			asyncLogger = (AsyncLogger) ContextHelper.getBean("asyncLogger");
		}
		return asyncLogger;
	}

	/**
	 * 获取Mapper
	 * @return
	 */
	protected abstract BaseMapper getMapper();

	@Override
	public <T extends BaseModel> T getModel(Object pk){
		return getMapper().get(pk);
	}

	/***
	 * 为非数据库自增类型ID的Model 生成ID
	 * @param model
	 */
	protected void generateIdFor(BaseModel model){
		// 需要程序生成ID
		if(model.isNew()){
			// 程序生成有序Long型ID
			if(BaseModel.PK_TYPE.SYSGI.equals(model.getPkType())){
				model.setId(getIdGenerator().nextId());
			}
			// 设置UUID
			else if(BaseModel.PK_TYPE.UUID.equals(model.getPkType())){
				model.setUuid(S.newUuid());
			}
		}
	}

	@Override
	public boolean createModel(BaseModel model) {
		if(model == null){
			logger.warn("调用错误: model为null");
			return false;
		}
		// 非数据库自增ID类型，由系统生成id
		if(!BaseModel.PK_TYPE.DBAI.equals(model.getPkType()) && model.isNew()){
			generateIdFor(model);
		}
		return getMapper().create(model) > 0 || model.getPk() != null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public <T extends BaseModel> boolean batchCreateModels(List<T> modelList){
		if(modelList == null){
			logger.warn("调用错误: modelList为null");
			return false;
		}
		// 插入前批量生成id
		BaseModel model = modelList.get(0);
		if(!BaseModel.PK_TYPE.DBAI.equals(model.getPkType())){
			for(BaseModel m : modelList){
				generateIdFor(m);
			}
		}
		// 分批批量插入
		boolean success = false;
		Iterator<List> batchIter = new BatchIterator(modelList, 100);
		while (batchIter.hasNext()) {
			List currentBatch = batchIter.next();
			success = getMapper().batchCreate(currentBatch) > 0;
		}

		return success;
	}

	@Override
	public boolean updateModel(BaseModel model, String... updateFields) {
		Map<String, Boolean> fields = convertArray2Map(model, updateFields);

		// 记录数据变更日志
		BaseModel oldModel = null;
		if(getAsyncLogger() != null){
			oldModel = getModel(model.getPk());
			// 解决creatorName被覆盖的问题，此修改有可能会导致extData无法被设为空， TODO 后续优化
			if(oldModel != null && oldModel.getExtdata() != null && V.isEmpty(model.getExtdata())){
				model.setExtdata(oldModel.getExtdata());
			}
		}
		boolean success = getMapper().update(model, fields) > 0;
		if(success){
			if(getAsyncLogger() != null){
				BaseUser user = BaseHelper.getCurrentUser();
				String comment = "更新字段: " + (fields!=null? S.join(updateFields) : "*");
				getAsyncLogger().saveTraceLog(user, TraceLog.OPERATION.UPDATE, model, oldModel, fields!=null? fields.keySet():null, comment);
			}
		}
		else{
			logger.warn("更新操作失败！model="+model.getClass().getSimpleName()+":"+model.getPk());
		}
		return success;
	}

	@Override
	public boolean createOrUpdateModel(BaseModel model){
		if(model.isNew()){
			return createModel(model);
		}
		else{
			return updateModel(model);
		}
	}

	@Override
	public boolean deleteModel(Object pk) {
		boolean success = getMapper().delete(pk) > 0;
		// 记录数据变更日志
		if(success){
			if(getAsyncLogger() != null){
				BaseUser user = BaseHelper.getCurrentUser();
				String objFlag = getMapper() != null? getMapper().getClass().getSimpleName() + ".delete() : " : "";
				String comment = "删除数据: " + objFlag + String.valueOf(pk);
				getAsyncLogger().saveTraceLog(user, TraceLog.OPERATION.DELETE, null, null, null, comment);
			}
		}
		else{
			String objFlag = getMapper() != null? getMapper().getClass().getSimpleName() + ".delete() : " : "";
			logger.warn("删除操作失败！"+objFlag + String.valueOf(pk));
		}

		return success;
	}

	@Override
	public boolean deleteModels(Map<String, Object> criteria) {
		if(criteria == null){
			logger.warn("阻止可能的全表删除操作！");
			return false;
		}
		boolean success = getMapper().deleteModels(criteria) > 0;
		// 记录数据变更日志
		if(success){
			if(getAsyncLogger() != null){
				BaseUser user = BaseHelper.getCurrentUser();
				String objFlag = getMapper() != null? getMapper().getClass().getSimpleName() + ".deleteModels() : " : "";
				String comment = "删除数据: " + objFlag + JSON.toJSONString(criteria);
				getAsyncLogger().saveTraceLog(user, TraceLog.OPERATION.DELETE, null, null, null, comment);
			}
		}
		else{
			String objFlag = getMapper() != null? getMapper().getClass().getSimpleName() + ".deleteModels() : " : "";
			logger.warn("删除操作失败！"+objFlag + JSON.toJSONString(criteria));
		}
		return success;
	}

	@Override
	public int getModelListCount(Map<String, Object> criteria) {
		if(criteria == null){
			criteria = new HashMap<>(4);
		}
		return getMapper().getListCount(criteria);
	}

	@Override
	public <T extends BaseModel> List<T> getModelList(
			Map<String, Object> criteria, int... pages) {
		criteria = attachPagination(criteria, pages);
		List<T> list = getMapper().getList(criteria);
		if(list != null && list.size() > 500){
			logger.warn("数据查询结果过多，记录数为 "+list.size()+"条，请检查调用是否合理！: criteria="
					+ (V.notEmpty(criteria) ? criteria.toString() : null));
		}
		return list;
	}

	@Override
	public <T extends BaseModel> List<T> getLimitModelList(Map<String, Object> criteria, int limitCount){
		if(criteria == null){
			criteria = new HashMap<>(8);
		}
		criteria.put(COUNT, limitCount);
		return getModelList(criteria);
	}

	@Override
	public <T extends BaseModel>T getSingleModel(Map<String, Object> criteria){
		List<T> modelList = getLimitModelList(criteria, 1);
		if(V.notEmpty(modelList)){
			return modelList.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getMapList(Map<String, Object> criteria, String[] loadFields, int... pages) {
		criteria = attachPagination(criteria, pages);
		// 指定加载哪些字段
		Map<String, Boolean> fields = convertArray2Map(null, loadFields);
		List<Map<String, Object>> list = getMapper().getMapList(criteria, fields);
		if(V.notEmpty(list)){
			if(list.size() > 500){
				logger.warn("数据查询结果过多，记录数为 "+list.size()+"条，请检查调用是否合理！: criteria=" +
						(V.notEmpty(criteria) ? criteria.toString() : null));
			}
			//转换Map中的key
			Map<String, String> exchangeKeys = getExchangeKeys(list.get(0).keySet(), loadFields);
			if(V.notEmpty(exchangeKeys)){
				for(Map<String, Object> map : list){
					for(Map.Entry<String, String> entry  : exchangeKeys.entrySet()){
						// 将原Map中的key替换为新的key
						map.put(entry.getValue(), map.get(entry.getKey()));
						map.remove(entry.getKey());
					}
				}
			}
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getKeyValuePairList(Map<String, Object> criteria, String valueField, String keyField) {
		List<Map<String, Object>> options = getMapList(criteria, new String[]{valueField, keyField});
		if(V.notEmpty(options) && options.size() > 100){
			logger.warn("getKeyValuePairList查询结果超过100条，请检查调用是否合理: criteria="
					+ (V.notEmpty(criteria) ? criteria.toString() : null));
		}
		return BeanUtils.convert2KVMapList(options, valueField, keyField);
	}

	/***
	 * 附加LIMIT属性值
	 * @param criteria
	 * @param pages
	 */
	protected Map<String, Object> attachPagination(Map<String, Object> criteria, int... pages){
		if(criteria == null){
			criteria = new HashMap<>(8);
		}
		if(pages != null && pages.length > 0){
			int pageSize = pages.length > 1? pages[1] : BaseConfig.getPageSize();
			criteria.put(OFFSET, pageSize * (pages[0] - 1));
			criteria.put(COUNT, pageSize);
		}
		return criteria;
	}

	/***
	 * new查询条件
	 * @param key
	 * @param value
	 * @return
	 */
	protected Map<String, Object> newCriteria(String key, Object value) {
		Map<String, Object> criteria = new HashMap<>(4);
		criteria.put(key, value);
		return criteria;
	}

	/***
	 * 转换数组到Map
	 * @param updateFields
	 * @return
	 */
	protected Map<String, Boolean> convertArray2Map(BaseModel model, String... updateFields){
		Map<String, Boolean> fields = null;
		if(V.notEmpty(updateFields)){
			fields = new HashMap(updateFields.length);
			for(String f : updateFields){
				fields.put(f, true);
			}
			// 更新扩展数据字段
			if(model != null && V.notEmpty(model.getExtdata())){
				fields.put(BaseModel.F.extdata, true);
			}
		}

		return fields;
	}

	/***
	 * 获取需要转换的Keys
	 * @param keySets
	 * @param loadFields
	 * @return
	 */
	private Map<String, String> getExchangeKeys(Set<String> keySets, String[] loadFields){
		Map<String, String> exchangeKeys = null;
		for(String key : keySets){
			if(key.contains("_")){
				for(String newKey : loadFields){
					if(newKey.equalsIgnoreCase(key.replaceAll("_", ""))){
						if(exchangeKeys == null){
							exchangeKeys = new HashMap<>(8);
						}
						exchangeKeys.put(key, newKey);
						break;
					}
				}
			}
		}
		return exchangeKeys;
	}
}