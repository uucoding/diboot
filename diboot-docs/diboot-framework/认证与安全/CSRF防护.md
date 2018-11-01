# CSRF防护

* CSRF防护功能可以有效防护CSRF攻击。
* framework中已经内置了对CSRF攻击的防护支持，您只需要进行简单的引用即可使用。

## 使用方法

* 在您的项目的SpringMvcConfig类中进行以下方法的配置，即可轻松使用CSRF防护功能。

```java
import com.diboot.framework.security.CsrfProtectionInterceptor;

@Override
public void addInterceptors(InterceptorRegistry registry) {
    // 默认拦截器
    SecurityInterceptor appInterceptor = new SecurityInterceptor();
    registry.addInterceptor(appInterceptor);
    // CSRF拦截器
    CsrfProtectionInterceptor csrfInterceptor = new CsrfProtectionInterceptor();
    csrfInterceptor.setIgnoreAjaxRequest(true);
    registry.addInterceptor(csrfInterceptor);
}
```