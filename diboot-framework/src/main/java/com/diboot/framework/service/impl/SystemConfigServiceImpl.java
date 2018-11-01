package com.diboot.framework.service.impl;

import com.diboot.framework.model.SystemConfig;
import com.diboot.framework.service.SystemConfigService;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.service.mapper.SystemConfigMapper;
import com.diboot.framework.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/***
* 系统配置相关操作Service
* @author Mazc@dibo.ltd
* @version 2018-06-04
* Copyright © www.dibo.ltd
*/
@Service("systemConfigService")
public class SystemConfigServiceImpl extends BaseServiceImpl implements SystemConfigService {
	private static final Logger logger = LoggerFactory.getLogger(SystemConfigServiceImpl.class);
	
	@Autowired
	SystemConfigMapper systemConfigMapper;

	@Override
	protected BaseMapper getMapper() {
		return systemConfigMapper;
	}

	@Override
	public Map<String, Object> getConfigMap(String category, String subcategory) {
		Query query = new Query(SystemConfig.F.category, category);
		query.add(SystemConfig.F.subcategory, subcategory);
		SystemConfig config = getSingleModel(query.build());
		if(config != null){
			return config.getExtdataMap();
		}
		return null;
	}

}