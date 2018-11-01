# CrudController基类
BaseCrudController继承自BaseController，用于创建基于页面的增删改查类似的controller。
## 创建一个controller类
在您的项目中controller包下创建一个controller类，并继承自framework中的BaseCrudController类，并添加@Controller与@RequestMapping("")注解，如下：

```java
@Controller
@RequestMapping("/metadata")
public class MetadataController extends BaseCrudController {

}
```

## 重写抽象方法
* 在framework的BaseCrudController中，共具有一个抽象方法。
* 在新建的类中，重写该方法，返回注入的相关Service。

```java
@Autowired
private MetadataService metadataService;

@Override
protected BaseService getService() {
    return metadataService;
}

```

## 使用已有功能
在framework中的BaseCURDController中，已经内置了相关功能，比如列表页，详情页，新建更新页的数据处理流程等，您需要做的只是将需要的功能添加一个方法在新建的controller中，并且调用父级相关方法并返回。
### 创建列表页面
* 基本用法如下，该功能自带分页以及获取Get参数作为查询条件的能力。

```java
@Override
@GetMapping("/list")
public String list(HttpServletRequest request, ModelMap modelMap) throws Exception {
    // 加载第一页
    return super.listPaging(1, request, modelMap);
}

@Override
@GetMapping("/list/{pageIndex}")
public String listPaging(@PathVariable("pageIndex")int pageIndex, HttpServletRequest request, ModelMap modelMap) throws Exception{
    return super.listPaging(pageIndex, request, modelMap);
}
```

* 自定义列表的查询条件

```java
@Override
@GetMapping("/list/{pageIndex}")
public String listPaging(@PathVariable("pageIndex")int pageIndex, HttpServletRequest request, ModelMap modelMap) throws Exception{
    // 附加条件
    Query query = new Query();
    query.add(Metadata.F.parentId, 0);
    query.add(Metadata.F.editable, true);
    modelMap.addAttribute("criteria", query.build());
    return super.listPaging(pageIndex, request, modelMap);
}
```

* 列表查看方法会调用attachMore()方法，如果您需要传递关联数据到页面，可以重写attachMore方法。
```java
@Override
protected void attachMore(HttpServletRequest request, ModelMap modelMap) throws Exception{
    // 附加关联数据到modelMap
}
```

### 创建详情页面
* 基本用法如下，该功能将自动根据id从service中获取model，并且会调用attachMore4View()方法对关联对象进行获取，如果您对关联对象的获取有要求，不妨重写该方法。

```java
@GetMapping("/view/{id}")
public String viewPage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {  
    return super.viewPage(id, request, modelMap);
}
```

* 自定义获取model过程。

```java
@GetMapping("/view/{id}")
public String viewPage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {  
    Metadata model = metadataServiceImpl.getModel(id);
    modelMap.addAttribute("model", model);
    return super.viewPage(id, request, modelMap);
}
```

* 该方法会调用attachMore4View()方法，如果您需要传递关联数据到页面，可以重写attachMore方法。
```java
@Override
protected void attachMore4View(Object id, BaseModel model, ModelMap modelMap){
    // 获取所有子元素
    List<Metadata> children = metadataService.getModelList(super.newCriteria(BaseTreeModel.F.parentId, id));
    modelMap.put("children", children);
}
```

### 创建新建页面
* 基本用法如下:

```java
@GetMapping("/create")
public String createPage(HttpServletRequest request, ModelMap modelMap) throws Exception {  
    return super.createPage(request, modelMap);
}
```
* 该方法会调用attachMore()方法，如果您需要传递关联数据到更新页面，可以重写attachMore方法。

### 接收新建数据
* 采用绑定的方式接收提交的数据，并会进行后端校验，校验的相关注解在model文件中编写，对于校验不通过的错误信息会添加到modelMap中。

```java
@PostMapping("/create")
public String create(@Valid Metadata model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
    return super.create(model, result, request, modelMap);
}
```

* 在新建数据的时候，如果在application.properties配置文件中设置diboot.log.trace-enabled=false，将会保存新建的操作记录。
* 在新建结束之后，默认返回到新建后的详情页面，如果传入了continue=true的post参数，则会跳转到新建页面继续新建，如果需要在新建成功后需要跳转到其他位置，可以在调用super.create()方法之前，将redirect属性添加到modelMap中，例如：

```java
@PostMapping("/create")
public String create(@Valid Metadata model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
    String redirect = getUrlPrefix() + "/detail/" + model.getPk();
    modelMap.addAttribute("redirect", redirect);
    return super.create(model, result, request, modelMap);
}
```

* 在新建之前如果需要进行一些操作，可以重写beforeCreate()方法。

```java
protected void beforeCreate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
    
}
```

* 在新建成功之后如果需要进行一些操作，可以重写afterCreated()方法。

```java
protected String afterCreated(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
    return null;
}
```

### 创建更新页面
* 基本用法如下:

```java
@GetMapping("/update/{id}")
public String updatePage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {  
    return super.updatePage(id, request, modelMap);
}
```
* 同列表/新建页面一样, 该方法也会调用attachMore()方法，如果您需要传递关联数据到更新页面，可以重写attachMore方法。

* 自定义model或更改model属性时，可以将model获取到之后并添加到modelMap后，再调用父级方法。

```java
@GetMapping("/update/{id}")
public String updatePage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {  
    Metadata model = metadataServiceImpl.getModel(id);
    modelMap.addAttribute("model", model);
    return super.updatePage(id, request, modelMap);
}
```

### 接收更新数据
* 接收更新数据仍然采用数据绑定方式，同新建数据的接收。

```java
@PostMapping("/update/{id}")
public String update(@PathVariable("id")Long id, @Valid Metadata model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception{
    return super.update(id, model, result, request, modelMap);
}
```

* 在更新数据的时候，如果在application.properties配置文件中设置diboot.log.trace-enabled=false，将会保存更新的操作记录。
* 在更新之前如果需要进行一些操作，可以重写beforeUpdate()方法。

```java
protected void beforeUpdate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
}
```

* 在更新成功之后如果需要进行一些操作，可以重写afterUpdated()方法。

```java
protected String afterUpdated(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
    return null;		
}
```

### 删除已有数据
* 删除已有数据，需要传入id数据，同时也会根据diboot.log.trace-enabled的配置项来决定是否记录删除的操作记录。

```java
@ResponseBody
@PostMapping("/delete/{id}")
public Map<String, Object> delete(@PathVariable("id")Long id, HttpServletRequest request) throws Exception{
    return super.delete(id, request);
}
```

* 在删除之前如果需要进行一些操作，可以重写beforeDelete()方法，改方法返回的为自定义处理流程的错误提示，默认为下，不可删除数据。

```java
protected String beforeDelete(BaseModel model){
    return "您无权删除该数据！";
}
```

* 在删除成功之后如果需要进行一些操作，可以重写afterDeleted()方法。

```java
protected void afterDeleted(HttpServletRequest request, BaseModel model) {
}

```