package com.diboot.framework.service;

/**
 * 跟踪日志相关操作Service
 * @author Mazc@dibo.ltd
 * @version 2017/4/18
 * Copyright @ www.dibo.ltd
 */
public interface TraceLogService extends BaseService {
    /***
     * 清理过期日志
     * @param outdate
     * @return
     */
    int clearLogOutdateLogsBefore(String outdate);
}
