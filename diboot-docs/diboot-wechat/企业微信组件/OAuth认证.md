# OAuth认证

## 使用已有认证

* 在企业微信组件中已经内置了基础的OAuth认证流程。
* 相关类[AuthTokenController]()

### 获取OAuth授权地址

* 请求接口地址：[/token/wechat/cp/buildOAuthUrl/{app}]()，{app}需替换为相关的app字段，比如[MSG]()。
* 请求方式：[GET]()。
* 传入参数：[url]()。
* 是否需要认证后调用：[否]()。
* 获取到的地址，可以在微信开发工具中测试，或者添加到应用的菜单，或页面内的按钮上。

### OAuth校验接口

* 请求接口地址：[/token/wechat/cp/apply/{app}]()，{app}需替换为相关的app字段，比如[MSG]()。
* 请求方式：[POST]()。
* 传入参数：[code](), [state]()。
* 当访问该接口的时候，请求头中带有有效的token参数，则将自动更换token，不再对code和state进行获取。如果没有有效的token参数，则根据code获取到UserId参数，在系统中查询相关信息后，来生成新token进行授权。

## 自定义OAuth授权过程

* [获取Service](获取Service.md)
* [weixin-java-tools](https://github.com/Wechat-Group/weixin-java-tools)的OAuth认证相关接口详见[CP_OAuth2网页授权文档](https://github.com/Wechat-Group/weixin-java-tools/wiki/CP_OAuth2%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83)

