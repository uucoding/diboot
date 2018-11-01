package com.diboot.framework.service;

import com.diboot.framework.model.BaseModel;
import org.springframework.stereotype.Component;

import java.util.List;

/***
 * 导入记录相关操作Service
 * @author Mazc@dibo.ltd
 * @version 2018-06-11
 * Copyright © www.dibo.ltd
*/
public interface ExcelImportRecordService extends BaseService{

    /***
     * 批量保存导入数据记录
     * @param fileUuid
     * @param modelList
     * @param <T>
     * @return
     */
    <T extends BaseModel> boolean batchCreateRecords(String fileUuid, List<T> modelList);
}