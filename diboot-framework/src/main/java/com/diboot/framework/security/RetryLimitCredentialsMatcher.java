package com.diboot.framework.security;

import com.diboot.framework.config.BaseCons;
import com.diboot.framework.utils.Encryptor;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  自定义用户登录校验，限定失败尝试次数
 * @author Mazc@dibo.ltd
 * @version 2018/1/4
 * Copyright © www.dibo.ltd
 */
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {
    private static final Logger logger = LoggerFactory.getLogger(RetryLimitCredentialsMatcher.class);

    /***
     * 认证失败尝试次数
     */
    private Cache<String, AtomicInteger> loginAttemptsCache;

    public RetryLimitCredentialsMatcher(CacheManager cacheManager) {
        loginAttemptsCache = cacheManager.getCache("loginAttemptsCache");
        super.setHashAlgorithmName(Encryptor.ALGORITHM);
        super.setHashIterations(Encryptor.ITERATIONS);
        super.setStoredCredentialsHexEncoded(true);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        AtomicInteger retryCount = loginAttemptsCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            loginAttemptsCache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > getMaxLoginRetryTimes()) {
            String errorMsg = "用户 "+username + " 登录失败次数超出最大限制！";
            logger.warn(errorMsg);
            throw new ExcessiveAttemptsException(errorMsg);
        }
        // 判断认证是否匹配
        boolean match = super.doCredentialsMatch(token, info);
        if(match){
            loginAttemptsCache.remove(username);
        }
        return match;
    }

    /***
     * 获取可尝试的最大次数
     * @return
     */
    protected int getMaxLoginRetryTimes(){
        return BaseCons.MAX_LOGIN_RETRY_TIMES;
    }
}
