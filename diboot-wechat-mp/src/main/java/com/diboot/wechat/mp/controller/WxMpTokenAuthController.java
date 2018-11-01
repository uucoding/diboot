package com.diboot.wechat.mp.controller;

import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.config.Status;
import com.diboot.framework.controller.BaseController;
import com.diboot.framework.model.BaseAuthUser;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.security.BaseJwtAuthenticationToken;
import com.diboot.framework.security.JwtHelper;
import com.diboot.framework.service.BaseAuthUserService;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.V;
import com.diboot.wechat.mp.service.WxMpServiceExt;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录认证/申请token相关操作Controller
 * @author Mazc@dibo.ltd
 * @version 2018/1/4
 * Copyright © www.dibo.ltd
 */
@RestController
@RequestMapping("/token/wechat/mp")
public class WxMpTokenAuthController extends BaseController {
    private static final Logger logger =  LoggerFactory.getLogger(WxMpTokenAuthController.class);

    private static final String STATE = BaseConfig.getProperty("wechat.state");

    @Autowired
    private BaseAuthUserService baseAuthUserService;

    @Autowired
    private WxMpServiceExt wxMpService;

    /**
     * 创建OAuth认证链接
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/buildOAuthUrl")
    public JsonResult buildOAuthUrl4mp(HttpServletRequest request) throws Exception{
        String url = request.getParameter("url");
        if (V.isEmpty(url)){
            return new JsonResult(Status.FAIL_OPERATION, new String[]{"url为空，获取OAuth链接失败"});
        }

        String oauthUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, STATE);
        return new JsonResult(Status.OK, oauthUrl, new String[]{"获取OAuth链接成功"});
    }

    /**
     * 微信服务号的回调授权登录认证
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/apply")
    public JsonResult applyTokenByOAuth2mp(HttpServletRequest request) throws Exception{
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String openid = "";
        if (JwtHelper.isRequestTokenEffective(request)){
            BaseUser user = BaseHelper.getCurrentUser();
            if (user == null){
                // 如果有code并且token已过期，则使用code获取wechat
                if (V.isEmpty(code)){
                    return new JsonResult(Status.FAIL_INVALID_TOKEN, new String[]{"token已过期"});
                }
            } else {
                openid = user.getOpenid();
            }
        }

        // 如果openid没有通过token获取到，则通过code获取
        if (V.isEmpty(openid)){
            // 校验STATE
            if (V.notEmpty(STATE) && !STATE.equals(state)){
                return new JsonResult(Status.FAIL_OPERATION, new String[]{"非法来源"});
            }
            // 获取wxMpService
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            if (!wxMpService.oauth2validateAccessToken(wxMpOAuth2AccessToken)){
                wxMpOAuth2AccessToken = wxMpService.oauth2refreshAccessToken(wxMpOAuth2AccessToken.getRefreshToken());
            }
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);

            // 更新或新建用户信息
            createOrModifyAuthUser(wxMpUser);

            openid = wxMpUser.getOpenId();
        }

        // 如果没有获取到wechat，则返回提示信息
        if (V.isEmpty(openid)){
            return new JsonResult(Status.FAIL_INVALID_TOKEN, new String[]{"获取信息失败，请确认是否已加入企业微信"});
        }

        // 设置token
        BaseJwtAuthenticationToken authToken = new BaseJwtAuthenticationToken(request, BaseUser.F.openid, openid, true);
        // 获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        String token = null;
        String errorMsg = null;

        try {
            subject.login(authToken);
            //验证是否登录成功
            if(subject.isAuthenticated()){
                token = (String)authToken.getCredentials();
                logger.debug("openid[" + openid + "]申请token成功！authtoken="+token);
            }
        }
        catch(UnknownAccountException uae){
            errorMsg = "登录失败: 用户名或密码错误!";
            logger.warn("用户[" + openid + "]登录验证失败: 未知账户!", uae);
        }
        catch(IncorrectCredentialsException ice){
            errorMsg = "登录失败: 用户名或密码错误!";
            logger.warn("用户[" + openid + "]登录验证失败: 错误的用户名密码!", ice);
        }
        catch(LockedAccountException lae){
            errorMsg = "登录失败: 账号已被锁定!";
            logger.warn("用户[" + openid + "]登录验证失败: 账号已锁定!", lae);
        }
        catch(ExcessiveAttemptsException eae){
            errorMsg = "登录失败: 尝试次数过多，账号已被锁定!";
            logger.warn("用户[" + openid + "]登录验证失败: 失败尝试次数过多!", eae);
        }
        catch(AuthenticationException ae){
            errorMsg = "登录失败: 用户名或密码错误!";
            logger.warn("用户[" + openid + "]登录验证失败: 错误的用户名密码!", ae);
        }

        if (V.isEmpty(token)){
            String msg = V.notEmpty(errorMsg) ? errorMsg : "申请token失败";
            return new JsonResult(Status.FAIL_INVALID_TOKEN, new String[]{msg});
        }

        return new JsonResult(Status.OK, token, new String[]{"申请token成功"});
    }

    /**
     * 创建或更新用户
     * @param wxUser
     * @return
     * @throws Exception
     */
    private BaseAuthUser createOrModifyAuthUser(WxMpUser wxUser) throws Exception{
        BaseAuthUser authUser = baseAuthUserService.getModelByOpenid(wxUser.getOpenId());
        if (authUser == null){
            authUser = new BaseAuthUser();
            authUser.setUserType(BaseUser.class.getSimpleName());
            authUser.setUserId(0L);
            authUser.setOpenid(wxUser.getOpenId());
            baseAuthUserService.createModel(authUser);
        }
        return authUser;
    }

    @Override
    protected String getViewPrefix() {
        return null;
    }
}
