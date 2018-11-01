# OAuth认证

* OAuth认证是基于[weixin-java-tools]()中的[OAuth认证接口](https://github.com/Wechat-Group/weixin-java-tools/wiki/MP_OAuth2%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83)来进行OAuth认证的。
* 默认的认证实现在该组件的 [com.diboot.wechat.mp.controller.TokenAuthController]() 类中，默认实现了获取回调链接，和回调获取授权token。

## 获取回调授权地址

* 接口地址：[/token/wechat/mp/buildOAuthUrl]()
* 请求方式：[GET]()
* 传入参数：[url]()
* 是否需要认证后调用：[否]()
* 当微信开发者工具或微信中访问该回调链接后，将重定向到url，并附带[code]()和[state]()的GET参数。

## 回调授权认证

### 接口

* 接口地址：[/token/wechat/mp/apply]()
* 请求方式：[POST]()
* 传入参数：[code](), [state]()

### 作用

* 当访问该接口的时候，请求头中带有有效的token参数，则将自动更换token，不再对code和state进行获取。
* 如果没有有效的token参数，则对state和code进行验证，并换取openid，根据openid再获取用户基本信息，如果openid已经存在于auth_user表，则更新auth_user表的信息，如果不存在auth_user表中，则新建该条记录。
* 您也可以通过调用[wexin-java-tools]()中的[回调授权接口](https://github.com/Wechat-Group/weixin-java-tools/wiki/MP_OAuth2%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83)来自定义相关处理流程。