# Model基类

> framework默认提供BaseModel和BaseTreeModel两个基类:

* [BaseModel]()中默认提供: 
    * id(Long型主键)
    * uuid(String型主键)
    * createBy(创建人ID)
    * createTime(创建时间)
    * updateTime(更新时间)
    * active(逻辑删除标记)
    * extdata(扩展属性json)。
* [BaseTreeModel]()继承自BaseModel，同时扩展了以下属性，用于支持树形结构model。
    * parentId(上级id)
    * children(子节点集合)属性

## 属性字段定义类F
> 每个Model中都会有一个静态的F类(Field)，用于属性名的唯一定义处，任何需要使用属性名的地方，都可以直接引用F的定义，避免多处手写字符串出现不一致，同时也可借助IDE的提示高效输入。

示例: 

```java
// 用于构造查询条件
query.add(Metadata.F.itemName, "男");

// 用于指定更新字段
messageService.updateModel(model, Message.F.status, Message.F.extdata);

// 指定查询字段
metadataService.getKeyValuePairList(query.build(), Metadata.F.itemValue, Metadata.F.itemName);
```

如果您启用了diboot开发助理，Model的属性定义和F定义可跟数据库表同步更新，无需手写。

如未启用需注意更新保持F的定义与Model属性定义一致（Model中跟数据库表字段映射无关的扩展属性无需添加到F）。