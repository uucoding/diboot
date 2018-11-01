# ID生成器

> 数字类型的ID生成器，避免数据库自增字段ID连续、安全性差的问题

* 使用方法: 调用nextId()生成下一个id值

```java
getIdGenerator().nextId();
```

* getIdGenerator()方法需返回单例的IdGenerator实例。

<font color="red">注意：</font>如果项目部署有多个应用实例，需配置不同的id.generator.workerid属性值在application.properties中以避免多应用实例间产生重复id。
    
```properties
id.generator.workerid=2
```

