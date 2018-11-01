package com.diboot.components.service;

import com.diboot.framework.service.BaseService;
import com.diboot.components.model.Message;
import org.springframework.stereotype.Component;

/***
 * 消息相关操作Service
 * @author Mazc@dibo.ltd
 * @version 2017-09-14
 * Copyright @ www.dibo.ltd
*/
@Component
public interface MessageService extends BaseService {

    /***
     * 保存并发送消息
     * @param msg
     * @return
     */
    boolean createAndSendMsg(Message msg);

    /***
     * 发送消息
     * @param msg
     * @return
     */
    boolean sendMsg(Message msg);

    /***
     * 发送验证码
     * @param cellphone
     * @return
     */
    boolean sendVerifyCode2Phone(String cellphone);

    /***
     * 校验手机验证码是否匹配
     * @param cellphone
     * @param verifyCode
     * @return
     */
    boolean isVerifyCodeValid(String cellphone, String verifyCode);
}