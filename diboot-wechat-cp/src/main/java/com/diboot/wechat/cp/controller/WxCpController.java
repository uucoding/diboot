package com.diboot.wechat.cp.controller;

import com.diboot.components.file.FileHelper;
import com.diboot.framework.config.Status;
import com.diboot.framework.model.*;
import com.diboot.framework.utils.D;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import com.diboot.common.service.FileService;
import com.diboot.wechat.cp.config.Cons;
import com.diboot.wechat.cp.factory.WxCpServiceFactory;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.cp.api.WxCpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wechat/cp")
public class WxCpController {
    private static final Logger logger =  LoggerFactory.getLogger(WxCpController.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private WxCpServiceFactory wxCpServiceFactory;

    @PostMapping("/getJsSdkConfig/{app}")
    public JsonResult getJsSdkConfig(@PathVariable("app")String app, HttpServletRequest request) throws Exception{
        String relObjType = request.getParameter("relObjType");
        String relObjIdStr = request.getParameter("relObjId");
        relObjType = V.notEmpty(relObjType) ? relObjType : BaseOrg.class.getSimpleName();
        Long relObjId = V.notEmpty(relObjIdStr) ? Long.parseLong(relObjIdStr) : 0L;

        // 获取相关应用的app
        WxCpService wxCpService = wxCpServiceFactory.getCpService(relObjType, relObjId, app);

        // 获取当前链接
        String url = request.getParameter("url");

        // 创建签名
        WxJsapiSignature signature = wxCpService.createJsapiSignature(url);

        return new JsonResult(Status.OK, signature, "获取js_sdk配置信息成功");
    }
}
