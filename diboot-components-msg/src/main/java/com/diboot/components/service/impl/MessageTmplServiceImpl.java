package com.diboot.components.service.impl;

import com.diboot.framework.service.MetadataService;
import com.diboot.framework.service.impl.BaseServiceImpl;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.components.service.MessageTmplService;
import com.diboot.components.model.MessageTmpl;
import com.diboot.components.service.mapper.MessageTmplMapper;
import com.diboot.framework.model.Metadata;
import com.diboot.framework.utils.Query;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
* 消息模板相关操作Service
* @author Mazc@dibo.ltd
* @version 2017-09-14
* Copyright @ www.dibo.ltd
*/
@Service
public class MessageTmplServiceImpl extends BaseServiceImpl implements MessageTmplService {
	private static final Logger logger = LoggerFactory.getLogger(MessageTmplServiceImpl.class);
	
	@Autowired
	MessageTmplMapper messageTmplMapper;

	@Autowired
	MetadataService metadataService;

	/**
	 * 获取微信消息模板
	 * @param tmplCode
	 * @return
	 */
	@Override
	public MessageTmpl getMsgTmpl(String tmplCode){
		Query query = new Query();
		query.add(MessageTmpl.F.code, tmplCode);

		List<MessageTmpl> messageTmplList = this.getModelList(query.build());

		if (V.isEmpty(messageTmplList)){
			return null;
		}
		return messageTmplList.get(0);
	}

	/**
	 * 获取微信消息模板变量
	 * @param tmplCode
	 * @return
	 */
	@Override
	public String[] getTmplVaribles(String tmplCode){
		// 从元数据中获取消息模板变量
		Query query = new Query();
		query.addNotEquals(Metadata.F.parentId, 0L);
		query.add(Metadata.F.type, MessageTmpl.METADATA_TYPE.MESSAGE_TMPL_VARIBLES.name());
		query.add(Metadata.F.itemName, tmplCode);
		List<Metadata> metadataList = metadataService.getModelList(query.build());
		if (V.isEmpty(metadataList)){
			logger.warn("未找到售后微信消息模板变量");
			return null;
		}
		return S.split(metadataList.get(0).getItemValue(), ",");
	}

	@Override
	protected BaseMapper getMapper() {
		return messageTmplMapper;
	}
}