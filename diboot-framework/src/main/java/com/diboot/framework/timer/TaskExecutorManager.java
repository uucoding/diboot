package com.diboot.framework.timer;

import com.diboot.framework.model.TimerTask;
import com.diboot.framework.service.TimerTaskService;
import com.diboot.framework.utils.ContextHelper;
import com.diboot.framework.utils.Query;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/***
 * 任务执行入口类
 * @author Mazc@dibo.ltd
 * @version 2017年11月17日
 * Copyright@www.Dibo.ltd
 */
@Component
public class TaskExecutorManager {
	private static final Logger logger = LoggerFactory.getLogger(TaskExecutorManager.class);

	@Autowired
	TimerTaskService timerTaskService;

	/***
	 * 处理所有待执行任务
	 */
	public void processAllPendingTimerTasks(){
		// 执行该批次待处理的定时任务
		Query query = new Query();
		query.add(TimerTask.F.status, TimerTask.STATUS.NEW.name());
		query.addLTE(TimerTask.F.scheduleTime, new Date());
		List<TimerTask> timerTasks = timerTaskService.getModelList(query.build());
		if(V.notEmpty(timerTasks)){
			for(TimerTask task : timerTasks){
				try{
					doExecute(task);
					logger.info("TimerTask "+task.getType()+" 执行完成。id="+task.getId());
				}
				catch(Exception e){
					logger.error("执行timerTask异常", e);
				}
			}
			logger.info("成功执行"+timerTasks.size()+"条定时任务！");
		}
	}

	/***
	 * 获取任务执行相应实现类
	 * @param timerTask
	 * @return
	 */
	private TaskExecutor getTaskExecutor(TimerTask timerTask){
		if(V.notEmpty(timerTask.getExecutor())){
			Object executor = ContextHelper.getBean(timerTask.getExecutor());
			if(executor != null){
				return (TaskExecutor)executor;
			}
		}
		logger.warn("未找到定时任务: "+timerTask.getExecutor() + " 对应的实现类，请检查！");
		return null;
	}

	/***
	 * 执行任务处理
	 * @param timerTask
	 */
	 boolean doExecute(TimerTask timerTask){
		TaskExecutor executor = getTaskExecutor(timerTask);
		if(executor != null){
			// 更新timerTask状态为已执行
			timerTask.setStatus(TimerTask.STATUS.DOING.name());
			timerTask.setBeginTime(new Date());
			timerTask.setComment("正在执行");
			timerTaskService.updateModel(timerTask,
					TimerTask.F.status, TimerTask.F.beginTime, TimerTask.F.comment);

			// 执行
			boolean success = executor.doExecute(timerTask);

			// 更新结果
			timerTask.setStatus(success? TimerTask.STATUS.SUCCESS.name() : TimerTask.STATUS.FAIL.name());
			timerTask.setEndTime(new Date());
			if(success){
				timerTask.setProgress(100.0);
				timerTask.setComment("执行完成");
			}
			timerTaskService.updateModel(timerTask,
					TimerTask.F.status, TimerTask.F.endTime, TimerTask.F.comment, TimerTask.F.progress);
			// 返回结果
			return success;
		}
		return false;
	}
}
