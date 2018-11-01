package com.diboot.components.msg.sms;

import com.diboot.components.msg.sms.eton.SmsServiceEtonImpl;
import com.diboot.components.msg.sms.submail.SmsServiceSubmailImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 短信发送
 * @author Mazc@dibo.ltd
 * @version 2017/9/14
 * Copyright @ www.dibo.ltd
 */
@Component
public class SmsSenderFactory {
    private static final Logger logger = LoggerFactory.getLogger(SmsSenderFactory.class);

    /**
     * 实现类
     */
    private SmsService smsService;

    /**
     * 获取实现类
     * @return
     */
    public SmsService getSmsService(){
        if(SmsServiceEtonImpl.isAvailable()){
            smsService = new SmsServiceEtonImpl();
        }
        else if(SmsServiceSubmailImpl.isAvailable()){
            smsService = new SmsServiceSubmailImpl();
        }
        else{
            logger.info("无可用的短信发送服务！");
        }
        return smsService;
    }

}
