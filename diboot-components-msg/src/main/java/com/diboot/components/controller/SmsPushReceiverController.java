package com.diboot.components.controller;

import com.diboot.framework.controller.BaseController;
import com.diboot.components.model.Message;
import com.diboot.components.service.MessageService;
import com.diboot.framework.utils.JSON;
import com.diboot.framework.utils.Query;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信推送接收Controller
 * @author Mazc@dibo.ltd
 * @version 2017/9/15
 * Copyright @ www.dibo.ltd
 */
@Controller
@RequestMapping("/public/msg/pushReceiver")
public class SmsPushReceiverController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SmsPushReceiverController.class);

    @Autowired
    MessageService messageService;

    @PostMapping("/etonSms")
    public void etonReceiver(HttpServletRequest request, HttpServletResponse response){
        String mtstat = request.getParameter("mtstat");
        String mterrorcode = request.getParameter("mterrorcode");
        boolean success = Message.STATUS.DELIVRD.name().equalsIgnoreCase(mtstat);

        // 短信记录编号
        String mtmsgid = request.getParameter("mtmsgid");
        if(V.notEmpty(mtmsgid)){
            // 更新model
            Query query = new Query(Message.F.response, mtmsgid);
            List<Message> msgList = messageService.getLimitModelList(query.build(), 1);
            if(V.notEmpty(msgList)){
                Message model = msgList.get(0);
                model.setStatus(success? Message.STATUS.DELIVRD.name() : Message.STATUS.UNDELIVERED.name());

                Map<String, Object> map = new HashMap<>();
                map.put("mtstat", mtstat);
                map.put("mterrorcode", mterrorcode);
                model.setExtdata(JSON.stringify(map));
                // 更新结果
                messageService.updateModel(model, Message.F.status, Message.F.extdata);
            }
        }

        // code = 000 成功
        if(success){
            logger.info("mtmsgid: " + mtmsgid + " 发送成功！");
        }
        else{
            logger.warn("mtmsgid: " + mtmsgid + " 发送失败！ mterrorcode="+mterrorcode + ", mtstat=" + mtstat);
        }
        //获取状态报告参数后，请即刻返回响应信息
        try{
            String spid = request.getParameter("spid");
            PrintWriter pw = response.getWriter();
            StringBuilder output = new StringBuilder();
            output.append("command=RT_RESPONSE&spid=");
            output.append(S.xssEncode(spid));
            output.append("&mtmsgid=");
            output.append(S.xssEncode(mtmsgid));
            output.append("&rtstat=ACCEPTD&rterrcode=000");
            response.setContentLength(output.length());
            pw.write(output.toString());
            pw.flush();
            pw.close();
        }
        catch(Exception e){
            logger.error("返回推送处理结果异常", e);
        }
    }
}
