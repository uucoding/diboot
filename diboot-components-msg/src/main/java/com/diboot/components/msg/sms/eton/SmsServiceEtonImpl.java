package com.diboot.components.msg.sms.eton;

import com.diboot.components.msg.sms.SmsService;
import com.diboot.framework.utils.V;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/***
 * 短信功能 - 移通实现 
 * @author Mazc@dibo.ltd
 * @version 2017年1月13日
 * Copyright 2017 www.dibo.ltd
 */
public class SmsServiceEtonImpl implements SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsServiceEtonImpl.class);
	
	private String COMMENT = "MT_REQUEST";
	public static final String ERROR = "ERROR:";
	private String ecodeform = "GBK";
	/***
	 * 返回码
	 */
	private HashMap<String, String> reutrnCodeMap = new HashMap<String, String>(){{		
		put("000", "SUCCESS");
		put("100", ERROR+" SP API接口参数错误");
		put("200", ERROR+" MLINK平台内部过滤路由信息");
		put("300", ERROR+" MLINK平台内部SP配置信息");
		put("400", ERROR+" MLINK网关发送时错误");
		put("500", ERROR+" 运行商反馈的信息");
		put("600", ERROR+" MLINK API发送时错误");
	}};

    /**
     * 是否可用
     * @return
     */
	public static boolean isAvailable(){
        Map<SmsConfig.SETTINGS, String> settings = SmsConfig.getEtonSettings();
        if(V.isEmpty(settings) || settings.get(SmsConfig.SETTINGS.ETON_SPID) == null || settings.get(SmsConfig.SETTINGS.ETON_SPPWD) == null){
            logger.info("Eton Sms Sender is not available.");
            return false;
        }
        return true;
    }

    @Override
    public String send(String phone, String content) throws Exception {
	    return send(phone, content, null);
    }

    @Override
	public String send(String phone, String content, String code) throws Exception {
        if(!isAvailable()){
            logger.warn("移通短信参数未设置，无法发送短信！");
            return ERROR + "短信参数未设置！";
        }

	    Map<SmsConfig.SETTINGS, String> settings = SmsConfig.getEtonSettings();
        String mtUrl = settings.get(SmsConfig.SETTINGS.ETON_MTURL);
        String spid = settings.get(SmsConfig.SETTINGS.ETON_SPID);
        String sppassword = settings.get(SmsConfig.SETTINGS.ETON_SPPWD);
        String sa = settings.get(SmsConfig.SETTINGS.ETON_SA);
        if(code == null) {
            code = settings.get(SmsConfig.SETTINGS.ETON_SIGNCODE);
        }
        String da = "86"+phone;
        String sm = new String(Hex.encodeHex(content.getBytes(ecodeform)));

        StringBuilder smsUrl = new StringBuilder();
        smsUrl.append(mtUrl);
        smsUrl.append("?command=" + COMMENT);
        smsUrl.append("&spid=" + spid);
        smsUrl.append("&sppassword=" + sppassword);
        smsUrl.append("&spsc=" + code);
        if(sa != null){
        	smsUrl.append("&sa=" + sa);        	
        }
        smsUrl.append("&da=" + da);
        smsUrl.append("&sm=" + sm);
        smsUrl.append("&dc=" + 15); //15为GBK

        // http get
        String resStr = doGetRequest(smsUrl.toString());
        
        // 解析结果
        HashMap<String,String> pp = parseResStr(resStr);
        String returnCode = pp.get("mterrcode");
        if(logger.isDebugEnabled()){
        	String message = "SEND_SMS: to="+phone+", command=" + pp.get("command") + ", mterrcode="+returnCode+", mtmsgid=" + pp.get("mtmsgid") + ", mtstat=" + pp.get("mtstat");
        	logger.debug(message);        	
        }
        // 成功 返回短信id
        if("000".equalsIgnoreCase(returnCode)){
        	return pp.get("mtmsgid");
        }
        else{
        	return reutrnCodeMap.get(returnCode);        	
        }
	}
    
    /**
     * @param dataCoding
     * @param realStr
     * @return Hex
     * @throws UnsupportedEncodingException 
     */
    public static String encodeHexStr(int dataCoding, String realStr) {
        String hexStr = null;
        if (realStr != null) {
            try {
                if (dataCoding == 15) {
                    hexStr = new String(Hex.encodeHex(realStr.getBytes("GBK")));
                } else if ((dataCoding & 0x0C) == 0x08) {
                    hexStr = new String(Hex.encodeHex(realStr.getBytes("UnicodeBigUnmarked")));
                } else {
                    hexStr = new String(Hex.encodeHex(realStr.getBytes("ISO8859-1")));
                }
            } catch (UnsupportedEncodingException e) {
                logger.error(e.toString());
            }
        }
        return hexStr;
    }

    /***
     * decode
     * @param dataCoding
     * @param hexStr
     * @return
     */
    public static String decodeHexStr(int dataCoding, String hexStr) {
        String realStr = null;
        try {
            if (hexStr != null) {
                if (dataCoding == 15) {
                    realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "GBK");
                } else if ((dataCoding & 0x0C) == 0x08) {
                    realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "UnicodeBigUnmarked");
                } else {
                    realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "ISO8859-1");
                }
            }
        }
        catch (Exception e) {
        	logger.error("解析decodeHex异常", e);
        }
        
        return realStr;
    }

    /**
     * HttpGet
     * @param urlstr
     * @return
     */
    public static String doGetRequest(String urlstr) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        String result = null;
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(urlstr);
            // 获取当前客户端对象
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
            } 
            httpClient.close();
        } catch (Exception e) {
        	logger.error("请求URL失败: url="+urlstr, e);
        }
        finally{
			try {
				httpClient.close();
			}
			catch (IOException e) {
				logger.error("关闭httpClient短信发送连接异常:", e);
			}
		}
        return result;
    }
    
    /**
     * 
     * @param resStr
     * @return
     */
    public static HashMap<String,String> parseResStr(String resStr) {
        HashMap<String,String> pp = new HashMap<String,String>();
        try {
            String[] ps = resStr.split("&");
            for(int i=0;i<ps.length;i++){
                int ix = ps[i].indexOf("=");
                if(ix!=-1){
                   pp.put(ps[i].substring(0,ix),ps[i].substring(ix+1)); 
                }
            }
        }
        catch (Exception e) {
            logger.error("解析发送结果异常", e);
        }
        return pp;
    }

}
