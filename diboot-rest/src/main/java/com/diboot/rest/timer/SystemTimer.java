package com.diboot.rest.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/***
 * 定时任务操作类
 * @author Mazc@dibo.ltd
 */
@Component
public class SystemTimer {
	private static final Logger logger = LoggerFactory.getLogger(SystemTimer.class);

	/***
	 * 每天凌晨2点执行
	 */
	@Scheduled(cron="0 0 1 ? * *")
	public void example(){
		logger.info("I'm working.");
	}
}
