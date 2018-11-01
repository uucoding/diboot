# Bean工具类

> Bean工具类可以进行与模型相关的处理，通过类似BeanUtils.copyProperties()这样的调用即可使用相关方法。

* 仅依赖Spring的BeanUtils类（不需依赖apache的BeanUtils）

* 拷贝属性

```java
Object copyProperties(Object source, Object target);
```

* 绑定map中的属性到model

```java
void bindProperties(BaseModel model, Map<String, Object> propMap);
```

* 将request请求参数值绑定到model

```java
void buildModel(BaseModel model, HttpServletRequest request);
```

* 将model列表转换为<指定字段值-model>的map对象，用于后续快速根据指定字段值查找到对应的model对象

```java
<T extends BaseModel> Map<Object, T> convert2KeyModelMap(List<T> allLists, String... fields);
```

* 将model列表转换为<指定字段值-model列表>的map对象，用于后续快速根据指定字段值查找到对应的model列表

```java
<T extends BaseModel> Map<Object, List<T>> convert2KeyModelListMap(List<T> allLists, String field);
```

* 将treeModel列表转换为树形结构的对象列表（返回顶层对象列表，子级节点挂载到父级的children对象中）

```java
<T extends BaseTreeModel> List<T> buildTreeModels(List<T> allModels);
```

* 提取两个model对象的差异(用于跟踪日志记录数据变化)

```java
String extractDiff(BaseModel oldModel, BaseModel newModel);
```

* 绑定一对一的关系（根据指定的属性值相等自动绑定一对一的关系）

```java
void bindOne2One(List<? extends BaseModel> leftModelList, String leftModelBindField, List<? extends BaseModel> rightModelList, String left2rightMapping);

// 示例如下：
BeanUtils.bindOne2One(users, "department", departments, "id=id");
```

* 绑定一对多的关系（根据指定的属性值相等自动绑定一对多的关系）

```java
void bindOne2Many(BaseModel model, String oneModelField, List<? extends BaseModel> manyModelList, String left2rightMapping)；

// 示例如下：
BeanUtils.bindOne2Many(users, "menus", menus, "id=id");
```