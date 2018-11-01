# JSSDK初始化

* 对于企业微信中的网页使用js-sdk相关接口功能，我们提供了相关数据接口，来一次性返回js-sdk接口初始化需要的所有参数。

## 获取JSSDK初始化参数

* 请求接口：[wechat/cp/getJsSdkConfig/{app}]()，{app}需替换为相关的app字段，比如[MSG]()。
* 传入参数：[url]()，请求JSSDK配置参数当前页面链接。前端一般使用[window.location.href.split('#')[0]]()获取。
* 是否需要认证后调用：[是]()。

## 将获取参数直接设置到wx.config字段中相关参数中，比如：

```javascript
// 假设前端获取到的结果为res
const data = res.data;
wx.config({
  debug: false,
  appId: data.appId, // 必填，公众号的唯一标识
  timestamp: data.timestamp, // 必填，生成签名的时间戳
  nonceStr: data.nonceStr, // 必填，生成签名的随机串
  signature: data.signature, // 必填，签名，见附录1
  jsApiList: appConfig.WEIXIN_JSSDK_API_LIST, // JSSDK接口列表
});
```