package com.diboot.components.msg.email;

import com.diboot.components.config.MsgCons;
import com.diboot.components.msg.sms.config.MsgConfig;
import com.diboot.framework.config.BaseCons;
import com.diboot.framework.utils.V;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 邮件发送工具类
 * @author Mazc@dibo.ltd
 * @version 2017/4/17
 * Copyright @ www.dibo.ltd
 */
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    private static String SENDER_NAME = MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.EMAIL, "email.sender.name"); // 发送方称呼
    private static String EMAIL_ADDR = MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.EMAIL, "email.sender.address"); // 发送方email地址
    private static String EMAIL_PWD = MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.EMAIL, "email.sender.password"); // 发送方密码
    private static String SMTP_HOST = MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.EMAIL, "email.sender.host"); // 发送方smptHost
    private static String SMTP_SSL_PORT = MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.EMAIL, "email.sender.sslport"); // 发送端口

    /***
     * 发送Email
     * @param to email
     * @param toName
     * @param title
     * @param content
     * @throws Exception
     */
    public static boolean send(String to, String toName, String title, String content) throws Exception{
        return send(to, toName, null, title, content);
    }

    /***
     * 发送邮件，可带附件
     * @param to email
     * @param toName
     * @param ccEmails
     * @param title
     * @param content
     * @param filePaths
     * @return
     * @throws Exception
     */
    public static boolean send(String to, String toName, String[] ccEmails, String title, String content, String... filePaths) throws Exception{
        if(logger.isDebugEnabled()){
            logger.debug("发送邮件开始, 收件人:" + to + ", message=" + content);
        }
        // Create the email message
        HtmlEmail email = new HtmlEmail();
        email.setHostName(SMTP_HOST);
        email.setCharset(BaseCons.CHARSET_UTF8);
        email.setSSLOnConnect(true);
        if(V.notEmpty(SMTP_SSL_PORT)){
            email.setSslSmtpPort(SMTP_SSL_PORT);
        }
        email.setAuthentication(EMAIL_ADDR, EMAIL_PWD);
        email.addTo(to, toName);
        if(V.notEmpty(ccEmails)){
            for(String cc : ccEmails){
                email.addCc(cc);
            }
        }
        email.setFrom(EMAIL_ADDR, SENDER_NAME);
        email.setSubject(title);
        // set the html message
        email.setHtmlMsg(content);

        // 发送附件
        if(V.notEmpty(filePaths)){
            for(String path : filePaths){
                File file = new File(path);
                if(!file.exists()){
                    logger.warn("附件文件不存在，无法发送！path="+path);
                    return false;
                }
                EmailAttachment attachment = new EmailAttachment();
                attachment.setName(file.getName());
                attachment.setPath(path);
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                email.attach(attachment);
            }
        }

        // send the email
        email.send();

        if(logger.isDebugEnabled()){
            logger.debug("发送邮件完成, 收件人:" + to);
        }

        return true;
    }

}
