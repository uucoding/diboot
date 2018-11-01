# JSSDK初始化

对于网页调用js-sdk，我们提供了JSSDK初始化接口，通过访问该接口，将返回js-sdk配置数据，将其一一填入wx.config()方法中，将自动初始化JSSDK。

## 获取JSSDK配置接口

* 接口地址：[/wechat/mp/getJsSdkConfig]()
* 请求方式：[POST]()
* 传入参数：[url]()，请求JSSDK配置参数当前页面链接。前端一般使用[window.location.href.split('#')[0]]()获取
* 是否需要认证后调用：[是]()