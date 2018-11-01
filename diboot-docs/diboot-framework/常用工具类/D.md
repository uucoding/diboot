# 日期时间类D
> 提供常用的日期时间处理方法的封装

## 格式常量

> 格式常量表示某种日期时间格式的一个字符串类型的常量，D类中已经内置了如下列表所示的格式常量，可以通过类似[D.FORMAT_DATE_y2M]()这样的调用来获取这些常量的内容。

* [FORMAT_DATE_y2M]() 值为 "yyMM", 格式化出的日期时间如：1806，表示18年6月；
* [FORMAT_DATE_y2Md]() 值为 "yyMMdd", 格式化出的日期时间如：180601，表示18年6月1日；
* [FORMAT_DATE_y4]() 值为 "yyyy", 格式化出的日期时间如：2018，表示2018年；
* [FORMAT_DATE_y4Md]() 值为 "yyyyMMdd", 格式化出的日期时间如：20180601，表示2018年6月1日；
* [FORMAT_DATE_Y4MD]() 值为 "yyyy-MM-dd", 格式化出的日期时间如：2018-06-01，表示2018年6月1日；
* [FORMAT_TIMESTAMP]() 值为 "yyMMddhhmmss", 格式化出的日期时间如：180601080808，表示18年6月1日8时8分8秒；
* [FORMAT_TIME_HHmm]() 值为 "HH:mm", 格式化出的日期时间如：11:11，表示11时11分；
* [FORMAT_TIME_HHmmss]() 值为 "HH:mm:ss", 格式化出的日期时间如：11:11:11，表示11时11分11秒；
* [FORMAT_DATETIME_Y4MDHM]() 值为 "yyyy-MM-dd HH:mm", 格式化出的日期时间如：2018-06-01 11:11，表示2018年6月1日11时11分；
* [FORMAT_DATETIME_Y4MDHMS]() 值为 "yyyy-MM-dd HH:mm:ss", 格式化出的日期时间如：2018-06-01 11:11，表示2018年6月1日11时11分11秒。


## 方法列表

> 以下方法中的fmt参数都可以输入上述相应的日期时间格式常量。

* 字符串转换为指定格式的日期

```java
Date convert2FormatDate(String datetime, String fmt);
```
  
* 日期转换为指定格式的字符串

```java
String convert2FormatString(Date date, String fmt);
```

* 日期增减某些天数
 
```java
String getDate(Date date, int... daysOffset);
```

* 日期时间增减某些天数

```java
String getDateTime(Date date, int... daysOffset);
```

* 模糊转换日期字符串（支持年月日、/、-、.等格式转换）为Date

```java
Date fuzzyConvert(String dateString);
```

* 获取当前日期时间特定字符串

```java
String now(String format);
```

...