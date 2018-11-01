# 字符串类S

> S继承自[apache.commons.lang3.StringUtils]()类，并扩展了以下常用方法，可通过S.xxx()进行调用。

* 将多个空格替换为一个

```java
String removeDuplicateBlank(String input);
```
    
*  格式转换:

```java
String[] toStringArray(List<String> stringList);
boolean toBoolean(String strValue);
Integer toInt(String strValue);
Long toLong(String strValue);
```

*   字符串拼接

```java
join(String[] stringArray);
join(List<String> stringList);
joinWithBr(List<String> stringList);
```

*   生成随机数字

```java
String newRandomNum(int length);
```

*   xss编码

```java
String xssEncode(String text);
```
