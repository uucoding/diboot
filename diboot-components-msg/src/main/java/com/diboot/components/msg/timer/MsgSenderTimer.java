package com.diboot.components.msg.timer;

import com.diboot.components.model.Message;
import com.diboot.components.service.MessageService;
import com.diboot.framework.utils.D;
import com.diboot.framework.utils.Query;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/***
 * 定时任务操作类
 * @author Mazc@dibo.ltd
 */
@Component
public class MsgSenderTimer {
	private static final Logger logger = LoggerFactory.getLogger(MsgSenderTimer.class);

	@Autowired
	MessageService messageService;

	/***
	 * 每半小时运行一次
	 */
	@Scheduled(cron="0 0/30 * * * ?")
	public void sendScheduleMsg(){
		// 查询定时时间在最近半小时内的消息
		Query query = new Query(Message.F.status, Message.STATUS.NEW.name());
		Date now = new Date();
		query.addGTE(Message.F.scheduleTime, D.addMinutes(now, -30));
		query.addLTE(Message.F.scheduleTime, now);

		List<Message> msgList = messageService.getModelList(query.build());
		if(V.notEmpty(msgList)){
			for(Message message : msgList){
				// 循环发送
				messageService.sendMsg(message);
			}
			logger.info("定时发送消息完成，发送消息数="+msgList.size());
			msgList = null;
		}
	}
}
