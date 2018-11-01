# Json工具类

> Json工具类继承自[fastjson](https://github.com/alibaba/fastjson)的com.alibaba.fastjson.JSONObject类，并扩展了如下方法:

* 将JSON字符串转换为Map、LinkedHashMap

```java
Map toMap(String jsonStr);
LinkedHashMap toLinkedHashMap(String jsonStr);
```

* 将json字符串转换为java对象

```java
<T> T toJavaObject(String jsonStr, Class<T> clazz);
```
