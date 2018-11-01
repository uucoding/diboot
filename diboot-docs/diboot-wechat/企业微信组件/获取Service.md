# 获取Service

## 添加应用列表的枚举

* 在应用的Cons类中添加企业微信已有应用的枚举列表。

```java
public enum WX_CP_APP{
    MSG,
    POSTSALE,
    PRESALE
}
```

## 注入Factory类

* 在需要调用企业微信接口的类中，注入[WxCpServiceFactory]()类，如下：

```java
@Autowired
private WxCpServiceFactory wxCpServiceFactory;
```

## 获取WxCpService实例

* 根据相应的应用获取对应企业微信的WxCpService实例，如下： 

```java
String app = WX_CP_APP.MSG.name();
WxCpService wxCpService = wxCpServiceFactory.getCpService( app);
```