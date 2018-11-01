package com.diboot.wechat.cp.controller;

import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseOrg;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.security.BaseJwtAuthenticationToken;
import com.diboot.framework.security.JwtHelper;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.V;
import com.diboot.framework.controller.BaseController;
import com.diboot.wechat.cp.factory.WxCpServiceFactory;
import me.chanjar.weixin.cp.api.WxCpService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录认证/申请token相关操作Controller
 * @author Mazc@dibo.ltd
 * @version 2018/1/4
 * Copyright © www.dibo.ltd
 */
@RestController
@RequestMapping("/token/wechat/cp")
public class WxCpAuthTokenController extends BaseController {
    private static final Logger logger =  LoggerFactory.getLogger(WxCpAuthTokenController.class);

    @Autowired
    private WxCpServiceFactory wxCpServiceFactory;

    /***
     * 构建授权URL
     * @param app
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/buildOAuthUrl/{app}")
    public JsonResult buildOAuthUrl4cp(@PathVariable("app")String app, HttpServletRequest request) throws Exception{

        WxCpService wxCpService = wxCpServiceFactory.getCpService(BaseOrg.class.getSimpleName(), 0L, app);

        String url = request.getParameter("url");
        if (V.isEmpty(url)){
            return new JsonResult(Status.FAIL_OPERATION, new String[]{"url为空，获取OAuth链接失败"});
        }

        String oauthUrl = wxCpService.getOauth2Service().buildAuthorizationUrl(url, null);
        return new JsonResult(Status.OK, oauthUrl, new String[]{"获取" + app + "回调地址成功"});
    }

    /**
     * 1. 用于微信回调授权的code认证
     * 2. 用于微信回调授权的token交换
     * 3. 用于登录模式的token交换
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/apply/{app}")
    public JsonResult applyTokenByOAuth2cp(@PathVariable("app")String app, HttpServletRequest request) throws Exception{
        String code = request.getParameter("code");
        //TODO 校验state
        //String state = request.getParameter("state");
        String wechat = "";
        if (JwtHelper.isRequestTokenEffective(request)){
            BaseUser user = BaseHelper.getCurrentUser();
            if (user == null){
                // 如果有code并且token已过期，则使用code获取wechat
                if (V.isEmpty(code)){
                    return new JsonResult(Status.FAIL_INVALID_TOKEN, new String[]{"token已过期"});
                }
            } else {
                wechat = user.getWechat();
            }
        }

        // 如果wechat没有通过token获取到，则通过code获取
        if (V.isEmpty(wechat)){
            if (V.isEmpty(code)){
                // 如果没有code参数，则返回提示信息
                return new JsonResult(Status.FAIL_INVALID_TOKEN, new String[]{"请重新进入页面"});
            }
            WxCpService wxCpService = wxCpServiceFactory.getCpService(BaseOrg.class.getSimpleName(), 0L, app);
            String[] res = wxCpService.getOauth2Service().getUserInfo(code);
            wechat = res[0];
        }

        // 如果没有获取到wechat，则返回提示信息
        if (V.isEmpty(wechat)){
            return new JsonResult(Status.FAIL_INVALID_TOKEN, new String[]{"获取信息失败，请确认是否已加入企业微信"});
        }

        // 设置token
        BaseJwtAuthenticationToken authToken = new BaseJwtAuthenticationToken(request, BaseUser.F.wechat, wechat, true);
        // 获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        String token = null;
        String errorMsg = null;

        try {
            subject.login(authToken);
            //验证是否登录成功
            if(subject.isAuthenticated()){
                token = (String)authToken.getCredentials();
                logger.debug("用户[" + wechat + "]申请token成功！authtoken="+token);
            }
        }
        catch(UnknownAccountException uae){
            errorMsg = "登录失败: 用户名或密码错误!";
            logger.warn("用户[" + wechat + "]登录验证失败: 未知账户!", uae);
        }
        catch(IncorrectCredentialsException ice){
            errorMsg = "登录失败: 用户名或密码错误!";
            logger.warn("用户[" + wechat + "]登录验证失败: 错误的用户名密码!", ice);
        }
        catch(LockedAccountException lae){
            errorMsg = "登录失败: 账号已被锁定!";
            logger.warn("用户[" + wechat + "]登录验证失败: 账号已锁定!", lae);
        }
        catch(ExcessiveAttemptsException eae){
            errorMsg = "登录失败: 尝试次数过多，账号已被锁定!";
            logger.warn("用户[" + wechat + "]登录验证失败: 失败尝试次数过多!", eae);
        }
        catch(AuthenticationException ae){
            errorMsg = "登录失败: 用户名或密码错误!";
            logger.warn("用户[" + wechat + "]登录验证失败: 错误的用户名密码!", ae);
        }

        if (V.isEmpty(token)){
            String msg = V.notEmpty(errorMsg) ? errorMsg : "申请token失败";
            return new JsonResult(Status.FAIL_INVALID_TOKEN, new String[]{msg});
        }

        return new JsonResult(Status.OK, token, new String[]{"申请token成功"});
    }

    @Override
    protected String getViewPrefix() {
        return null;
    }
}
