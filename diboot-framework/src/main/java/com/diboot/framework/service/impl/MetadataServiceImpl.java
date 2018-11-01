package com.diboot.framework.service.impl;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.Metadata;
import com.diboot.framework.service.MetadataService;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.service.mapper.MetadataMapper;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.Query;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Mazc@dibo.ltd
 * @version 2017/4/17
 * Copyright @ www.dibo.ltd
 */
@Service("metadataService")
public class MetadataServiceImpl extends BaseServiceImpl implements MetadataService {
    private static final Logger logger = LoggerFactory.getLogger(MetadataServiceImpl.class);

    @Autowired
    private MetadataMapper metadataMapper;

    @Override
    protected BaseMapper getMapper(){return metadataMapper;}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createModel(BaseModel model) {
        boolean success = super.createModel(model);
        if(success){
            // 保存子项
            Metadata metadata = (Metadata)model;
            if(V.notEmpty(metadata.getChildren())){
                List<Metadata> modelList = metadata.getChildren();
                for(Metadata child : modelList){
                    child.setParentId(metadata.getId());
                    child.setType(metadata.getType());
                    child.setSystem(metadata.isSystem());
                }
                if(!modelList.isEmpty()){
                    success = batchCreateModels(modelList);
                }
            }
        }
        return success;
    }

    @Override
    public Metadata getModel(Object id) {
        Metadata model = metadataMapper.get(id);
        if(model.isTopLevel()){
            Map<String, Object> criteria = super.newCriteria(Metadata.F.parentId, id);
            List<Metadata> children = metadataMapper.getList(criteria);
            model.setChildren(children);
        }
        return model;
    }

    @Override
    public List<Metadata> getChildrenByType(String type) {
        Query query = new Query(Metadata.F.type, type);
        query.addGT(Metadata.F.parentId, 0);
        List<Metadata> children = metadataMapper.getList(query.toMap());
        return children;
    }

    @Override
    public List<Map<String, Object>> getKeyValuePairListByType(String type) {
        Query query = new Query(Metadata.F.type, type);
        query.addGT(Metadata.F.parentId, 0);
        return getKeyValuePairList(query.toMap(), Metadata.F.itemValue, Metadata.F.itemName);
    }

    @Override
    public List<Map<String, Object>> getNameTypePairList() {
        return metadataMapper.getNameTypePairList();
    }

    @Override
    public Map<Object, String> getValueKeyMapByType(String type) {
        List<Map<String, Object>> mapList = getKeyValuePairListByType(type);
        return BaseHelper.convert2VKMap(mapList);
    }

}
