package com.diboot.web.timer;

import com.diboot.framework.timer.TaskExecutorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * 定时任务执行类
 * @author Mazc@dibo.ltd
 */
@Component
public class SystemTimer {
	private static final Logger logger = LoggerFactory.getLogger(SystemTimer.class);

	@Autowired
	TaskExecutorManager taskExecutorManager;

	/***
	 * 每10运行一次
	 */
	//@Scheduled(fixedRate = 600000)
	public void timerTaskExecute(){
		logger.info("处理定时任务开始.");
		taskExecutorManager.processAllPendingTimerTasks();
		logger.info("处理定时任务完成.");
	}

}
