package com.diboot.framework.service.mapper;

import org.springframework.stereotype.Component;

/**
 * @author Mazc@dibo.ltd
 * @version 2017/4/18
 * Copyright @ www.dibo.ltd
 */
@Component
public interface TraceLogMapper extends OperationLogMapper{

    /**
     * 清理过期日志
     * @param outdate
     * @return
     */
    int clearLogOutdateLogsBefore(String outdate);
}
