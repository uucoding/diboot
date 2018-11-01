package com.diboot.web.timer.task;

import com.diboot.framework.model.TimerTask;
import com.diboot.framework.service.TraceLogService;
import com.diboot.framework.timer.TaskExecutor;
import com.diboot.framework.utils.D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 清理操作日志
 * @author wuy
 * @version 2017/12/27
 */
@Service
public class ClearTraceLogTaskExecutor implements TaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ClearTraceLogTaskExecutor.class);

    @Autowired
    TraceLogService traceLogService;

    @Override
    public String getExecutor() {
        return "clearTraceLogTaskExecutor";
    }

    @Override
    public String getType() {
        return "日志清理";
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public boolean doExecute(TimerTask timerTask) {
        // 清理一个月以前的
        String outdate = D.getDate(new Date(), -30);
        // 执行清理
        int count = traceLogService.clearLogOutdateLogsBefore(outdate);
        logger.info("清理了 "+outdate+" 之前的记录: "+count + " 条.");
        // 失败请写入错误信息到comment字段
        timerTask.setComment("执行失败请返回错误信息！");
        return true;
    }

}
