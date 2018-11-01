package com.diboot.framework.timer;

import com.diboot.framework.model.TimerTask;

/***
 * 任务执行者接口
 * @author Mazc@dibo.ltd
 * @version 2017年11月17日
 * Copyright@www.Dibo.ltd
 */
public interface TaskExecutor {

	/***
	 * 执行者beanId
	 * @return
	 */
	String getExecutor();

	/***
	 * 任务类型
	 * @return
	 */
	String getType();

	/***
	 * 是否可见
	 * @return
	 */
	boolean isVisible();

	/***
	 * 任务执行
	 * @return
	 */
	boolean doExecute(TimerTask timerTask);
	
}