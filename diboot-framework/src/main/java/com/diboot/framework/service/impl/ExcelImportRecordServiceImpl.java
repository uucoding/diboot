package com.diboot.framework.service.impl;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.ExcelImportRecord;
import com.diboot.framework.service.ExcelImportRecordService;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.service.mapper.ExcelImportRecordMapper;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/***
* 导入记录相关操作Service
* @author Mazc@dibo.ltd
* @version 2018-06-11
* Copyright © www.dibo.ltd
*/
@Service("excelImportRecordService")
public class ExcelImportRecordServiceImpl extends BaseServiceImpl implements ExcelImportRecordService {
	private static final Logger logger = LoggerFactory.getLogger(ExcelImportRecordServiceImpl.class);
	
	@Autowired
	ExcelImportRecordMapper excelImportRecordMapper;
	
	@Override
	protected BaseMapper getMapper() {
		return excelImportRecordMapper;
	}

	@Override
	public <T extends BaseModel> boolean batchCreateRecords(String fileUuid, List<T> modelList) {
		if(V.isEmpty(modelList)){
			return true;
		}
		List<ExcelImportRecord> recordList = new ArrayList<>(modelList.size());
		for(BaseModel model : modelList){
			ExcelImportRecord record = new ExcelImportRecord();
			record.setFileUuid(fileUuid);
			record.setRelObjType(model.getClass().getSimpleName());
			if(BaseModel.PK_TYPE.UUID.equals(record.getPkType())){
				record.setRelObjUid(model.getUuid());
			}
			else{
				record.setRelObjId(model.getId());
			}
			record.setCreateBy(model.getCreateBy());
			record.setCreatorName(model.getCreatorName());
			if(record.getCreateBy() == null){
				record.setCreateBy(0L);
			}
			recordList.add(record);
		}
		// 批量创建
		return batchCreateModels(recordList);
	}
}