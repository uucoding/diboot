package com.diboot.web.service.impl;

import java.util.List;

import com.diboot.framework.utils.Query;
import com.diboot.web.model.EditableText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diboot.framework.utils.V;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.service.impl.BaseServiceImpl;

import com.diboot.web.service.EditableTextService;
import com.diboot.web.service.mapper.EditableTextMapper;

/***
* 编辑器文本相关操作Service
* @author Mazc@dibo.ltd
* @version 2018-07-02
* Copyright © www.dibo.ltd
*/
@Service
public class EditableTextServiceImpl extends BaseServiceImpl implements EditableTextService{
	private static final Logger logger = LoggerFactory.getLogger(EditableTextServiceImpl.class);
	
	@Autowired
	EditableTextMapper editableTextMapper;

	@Override
	public boolean upsert(EditableText model){
		Query query = new Query();
		query.add(EditableText.F.relObjType, model.getRelObjType())
				.add(EditableText.F.relObjId, model.getRelObjId())
				.add(EditableText.F.field, model.getField());
		List<EditableText> modelList = editableTextMapper.getList(query.build());
		if (V.isEmpty(modelList)){
			return this.createModel(model);
		}

		EditableText oldModel = modelList.get(0);
		oldModel.setSrcContent(model.getSrcContent());
		return this.updateModel(oldModel);
	}
	
	@Override
	protected BaseMapper getMapper() {
		return editableTextMapper;
	}
}