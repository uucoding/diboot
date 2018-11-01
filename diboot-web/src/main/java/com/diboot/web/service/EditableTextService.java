package com.diboot.web.service;

import com.diboot.web.model.EditableText;
import com.diboot.framework.service.BaseService;
import org.springframework.stereotype.Component;

/***
 * 编辑器文本相关操作Service
 * @author Mazc@dibo.ltd
 * @version 2018-07-02
 * Copyright © www.dibo.ltd
*/
@Component
public interface EditableTextService extends BaseService{

    /**
     * 新建或更新源内容
     * @param model
     * @return
     */
    boolean upsert(EditableText model);
}