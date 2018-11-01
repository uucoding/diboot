package com.diboot.framework.security;

import com.diboot.framework.model.BaseUser;
import com.diboot.framework.utils.V;
import org.apache.shiro.authc.AuthenticationToken;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mazc@dibo.ltd
 * @version 2018/1/6
 * Copyright © www.dibo.ltd
 */
public class BaseJwtAuthenticationToken implements AuthenticationToken{
    // 用户名
    private String username;
    // auth token
    private String authtoken;
    // 申请token的密码
    private String applyTokenSecret;
    // 签名key (默认SIGN_KEY，配置signKey, 或微信state)
    private String signKey = JwtHelper.SIGN_KEY;
    // 过期时间
    private long expiresInMinutes = JwtHelper.EXPIRES_IN_MINUTES;

    /***
     * 用于用户匹配的字段
     */
    private String fetchUserByField = BaseUser.F.username;

    /***
     *是否已初步认证过
      */
    private boolean preliminaryVerified = false;

    /***
     * 用户名密码形式的授权
     */
    public BaseJwtAuthenticationToken(String username, String authtoken) {
        this.username = username;
        this.authtoken = authtoken;
    }

    /***
     * 用户名密码形式的授权
     */
    public BaseJwtAuthenticationToken(HttpServletRequest request) {
        initialize(request);
        if(this.username != null){
            this.authtoken = JwtHelper.generateToken(this.username, this.signKey, this.expiresInMinutes);
        }
    }

    /***
     * 用户名密码形式的授权
     * @param request
     * @param expiresInMinutes 过期时间（分钟）
     */
    public BaseJwtAuthenticationToken(HttpServletRequest request, long expiresInMinutes) {
        initialize(request);
        this.expiresInMinutes = expiresInMinutes;
        if(this.username != null){
            this.authtoken = JwtHelper.generateToken(this.username, this.signKey, expiresInMinutes);
        }
    }

    /***
     * 用于微信企业号，服务号等的授权
     * @param request
     * @param fetchUserByField 用户标识: BaseUser.F.username, BaseUser.F.wechat, BaseUser.F.openid
     * @param username 用户标识的值
     * @param preliminaryVerified 是否已初步验证（微信通过code拿到wechat或openid视为已初步验证）
     */
    public BaseJwtAuthenticationToken(HttpServletRequest request, String fetchUserByField, String username, boolean preliminaryVerified) {
        initJwtAuthenticationToken(request, fetchUserByField, username, preliminaryVerified, this.expiresInMinutes);
    }

    /***
     * 用于微信企业号，服务号等的授权
     * @param request
     * @param fetchUserByField 用户标识: BaseUser.F.username, BaseUser.F.wechat, BaseUser.F.openid
     * @param username 用户标识的值
     * @param preliminaryVerified 是否已初步验证（微信通过code拿到wechat或openid视为已初步验证）
     * @param expiresInMinutes 过期时间(分钟)
     */
    public BaseJwtAuthenticationToken(HttpServletRequest request, String fetchUserByField, String username, boolean preliminaryVerified, long expiresInMinutes) {
        initJwtAuthenticationToken(request, fetchUserByField, username, preliminaryVerified, expiresInMinutes);
    }

    /***
     * 初始化认证token
     * @param request
     * @param fetchUserByField
     * @param username
     * @param preliminaryVerified
     * @param expiresInMinutes
     */
    private void initJwtAuthenticationToken(HttpServletRequest request, String fetchUserByField, String username, boolean preliminaryVerified, long expiresInMinutes){
        initialize(request);
        if(V.notEmpty(username)){
            this.username = username;
        }
        if(V.notEmpty(fetchUserByField)){
            this.fetchUserByField = fetchUserByField;
        }
        this.preliminaryVerified = preliminaryVerified;
        if(this.username != null){
            this.authtoken = JwtHelper.generateToken(this.username, this.signKey, expiresInMinutes);
        }
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return authtoken;
    }

    public String getApplyTokenSecret() {
        return applyTokenSecret;
    }

    /***
     * 验证失败的时候清空token
     */
    public void clearAuthtoken(){
        this.authtoken = null;
    }

    public void setApplyTokenSecret(String applyTokenSecret) {
        this.applyTokenSecret = applyTokenSecret;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getFetchUserByField() {
        return fetchUserByField;
    }

    public void setFetchUserByField(String fetchUserByField) {
        this.fetchUserByField = fetchUserByField;
    }

    public boolean isPreliminaryVerified() {
        return preliminaryVerified;
    }

    public void setPreliminaryVerified(boolean preliminaryVerified) {
        this.preliminaryVerified = preliminaryVerified;
    }

    /***
     * 从request取所需的参数值：尝试取多个值
     * @param request
     * @param params
     * @return
     */
    private String getRequestParam(HttpServletRequest request, String... params){
        if(params != null && params.length > 0){
            for(String param : params){
                String value = request.getParameter(param);
                if(V.notEmpty(value)){
                    return value;
                }
            }
        }
        return null;
    }

    private void initialize(HttpServletRequest request){
        this.username = getRequestParam(request, "username", "wechat", "openid");
        this.applyTokenSecret = getRequestParam(request, "password", "secret");
        String signKey = getRequestParam(request, "signKey");
        if(V.notEmpty(signKey)){
            this.signKey = signKey;
        }
    }

}
