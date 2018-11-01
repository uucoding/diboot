package com.diboot.components.service;

import com.diboot.framework.service.BaseService;
import com.diboot.components.model.MessageTmpl;
import org.springframework.stereotype.Component;

/***
 * 消息模板相关操作Service
 * @author Mazc@dibo.ltd
 * @version 2017-09-14
 * Copyright @ www.dibo.ltd
*/
@Component
public interface MessageTmplService extends BaseService {

    MessageTmpl getMsgTmpl(String tmplCode);

    String[] getTmplVaribles(String tmplCode);

}