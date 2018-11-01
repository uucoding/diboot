package com.diboot.components.service.impl;

import com.diboot.framework.service.impl.BaseServiceImpl;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.components.service.MessageService;
import com.diboot.components.service.MessageTmplService;
import com.diboot.components.service.mapper.MessageMapper;
import com.diboot.components.msg.wechat.Sender;
import com.diboot.components.model.Message;
import com.diboot.components.model.MessageTmpl;
import com.diboot.components.msg.sms.SmsSenderFactory;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.utils.D;
import com.diboot.framework.utils.Query;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/***
* 消息相关操作Service
* @author Mazc@dibo.ltd
* @version 2017-09-14
* Copyright @ www.dibo.ltd
*/
@Service
public class MessageServiceImpl extends BaseServiceImpl implements MessageService {
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Autowired
	MessageMapper messageMapper;

	@Autowired
	MessageTmplService messageTmplService;

	@Autowired
	SmsSenderFactory smsSenderFactory;

	@Override
	protected BaseMapper getMapper() {
		return messageMapper;
	}

	@Override
	public boolean createAndSendMsg(Message msg) {
		boolean success = createModel(msg);
		if(success){
			success = sendMsg(msg);
		}
		return success;
	}

	@Override
	public boolean sendMsg(Message msg) {
		if(msg == null){
			logger.warn("调用错误：发送消息msg参数为空");
			return false;
		}

		// 处理发送
		boolean success = false;
		if(Message.TYPE.EMAIL.name().equalsIgnoreCase(msg.getType())){
			String to = msg.getReceiver();
			String toName = msg.getReceiver();
			String title = msg.getTitle();
			String content = msg.getContent();
			String[] ccEmails = null;
			String[] filePaths = null;
			// 扩展属性
			if(V.notEmpty(msg.getFromJson("toName"))){
				toName = (String) msg.getFromJson("toName");
			}
			if(V.notEmpty(msg.getFromJson("ccEmails"))){
				ccEmails = (String[]) msg.getFromJson("ccEmails");
			}
			if(V.notEmpty(msg.getFromJson("filePaths"))){
				filePaths = (String[]) msg.getFromJson("filePaths");
			}
			try{
				success = com.diboot.components.msg.email.Sender.send(to, toName, ccEmails, title, content, filePaths);
			}
			catch(Exception e){
				logger.error("发送邮件异常, to="+to, e);
			}
			if(success){
				msg.setStatus(Message.STATUS.DELIVRD.name());
				updateModel(msg, Message.F.status);
			}
		}
		else if(Message.TYPE.SMS.name().equalsIgnoreCase(msg.getType())){
			String signCode = null;
			if(V.notEmpty(msg.getFromJson("signCode"))){
				signCode = (String) msg.getFromJson("signCode");
			}
			try{
				String spmsgid = smsSenderFactory.getSmsService().send(msg.getReceiver(), msg.getContent(), signCode);
				if(V.notEmpty(spmsgid)){
					msg.setResponse(spmsgid);
					if(!spmsgid.startsWith("ERROR")){
						success = true;
					}
				}
			}
			catch(Exception e){
				logger.error("发送短信异常, to="+msg.getReceiver(), e);
			}
			if(success){
				msg.setStatus(Message.STATUS.SENT.name());
				updateModel(msg, Message.F.status, Message.F.response);
			}
		}
		else if(Message.TYPE.WECHAT.name().equalsIgnoreCase(msg.getType())){
			if(V.notEmpty(msg.getUrl())){
				String mediaid = null;
				if(V.notEmpty(msg.getFromJson("mediaid"))){
					mediaid = (String) msg.getFromJson("mediaid");
				}
				success = Sender.sendLinkMsg(msg.getSender(), msg.getReceiver(), msg.getTitle(),
						msg.getContent(), msg.getUrl(), mediaid);
			}
			else{
				success = Sender.sendTextMsg(msg.getSender(), msg.getReceiver(), msg.getContent());
			}
			if(success){
				msg.setStatus(Message.STATUS.DELIVRD.name());
				updateModel(msg, Message.F.status);
			}
		}
		else if(Message.TYPE.SYS.name().equalsIgnoreCase(msg.getType())){
			logger.warn("TODO: //发送SYS系统消息。");
		}

		return success;
	}

	@Override
	public boolean sendVerifyCode2Phone(String cellphone) {
		Query query = new Query(MessageTmpl.F.code, MessageTmpl.VERIFICATION_CODE);
		List<MessageTmpl> list = messageTmplService.getLimitModelList(query.build(), 1);
		if(V.isEmpty(list)){
			logger.warn("未找到任何验证码的模板定义，请检查MessageTmpl表中是否存在code=VC的模板定义！");
			return false;
		}
		MessageTmpl tmpl = list.get(0);
		String vcode = S.newRandomNum(6);
		Message msg = new Message();
		msg.setType(Message.TYPE.SMS.name());
		msg.setBusinessType(MessageTmpl.VERIFICATION_CODE);
		msg.setBusinessId(Long.parseLong(vcode));
		msg.setTmplId(tmpl.getId());
		msg.setTitle(tmpl.getTitle());
		String content = tmpl.getContent().replace("{验证码}", vcode);
		msg.setContent(content);
		msg.setReceiver(cellphone);
		boolean success = createModel(msg);
		if(success){
			success = sendMsg(msg);
		}
		return success;
	}

	@Override
	public boolean isVerifyCodeValid(String cellphone, String verifyCode) {
		Query query = new Query(Message.F.receiver, cellphone);
		query.add(Message.F.businessType, MessageTmpl.VERIFICATION_CODE);
		query.add(Message.F.businessId, Long.parseLong(verifyCode));
		query.addGTE(BaseModel.F.createTime, D.addMinutes(new Date(), -30));
		// 查询是否是合法的验证码
		boolean success = messageMapper.findVerifyCode(query.toMap()) > 0;
		return success;
	}

}