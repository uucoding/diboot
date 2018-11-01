# CrudRestController基类
创建一个用于接口的RestController
## 创建一个controller类
在您的项目中controller包下创建一个controller类，并继承自framework中的BaseCrudRestController类，并添加@RestController与@RequestMapping("")注解，如下：

```java
@RestController
@RequestMapping("/user")
public class UserController extends BaseCrudRestController {

}
```

## 重写抽象方法
* 在新建的类中，重写getService()方法，返回你的业务相关Service

```java
@Autowired
private BaseUserService baseUserService;

@Override
protected BaseService getService() {
    return baseUserService;
}
```

## 使用已有功能
在framework中的BaseCURDController中，已经内置了相关功能，比如列表页，详情页，新建更新页的数据处理流程等，您需要做的只是将需要的功能添加一个方法在新建的controller中，并且调用父级相关方法并返回。
### 获取列表数据
* 基本用法如下：

```java
@GetMapping("/list")
@Override
public JsonResult getModelList(HttpServletRequest request, ModelMap modelMap) throws Exception{
    return super.getModelList(request, modelMap);
}
```

* 分页相关属性
    * page：如果GET参数中带有改参数值，则获取该页的数据，如page=2，则获取第二页的数据，如果没有该数据，则默认获取第一页的数据。
    * pageSize： 如果GET参数中带有改参数值，则根据该数值获取该页的分页数据
    * 如果需要获取所有数据，则在当前新建方法内，直接调用相关方法获取到modelList，并添加到modelMap中即可，例如：

```java
@GetMapping("/list")
@Override
public JsonResult getModelList(HttpServletRequest request, ModelMap modelMap) throws Exception{
    List<BaseUser> modelList = baseUserService.getModelList(null);
    modelMap.addAttribute("modelList", modelList);
    return super.getModelList(request, modelMap);
}
```

* 获取列表数据将会调用attachMore方法，如果需要增加更多关联数据到model实体中，则重写该方法

```java
@Override
protected void attachMore(HttpServletRequest request, ModelMap map) throws Exception {
}
```

### 获取详情数据
* 基本用法如下：

```java
@GetMapping("/{id}")
public JsonResult getModel(@PathVariable("id")Long userId, HttpServletRequest request, ModelMap modelMap)
        throws Exception{
    return super.getModel(userId, request, modelMap);
}
```

* 如果在该方法调用前将model添加到modelMap中，则会直接返回该model，而不会在父级方法中去重新获取，这种方案适用于需要对model进行处理的情况。
* 该方法会调用attachMore4View()，为model添加更多属性，如果需要对model进行特殊处理，也可以在重写后的该方法中进行处理。
* 如果开启了访问日志记录，访问到该接口则会添加一条访问记录。（关于访问日志记录）
### 新建数据
* 基本用法如下：

```java
@PostMapping("/")
public JsonResult createModel(@ModelAttribute BaseUser user, BindingResult result, HttpServletRequest request, ModelMap modelMap)
throws Exception{
    return super.createModel(user, result, request, modelMap);
}
```
* 该接口通过数据绑定来获取提交的数据，如果绑定出错，将会返回FAIL_VALIDATION的错误码，并返回错误信息到消息字段中。
* 如果开启了操作日志记录，访问到该接口则会添加一条新建操作记录。（关于操作日志记录）
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

### 更新数据
* 基本用法如下：

```java
@PutMapping("/{id}")
public JsonResult updateModel(@PathVariable("id")Long id, @ModelAttribute BaseUser user, BindingResult result, HttpServletRequest request, BaseModel model, ModelMap modelMap) {
    return super.updateModel(user, result, request, modelMap);
}
```

* 该接口通过数据绑定来获取提交的数据，如果绑定出错，将会返回FAIL_VALIDATION的错误码，并返回错误信息到消息字段中。
* 如果开启了操作日志记录，访问到该接口则会添加一条更新操作记录。（关于操作日志记录）
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

### 删除数据
* 基本用法如下：

```java
@DeleteMapping("/{id}")
public JsonResult deleteModel(@PathVariable("id")Long id, HttpServletRequest request) {
    return super.deleteModel(id, request);
}
```

* 该接口会删除主键为id的数据记录。
* 如果删除失败，会返回相应错误码的JsonResult结果。
* 在删除之前如果需要进行一些操作，可以重写beforeDelete()方法，改方法返回的为自定义处理流程的错误提示，默认为下，不允许删除数据。

```java
protected String beforeDelete(BaseModel model) {
        return Status.FAIL_NO_PERMISSION.label();
    }
```

* 在删除成功之后如果需要进行一些操作，可以重写afterDeleted()方法。

```java
protected void afterDeleted(HttpServletRequest request, BaseModel model) {
}

```

## Rest接口返回类型 JsonResult
* JsonResult类，用于构建Rest接口返回数据时使用，
### 创建JsonResult实例

* 引用示例如下：
```java
 new JsonResult()，默认返回status为OK的code码。
 new JsonResult(code)， 返回指定status的code码。
 new JsonResult(code, msg), 返回指定status的code码，返回提示消息。
 new JsonResult(code, data, msg), 返回制定status的code码，并返回相关的data数据，且返回提示消息。
```
### code引用Status中相应状态码
* Status在com.diboot.framework.config包中
* 
## Rest接口方法返回值
* Rest接口方法可以返回Map类型数据，也可以按照我们framework中的标准，来返回JsonResult类的实例。
* Status状态码定义（com.diboot.framework.config.Status）
```java
    OK(0, "操作成功"),  // 请求处理成功
    
    WARN_PARTIAL_SUCCESS(1001, "部分成功"), // 部分成功
    WARN_PERFORMANCE_ISSUE(1002, "潜在的性能问题"), // 有性能问题

    FAIL_INVALID_PARAM(4000, "请求参数不匹配"),       // 传入参数不对
    FAIL_INVALID_TOKEN(4001, "Token无效或已过期"),    // token无效
    FAIL_NO_PERMISSION(4003, "没有权限执行该操作"),  // 无权查看
    FAIL_NOT_FOUND(4004, "请求资源不存在"),  // 404 页面不存在
    FAIL_VALIDATION(4005, "提交数据校验不通过"),     // 数据校验不通过
    FAIL_OPERATION(4006, "操作执行失败"),     // 操作执行失败
    FAIL_EXCEPTION(5000, "系统异常");       // 系统异常
```