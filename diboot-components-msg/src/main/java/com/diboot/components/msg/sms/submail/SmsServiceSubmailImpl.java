package com.diboot.components.msg.sms.submail;

import com.alibaba.fastjson.JSONObject;
import com.diboot.components.config.MsgCons;
import com.diboot.components.msg.sms.SmsService;
import com.diboot.components.msg.sms.config.MsgConfig;
import com.diboot.components.msg.sms.submail.utils.RequestEncoder;
import com.diboot.framework.config.BaseCons;
import com.diboot.framework.utils.JSON;
import com.diboot.framework.utils.V;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 短信发送 - Submail实现
 * @author Mazc@dibo.ltd
 * @version 2017/9/14
 * Copyright @ www.dibo.ltd
 */
public class SmsServiceSubmailImpl implements SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsServiceSubmailImpl.class);

    /**
     * 时间戳接口配置
     */
    private static final String TIMESTAMP = "https://api.mysubmail.com/service/timestamp";
    /**
     * 备用接口
     * http://api.mysubmail.com/service/timestamp
     * https://api.submail.cn/service/timestamp
     * http://api.submail.cn/service/timestamp
     */
    private static final String TYPE_MD5 = "md5";
    /**
     * API 请求接口配置
     */
    private static final String URL = "https://api.mysubmail.com/message/send";
    private static final String XURL = "https://api.mysubmail.com/message/xsend";
    /**
     * 备用接口
     * http://api.mysubmail.com/message/send
     * https://api.submail.cn/message/send
     * http://api.submail.cn/message/send
     */

    private static final String APP_ID = MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.SMS_SUBMAIL,"sms.submail.appid");
    private static final String APP_KEY = MsgConfig.getValueFromFileAndDb(MsgCons.SUBCATEGORY.SMS_SUBMAIL,"sms.submail.appkey.secret");

    /***
     * 是否可用
     * @return
     */
    public static boolean isAvailable() {
        /**
         * --------------------------------参数配置------------------------------------
         * 请仔细阅读参数配置说明
         *
         * 配置参数包括 appid, appkey ,to , signtype, 	content
         * 用户参数设置，其中 appid, appkey, content, to 为必须参数
         * 请访问 submail 官网创建并获取 appid 和 appkey，链接：https://www.mysubmail.com/chs/sms/apps
         * 请访问 submail 官网创建获取短信模板内容，链接：https://www.mysubmail.com/chs/sms/templates
         * content 参数
         *   |正文中必须提交有效的短信签名，且您的短信签名必须放在短信的最前端，e.g. 【SUBMAIL】您的短信验证码：4438，请在10分钟内输入。
         *   |content 参数将会与您账户中的短信模板进行匹配，如 API 返回 420 错误，请在您的账户中添加短信模板，并提交审核
         *   |请将 短信正文控制在 350 个字符以内
         * signtype 为可选参数: normal | md5 | sha1
         * 当 signtype 参数为空时，默认为 normal
         * --------------------------------------------------------------------------
         */

        return V.notEmpty(APP_ID) && V.notEmpty(APP_KEY);
    }

    @Override
    public String send(String phone, String content) throws Exception {
        return send(phone, content, null);
    }

    /***
     * content为JSON字符串, code为模板编码
     * @param phone
     * @param content
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public String send(String phone, String content, String code) throws Exception {
        TreeMap<String, Object> requestData = new TreeMap<String, Object>();
        requestData.put("appid", APP_ID);
        requestData.put("to", phone);

        // 如果code存在则为模板消息
        String sendUrl = URL;
        if(V.notEmpty(code)){
            requestData.put("project", code);
            sendUrl = XURL;
            if(V.notEmpty(content)){
                requestData.put("vars", content);
            }
        }
        else{
            requestData.put("content", content);
        }

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,HTTP.UTF_8);
        for(Map.Entry<String, Object> entry: requestData.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof String){
                builder.addTextBody(key, String.valueOf(value),contentType);
            }
        }

        String timestamp = getTimestamp();
        requestData.put("timestamp", timestamp);
        requestData.put("sign_type", TYPE_MD5);
        String signStr = APP_ID + APP_KEY + RequestEncoder.formatRequest(requestData) + APP_ID + APP_KEY;

        builder.addTextBody("timestamp", timestamp);
        builder.addTextBody("sign_type", TYPE_MD5);
        builder.addTextBody("signature", RequestEncoder.encode(TYPE_MD5, signStr), contentType);

        HttpPost httpPost = new HttpPost(sendUrl);
        httpPost.addHeader("charset", BaseCons.CHARSET_UTF8);
        httpPost.setEntity(builder.build());
        try{
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
            HttpResponse response = closeableHttpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if(httpEntity != null){
                String jsonStr = EntityUtils.toString(httpEntity, BaseCons.CHARSET_UTF8);
                Map result = JSON.toMap(jsonStr);
                return (String)result.get("send_id");
            }
        }
        catch(ClientProtocolException e){
            logger.warn("Submail发送短信异常！", e);
        }
        catch(IOException e){
            logger.warn("Submail发送短信异常！", e);
        }
        return null;
    }

    /**
     * 获取时间戳
     * @return
     */
    private static String getTimestamp(){
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(TIMESTAMP);
        try{
            HttpResponse response = closeableHttpClient.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String jsonStr = EntityUtils.toString(httpEntity,BaseCons.CHARSET_UTF8);
            if(jsonStr != null){
                JSONObject json = JSONObject.parseObject(jsonStr);
                return json.getString("timestamp");
            }
            closeableHttpClient.close();
        } catch (ClientProtocolException e) {
            logger.warn("Submail获取时间戳异常！", e);
        } catch (IOException e) {
            logger.warn("Submail获取时间戳异常！", e);
        }
        return null;
    }

}
