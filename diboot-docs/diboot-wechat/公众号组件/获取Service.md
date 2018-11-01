# 获取Service

* 在项目启动时候，我们已经将公众号相关参数设置并初始化到了WxMpService类中。
* 通过注入[WxMpServiceExt]()，即可参照[weixin-java-tools](https://github.com/Wechat-Group/weixin-java-tools)相关接口来调用相关接口。
* 获取service并使用相关接口的示例如下：

```java
package com.diboot.wechat.mp.controller;

import com.diboot.wechat.mp.service.WxMpServiceExt;
import com.diboot.framework.config.Status;
import com.diboot.framework.model.JsonResult;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wechat/mp")
public class WxMpController {

    @Autowired
    private WxMpServiceExt wxMpService;

    /**
     *  获取调用js_api所需的配置信息
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/getJsSdkConfig")
    public JsonResult getJsSdkConfig(HttpServletRequest request) throws Exception{
        // 获取当前链接
        String url = request.getParameter("url");

        // 创建签名
        WxJsapiSignature signature = wxMpService.createJsapiSignature(url);

        return new JsonResult(Status.OK, signature, new String[]{"获取js_sdk配置信息成功"});
    }

}

```