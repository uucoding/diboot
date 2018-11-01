# 数据校验类V

> 数据校验类提供常用的数据校验，可通过类似V.isEmpty()这样的调用方式直接使用。

* 判断是否为null或者空值对象（常用于字符串的判断等）

```java
boolean isEmpty();
boolean notEmpty();
```

* 数据格式校验

```java
boolean isNumeric();
boolean isEmail();
boolean isPhone();
```
    
* 统一校验

```java
String validate(String value, String validation);
```
        
*   对象类型和值的比对 (避免类似Long值==的坑)

```java
boolean equal(Object source, Object target);
boolean notEqual(Object source, Object target);
```
