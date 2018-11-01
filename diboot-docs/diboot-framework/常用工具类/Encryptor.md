# 加解密类

> 加密解密工具类提供已经封装好的加解密相关方法，如下：

* 密码单向加密类

```java
String  encryptPassword(BaseUser user);
String encryptPassword(String password, String salt);
```

* 字符串AES加密

```java
encrypt(String input, String... key);
```

* 字符串AES解密

```java
String decrypt(String input, String... key);
```
