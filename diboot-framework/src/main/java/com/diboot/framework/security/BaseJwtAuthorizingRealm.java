package com.diboot.framework.security;

import com.diboot.framework.service.BaseAuthUserService;
import com.diboot.framework.service.BaseUserService;
import com.diboot.framework.model.BaseAuthUser;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.utils.Encryptor;
import com.diboot.framework.utils.V;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Shrio用户认证，包括登陆和权限认证
 * @author wuy
 * @version 2018/1/29
 */
public class BaseJwtAuthorizingRealm extends AuthorizingRealm{
    private static final Logger logger =  LoggerFactory.getLogger(BaseJwtAuthorizingRealm.class);

    private static final String AUTH_EXCEPTION_MSG = "用户名或密钥错误！";

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private BaseAuthUserService baseAuthUserService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof BaseJwtAuthenticationToken;
    }

    @Override
    public Class<?> getAuthenticationTokenClass() {
        return BaseJwtAuthorizingRealm.class;
    }

    /***
     * 赋予角色权限
     * @param authorizationInfo
     * @param user
     */
    protected void grantPermission(SimpleAuthorizationInfo authorizationInfo, BaseUser user) {
        if (V.notEmpty(user.getRoleList())) {
            Set<String> roles = new HashSet(Arrays.asList(user.getRoleList()));
            authorizationInfo.setRoles(roles);
            authorizationInfo.addStringPermissions(roles);
        }
    }

    /***
     * 登陆
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        BaseJwtAuthenticationToken jwt = (BaseJwtAuthenticationToken) token;
        //根据用户名去数据库查找这个用户是否存在
        String username = (String) jwt.getPrincipal();
        if(V.isEmpty(username)){
            throw new AuthenticationException("无效的Token !");
        }
        BaseUser user = null;
        //根据用户名去数据库查找这个用户是否存在
        if(BaseUser.F.username.equalsIgnoreCase(jwt.getFetchUserByField())){
            user = baseUserService.getUserByUsername(username);
        }
        else if(BaseUser.F.openid.equalsIgnoreCase(jwt.getFetchUserByField())){
            user = baseAuthUserService.getModelByOpenid(username);
            attachUserModel((BaseAuthUser)user);
        }
        else{
            user = baseUserService.getUserByField(jwt.getFetchUserByField(), username);
        }
        if (user != null){
            // 是否已初步验证过
            boolean isAuthed = jwt.isPreliminaryVerified();
            String secret = jwt.getApplyTokenSecret();
            if(!isAuthed && V.notEmpty(secret)){
                // 密钥是否匹配
                isAuthed = Encryptor.encryptPassword(secret, user.getSalt()).equals(user.getPassword());
            }
            // 验证不通过
            if(!isAuthed){
                throw new AuthenticationException(AUTH_EXCEPTION_MSG);
            }
            // 验证通过，检查账号是否可用
            if(!user.isEnabled()) {
                throw new LockedAccountException("用户账号: " + username + " 已停用或被锁定！");
            }
            // 用户账号已过期
            else if (user.isExpired()) {
                String message = "账号: " + username + " 已过期失效！";
                logger.warn(message);
                // 锁定过期的账号
                user.setEnabled(false);
                baseUserService.updateModel(user, new String[]{BaseUser.F.enabled});
                throw new ExpiredCredentialsException(message);
            }
            // 认证成功
            logger.debug("用户信息获取成功！username="+username);
            // 认证成功 缓存用户
            return new SimpleAuthenticationInfo(user, jwt.getCredentials(), getName());
        }
        else {
            throw new AuthenticationException(AUTH_EXCEPTION_MSG);
        }
    }

    /***
     * 需要检测用户权限时调用，例如checkRole,checkPermission之类的鉴权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        BaseUser user = (BaseUser) principals.getPrimaryPrincipal();
        // 授权
        grantPermission(info, user);
        return info;
    }

    /**
     * 将用户实体添加到到认证户实体中
     * @param authUser
     */
    protected void attachUserModel(BaseAuthUser authUser){
    }
}
