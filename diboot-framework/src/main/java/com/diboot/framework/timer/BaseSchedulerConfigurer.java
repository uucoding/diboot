package com.diboot.framework.timer;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 定时任务的默认配置
 * @author Mazc@dibo.ltd
 * @version 2018/4/12
 * Copyright © www.dibo.ltd
 */
public class BaseSchedulerConfigurer implements SchedulingConfigurer{

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(poolTaskScheduler());
    }

    @Bean
    public ThreadPoolTaskScheduler poolTaskScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("Timer");
        scheduler.setPoolSize(10);
        return scheduler;
    }

}
