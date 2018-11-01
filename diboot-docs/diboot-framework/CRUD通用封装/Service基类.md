# Service基类

## BaseService(BaseServiceImpl)默认实现的接口

* Service基类提供了常用CRUD场景的Java代码部分实现。
    在使用过程中，如果默认实现可以满足您的需要，那么在您新增的service实现中，无需再写相关实现代码，
    否则可以重写覆盖相关接口的实现，来实现特定需求。

### 获取Model

* <T extends BaseModel>T getModel(Object pk);
    * 根据主键获取对应的记录，并返回该条记录的model实体

```java
    Metadata model = medatadaService.getModel(120L);
```

### 创建(批量创建)Model
* boolean createModel(BaseModel model);
    * 创建单条记录（非数据库自增主键，创建前会先生成主键）

```java
    boolean success = medatadaService.createModel(metadata);
```

* <T extends BaseModel> boolean batchCreateModels(List<T> modelList);
    * 批量创建多条记录，数量超100条会分批次插入（100条/批次）。（非数据库自增主键，创建前会先生成主键）
    
```java
    boolean success = medatadaService.batchCreateModels(newMetadataList);
```
    
### 更新Model
* boolean updateModel(BaseModel model, String... updateFields);
    * 更新一条数据记录
    * updateFields是一个变参，如果传入，该接口对应的update语句只更新该变量中指定的字段，其他字段将不更新，避免脏写。

```java
    // 更新全部字段
    boolean success = medatadaService.updateModel(metadata);
    // 仅更新itemValue字段
    boolean success = medatadaService.updateModel(metadata, Metadata.F.itemValue);
```

* boolean createOrUpdateModel(BaseModel model);
    * 创建或更新Model（model为新的对象(isNew==true)则调用createModel新建，否则调用updateModel更新）

```java
    boolean success = medatadaService.createOrUpdateModel(metadata);
```

### 删除数据
* boolean deleteModel(Object pk);
    * 根据主键删除一条数据记录
    * 在系统提供的默认实现中，为逻辑删除，即将active字段更新为0，所有查询中通过active=1来筛选有效数据

```java
    boolean success = medatadaService.deleteModel(120L);
```

### 查询数据
* int getModelListCount(Map<String, Object> criteria)
    * 根据传入的条件返回符合条件的记录数(criteria为null则返回所有有效记录数)

```java
    Query query = new Query();
    query.add(Metadata.F.parentId, 0);
    int totalCount = medatadaService.getModelListCount(query.build());
```

* <T extends BaseModel> List<T> getModelList(Map<String, Object> criteria, int... page)
    * 根据条件以及页码，来获取数据列表
    * page参数为变参，传入则分页，不传则返回符合条件的所有记录
    
```java
    Query query = new Query();
    query.add(Metadata.F.parentId, 0);
    List<Metadata> modelList = medatadaService.getModelList(query.build()); //所有
    List<Metadata> modelList = medatadaService.getModelList(query.build(), 1); //第一页
```

* <T extends BaseModel> List<T> getLimitModelList(Map<String, Object> criteria, int limitCount)
    * 查询符合条件的指定条数记录 
    
    ```java
      Query query = new Query(Metadata.F.type, "GENDER");
      List<Metadata> modelList = medatadaService.getLimitModelList(query.build(), 10); //返回10条
    ```
* <T extends BaseModel>T getSingleModel(Map<String, Object> criteria)
    * 查询符合条件的1条记录

```java
    Query query = new Query(Metadata.F.itemName, "男");
    Metadata metadata = medatadaService.getSingleModel(query.build()); //返回10条
```

* List<Map<String, Object>> getMapList(Map<String, Object> criteria, String[] loadFields, int... page);
    * 根据条件，以及需要获取的字段数组，以及页码，来返回一个Map类型的数据列表，适用于仅查询少数列不需要返回Model的场景
    * loadFields是返回对象的数组，通过Model中F静态类指定
    * 分页参数page为变参，不传表示获取符合条件的所有记录

```java
    Query query = new Query();
    query.add(Metadata.F.type, "GENDER");
    query.addGT(Metadata.F.parentId, 0);
    List<Map<String, Object>> mapList = medatadaService.getMapList(query.build(), new String[]{Metadata.F.id, Metadata.F.itemName, Metadata.F.itemValue});
```

* List<Map<String, Object>> getKeyValuePairList(Map<String, Object> criteria, String keyField, String valueField);
    * 根据传入的条件，获取相关的键值对列表
    * 常用于关联对象数据获取 或者 构建前端下拉框等场景 

```java
    Query query = new Query();
    query.add(Metadata.F.type, "GENDER");
    query.addGT(Metadata.F.parentId, 0);
    List<Map<String, Object>> mapList = medatadaService.getKeyValuePairList(query.build(), Metadata.F.itemName, Metadata.F.itemValue);
```
  
## 新建一个CRUD的Service
1.  在项目service包目录下创建一个service接口，并继承自framework中的BaseService接口，例如：

```java
public interface MyCustomService extends BaseService{
}
```

2.  创建service实现类
在项目中service/impl的包目录下，建一个service实现类，继承自framework中的BaseServiceImpl方法并实现你的service接口，并添加@Service注解，
例如：

```java
@Service
public class MetadataServiceImpl extends BaseServiceImpl implements MetadataService {
    @Autowired
    private MetadataMapper metadataMapper;
    // 返回你的mapper
    @Override
    protected BaseMapper getMapper(){return metadataMapper;}
}
```

service实现类中需要注入你的mapper，以便对接接口的SQL实现
