# token登录授权

diboot-framework默认是用Shrio进行用户认证，包括登陆和权限认证

## Shiro相关配置
### 初始化shiroFilter
* 新建ShiroConfiguration类，并添加@Configuration注解
* 添加相关的Bean

```java
@Configuration
public class ShiroConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

    /**
     * Shiro的Web过滤器Factory: shiroFilter
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //Shiro securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //用户访问未对其授权的资源时的错误提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //定义shiro过滤链 Map结构
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/error", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //设置过滤器
        Map<String, Filter> filters = new HashMap<>();
        filters.put("anon", new AnonymousFilter());
        filters.put("jwt", new BaseJwtAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizingRealm authorizingRealm(){
        WxMemberJwtAuthorizingRealm jwtRealm = new WxMemberJwtAuthorizingRealm();
        return jwtRealm;
    }

    @Bean
    public EhCacheManager cacheManager(){
        System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
        EhCacheManager cacheManager = new EhCacheManager();
        //cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    /**
     * Shiro SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authorizingRealm());
        securityManager.setCacheManager(cacheManager());
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如 @RequiresRoles, @RequiresPermissions)
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
```
### 扩展BaseJwtAuthorizingRealm
* 如果使用了扩展后的BaseJwtAuthorizingRealm子类，则在下面示例中，添加扩展后的子类到该bean中。

```
@Bean
public AuthorizingRealm authorizingRealm(){
    WxMemberJwtAuthorizingRealm jwtRealm = new WxMemberJwtAuthorizingRealm();
    return jwtRealm;
}
```

## 使用BaseUser类以及BaseAuthUser类进行认证

### BaseUser
* BaseUser是用户类的基础类，继承自BaseModel类，在默认的认证流程中，使用username，以及非openid的其他字段进行登录校验时，使用该类进行校验，BaseHelper.getCurrentUser()也将返回BaseUser实体。
* BaseUser一般用于其他类继承自该类，信息存储在其他子类相对应的表中

### BaseAuthUser
* BaseAuthUser是用户认证的基础类，继承自BaseUser类，在使用openid进行登录验证时，使用该类进行验证，BaseHelper.getCurrentUser()将返回BaseAuthUser实体。
* BaseAuthUser一般用于多种类型用户的统一认证，并且存在单独的auth_user表中，当其他类型的数据新建或更新时候，也新建或更新auth_user表中的记录。
* 比如一套系统中具有好几种用户，并且分别存在不同的表中，那么可以将那几种用户的信息统一同步到auth_user表中，在一个系统中，都是用auth_user进行登录授权等等。

## 授权核心类

### BaseJwtAuthenticationToken
* BaseJwtAuthenticationToken(HttpServletRequest request), 只传入request参数，默认是用username和password进行用户名密码授权，默认有效时间是120分钟。
* BaseJwtAuthenticationToken(HttpServletRequest request, long expiresInMinutes), 传入request和expiresInMinutes参数，后面的参数是过期时间参数，以分钟数计算。默认也以username和password进行用户名密码授权。
* BaseJwtAuthenticationToken(HttpServletRequest request, String fetchUserByField, String username, boolean preliminaryVerified), 可用于微信登录授权，以及其他方式授权等。
    * fetchUserByField， 用于授权的字段参数
    * username， 用于授权的字段值
    * preliminaryVerified， 是否已经完成登录验证
* BaseJwtAuthenticationToken(HttpServletRequest request, String fetchUserByField, String username, boolean preliminaryVerified, long expiresInMinutes)， 相比上面的构造方法，多传入了超时时间，其他功能相同。

### BaseJwtAuthorizingRealm
* 该类提供了登录，赋予角色权限，鉴权等功能。
* 默认在user表和auth_user表中查询相关信息，并授权登录。
* 扩展
    * 新建子类继承该类，并重写相关方法。
    * 更改赋予角色权限的方法，重写grantPermission方法。

    ```java
    @Override
    protected void grantPermission(SimpleAuthorizationInfo authorizationInfo, BaseUser user) {
        if (V.notEmpty(user.getRoleList())) {
            Set<String> roles = new HashSet(Arrays.asList(user.getRoleList()));
            authorizationInfo.setRoles(roles);
            authorizationInfo.addStringPermissions(roles);
        }
    }
    ```
    * 更改登录方法，重写doGetAuthenticationInfo方法。

    ```java
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

        // 对该用户的校验

        // 是否已初步验证过
        boolean isAuthed = jwt.isPreliminaryVerified();
    
        if(!isAuthed && V.notEmpty(secret)){
            // 密钥是否匹配
            isAuthed = Encryptor.encryptPassword(secret, user.getSalt()).equals(user.getPassword());
        }

        // 如果验证不通过，抛出异常，如果通过，返回SimpleAuthenticationInfo实例
        if(!isAuthed){
            throw new AuthenticationException(AUTH_EXCEPTION_MSG);
        }
    }
    ```
    * 更改鉴权流程，重写doGetAuthorizationInfo方法。

    ```java
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        BaseUser user = (BaseUser) principals.getPrimaryPrincipal();
        // 授权
        grantPermission(info, user);
        return info;
    }
    ```
    * 使用BaseAuthUser进行授权时，在子类中重写attachUserModel方法，可以将具体的用户实体，添加到BaseAuthUser中，将具体的用户实体，添加到BaseAuthUser的userModel属性中。添加该属性后，即可使用BaseHelper.getCurrentUser().getUserModel()获取到相对应的自定义用户实体。

    ```java
    @Override
    protected void attachUserModel(BaseAuthUser authUser){
        if (WxMember.class.getSimpleName().equalsIgnoreCase(authUser.getUserType())){
            WxMember wxMember = wxMemberService.getModel(authUser.getUserId());
            if (wxMember != null){
                authUser.setUserModel(wxMember);
            }
        }
    }
    ```



## 用户名密码登录
* 按照相关业务流程完成相关的用户名，和密码等的验证
* 用户名和密码验证通过后，开始生成token，例如：

```java
String username = request.getParameter("username");
BaseJwtAuthenticationToken authToken = new BaseJwtAuthenticationToken(request, BaseUser.F.username, username, true);
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
        return new JsonResult(token, "登录成功");
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
```
## 电话验证码登录

```java
String phone = request.getParameter("phone");
BaseJwtAuthenticationToken authToken = new BaseJwtAuthenticationToken(request, BaseUser.F.phone, phone, true);
//获取当前的Subject
Subject subject = SecurityUtils.getSubject();
String errorMsg = null;
try {
    subject.login(authToken);
    //验证是否登录成功
    if(subject.isAuthenticated()){
        logger.debug("用户[" + phone + "]申请token成功！authtoken="+authToken.getCredentials());
        String token = (String)authToken.getCredentials();
        // 跳转到首页
        return new JsonResult(token, "登录成功");
    }
}
catch(UnknownAccountException uae){
    errorMsg = "登录失败: 用户名或密码错误!";
    logger.warn("用户[" + phone + "]登录验证失败: 未知账户!", uae);
}
catch(IncorrectCredentialsException ice){
    errorMsg = "登录失败: 用户名或密码错误!";
    logger.warn("用户[" + phone + "]登录验证失败: 错误的用户名密码!", ice);
}
catch(LockedAccountException lae){
    errorMsg = "登录失败: 账号已被锁定!";
    logger.warn("用户[" + phone + "]登录验证失败: 账号已锁定!", lae);
}
catch(ExcessiveAttemptsException eae){
    errorMsg = "登录失败: 尝试次数过多，账号已被锁定!";
    logger.warn("用户[" + phone + "]登录验证失败: 失败尝试次数过多!", eae);
}
catch(AuthenticationException ae){
    errorMsg = "登录失败: 用户名或密码错误!";
    logger.warn("用户[" + phone + "]登录验证失败: 错误的用户名密码!", ae);
}
```
## 微信公众号使用oAuth授权登录
* 当用户访问相关回调地址，重定向到目标地址后，获取到code，通过相关接口使用code获取到相对应的openid
* 当完成openid相关的校验后，利用openid来进行token的获取和认证，例如：

```java
String code = request.getParameter("code");
String state = request.getParameter("state");

String openid = ....;   // 调用微信相关接口，获取code对应的openid
// 设置token
BaseJwtAuthenticationToken authToken = new BaseJwtAuthenticationToken(request, BaseAuthUser.F.openid, openid, true);
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
catch(Exception uae){
    errorMsg = "登录失败: 微信登录失败!";
    logger.warn("openid[" + openid + "]登录验证失败!", uae);
}
```

## 使用wechat或其他字段进行登录授权
* 当使用wechat或其他字段进行登录授权时，只需要更改创建BaseJwtAuthenticationToken实例时传入的相关参数以及捕获相关异常即可。例如利用wechat字段的授权如下：

```java
String wechat = ...;         // 获取到wechat
BaseJwtAuthenticationToken authToken = new BaseJwtAuthenticationToken(request, BaseUser.F.wechat, wechat, true);
try {
    subject.login(authToken);
    //验证是否登录成功
    if(subject.isAuthenticated()){
        token = (String)authToken.getCredentials();
        logger.debug("wechat[" + wechat + "]申请token成功！authtoken="+token);
    }
}
catch(Exception uae){
    errorMsg = "登录失败!";
    logger.warn("wechat[" + wechat + "]登录验证失败!", uae);
}
```