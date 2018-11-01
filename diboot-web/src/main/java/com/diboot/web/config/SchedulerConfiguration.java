package com.diboot.web.config;

import com.diboot.framework.timer.BaseSchedulerConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * 定时任务配置
 * @author Mazc@dibo.ltd
 * @version 2018/4/12
 * Copyright © www.dibo.ltd
 */
@Configuration
@EnableScheduling
public class SchedulerConfiguration extends BaseSchedulerConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(2));
    }

}
