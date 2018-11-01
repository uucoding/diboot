# XSS防护

* xss防护可以有效抵御xss攻击。
* framework中已经对xss防护相关的类进行了初始化和基础配置，只需要简单的引用即可使用。

## 使用方法

* 在您的项目中的web.xml中添加以下内容即可使用xss防护。

```xml
<filter>  
    <filter-name>xssFilter</filter-name>  
    <filter-class>dibo.framework.security.XSSProtectFilter</filter-class>  
</filter>  
<filter-mapping>  
    <filter-name>xssFilter</filter-name>  
    <url-pattern>/*</url-pattern>  
</filter-mapping>
```