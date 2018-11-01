package com.diboot.framework.security;

import com.diboot.framework.service.BaseUserService;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.utils.V;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mazc@dibo.ltd
 * @version 2018/3/28
 * Copyright © www.dibo.ltd
 */
public class BaseUserRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(BaseUserRealm.class);

    @Autowired
    protected BaseUserService baseUserService;

    /***
     * 附加用户角色权限信息，留给子类添加角色权限
     */
    protected void grantPermission(SimpleAuthorizationInfo authorizationInfo, BaseUser user){
        if(V.notEmpty(user.getRoleList())){
            Set<String> roles = new HashSet(Arrays.asList(user.getRoleList()));
            authorizationInfo.setRoles(roles);
            authorizationInfo.addStringPermissions(roles);
        }
    }

    /**
     * 获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String currentLoginName = (String) principals.getPrimaryPrincipal();
        //从数据库中获取登录用户信息
        BaseUser baseUser = baseUserService.getUserByUsername(currentLoginName);
        if (null == baseUser) {
           throw new AuthorizationException();
        }
        //为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        grantPermission(authorizationInfo, baseUser);
        return authorizationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //查询是否有此用户
        BaseUser user = baseUserService.getUserByUsername(token.getUsername());
        if (user != null) {
            // 停用用户
            if(!user.isEnabled()){
                throw new LockedAccountException("用户账号: "+token.getUsername()+" 已停用！");
            }
            // 查看用户账号是否过期，如果过期将enabled=false，然后更新user
            if(user.isExpired()){
                String message = "账号: "+token.getUsername()+" 已过期失效！";
                logger.warn(message);
                // 更新状态
                user.setEnabled(false);
                baseUserService.updateModel(user, BaseUser.F.enabled);
                throw new ExpiredCredentialsException(message);
            }
            // 认证成功
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user,
                    user.getPassword(),
                    ByteSource.Util.bytes(user.getSalt()),
                    getName()
            );
            return authenticationInfo;
        }
        return null;
    }

}
