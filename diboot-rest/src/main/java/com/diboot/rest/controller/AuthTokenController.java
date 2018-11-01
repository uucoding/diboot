package com.diboot.rest.controller;

import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.security.BaseJwtAuthenticationToken;
import com.diboot.framework.security.JwtHelper;
import com.diboot.framework.service.BaseUserService;
import com.diboot.framework.utils.Encryptor;
import com.diboot.framework.utils.V;
import com.diboot.rest.utils.AppHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 接口认证&鉴权
 * 接口调用之前的token认证
 * Rest接口调用的状态码说明:
 *      0 : 操作成功,
 *      1001 : 部分成功,
 *      1002: 潜在的性能问题,
 *      4000: 请求参数不匹配,
 *      4001: Token无效或已过期,
 *      4003: 没有权限执行该操作,
 *      4004: 请求资源不存在,
 *      4005: 提交数据校验不通过,
 *      4006: 操作执行失败,
 *      5000: 系统异常
 * @author Mazc@dibo.ltd
 * @version 2018/1/4
 * Copyright © www.dibo.ltd
 */
@RestController
@RequestMapping("/token")
public class AuthTokenController extends BaseController {
    private static final Logger logger =  LoggerFactory.getLogger(AuthTokenController.class);

    @Autowired
    private BaseUserService baseUserService;

    /**
     * 用户登录时申请token
     * 1. 申请新token: 传入用户名username + 密码 password
     *    申请完成token后将token(response返回的data属性值)放入header中的authtoken属性中, 每次请求接口时附带。
     *    示例authtoken: Bearer xxx.yyy.zzz
     * 2. 刷新token: token有效期内重新请求该接口（header中附带有效的authtoken即可，无需参数）
     *
     * @param username  用户名
     * @param password  密码
     * @return JsonResult
     */
    @PostMapping("/apply")
    public JsonResult applyOrRefreshToken(String username, String password, HttpServletRequest request) throws Exception{
        String tokenUsername = null;    // 用于生成token的username
        // 有效的旧token换取新token
        if (JwtHelper.isRequestTokenEffective(request)){
            BaseUser user = AppHelper.getCurrentUser();
            if (user == null){
                if (V.isEmpty(username) || V.isEmpty(password)){
                    return new JsonResult(Status.FAIL_OPERATION, new String[]{"token已过期"});
                }
            } else {
                tokenUsername = user.getUsername();
            }
        }

        // 如果username没有通过token获取到，则通过username与password一起校验
        if (V.isEmpty(tokenUsername)){
            if (V.isEmpty(username) || V.isEmpty(password)){
                return new JsonResult(Status.FAIL_OPERATION, new String[]{"用户名密码为空"});
            }

            // 校验用户名密码是否一致
            BaseUser user = baseUserService.getUserByUsername(username);
            // 校验
            if(user.getPassword().equals(Encryptor.encryptPassword(password, user.getSalt()))){
                // 查看用户账号是否过期，如果过期将enabled=false，然后更新user
                if(user.isExpired()){
                    String message = "账号: "+username+" 已过期失效！";
                    logger.warn(message);
                    // 更新状态
                    user.setEnabled(false);
                    baseUserService.updateModel(user, BaseUser.F.enabled);
                }
                // 非正常用户
                if(!user.isEnabled()){
                    // 停用账号
                    throw new LockedAccountException("账号: "+username+" 已被锁定！");
                }
                logger.debug("用户[" + username + "]登录认证通过！");
                tokenUsername = username;
            }
            else{
                // 验证失败，返回登录页
                return new JsonResult(Status.FAIL_INVALID_PARAM, "用户名或密码错误！");
            }
        }
        // 生成认证token
        BaseJwtAuthenticationToken authToken = new BaseJwtAuthenticationToken(request, BaseUser.F.username, tokenUsername, true);
        //获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        String errorMsg = null;
        try {
            subject.login(authToken);
            //验证是否登录成功
            if(subject.isAuthenticated()){
                logger.debug("用户[" + username + "]申请token成功！authtoken="+authToken.getCredentials());
                String token = (String)authToken.getCredentials();
                // 跳转到首页
                return new JsonResult(token, "Token申请成功");
            }
        }
        catch(UnknownAccountException uae){
            errorMsg = "登录失败: 用户名或密码错误!";
            logger.warn("用户[" + username + "]登录验证失败: 未知账户!", uae);
        }
        catch(IncorrectCredentialsException ice){
            errorMsg = "登录失败: 用户名或密码错误!";
            logger.warn("用户[" + username + "]登录验证失败: 错误的用户名密码!", ice);
        }
        catch(LockedAccountException lae){
            errorMsg = "登录失败: 账号已被锁定!";
            logger.warn("用户[" + username + "]登录验证失败: 账号已锁定!", lae);
        }
        catch(ExcessiveAttemptsException eae){
            errorMsg = "登录失败: 尝试次数过多，账号已被锁定!";
            logger.warn("用户[" + username + "]登录验证失败: 失败尝试次数过多!", eae);
        }
        catch(AuthenticationException ae){
            errorMsg = "登录失败: 用户名或密码错误!";
            logger.warn("用户[" + username + "]登录验证失败: 错误的用户名密码!", ae);
        }

        // 验证失败，返回登录页
        return new JsonResult(Status.FAIL_INVALID_TOKEN, errorMsg);
    }

}
