# Excel数据导入

## 创建数据导入页面的controller与views

### 创建controller

* 在controller包下创建相关controller类，并继承[BaseExcelImportController]()类，如下：

```java
// Excel上传样例
@Controller
@RequestMapping("/student/import")
public class StudentImportController extends BaseExcelImportController {
    private static Logger logger = LogManager.getLogger(StudentImportController.class);

    @Autowired
    private StudentService studentService;
    
    @Override
    protected String getViewPrefix() {
        return "student/import";
    }

    /***
    * 根路径/
    */
    @Override
    @GetMapping("/")
    public String home(HttpServletRequest request, ModelMap modelMap) throws Exception {
        return list(request, modelMap);
    }

    /***
    * 显示首页面
    */
    @Override
    @GetMapping("/list")
    public String list(HttpServletRequest request, ModelMap modelMap) throws Exception {
        // 加载第一页
        return listPaging(1, request, modelMap);
    }

    /**
    * 显示首页-分页
    */
    @Override
    @GetMapping("/list/{pageIndex}")
    public String listPaging(@PathVariable("pageIndex")int pageIndex, HttpServletRequest request, ModelMap modelMap) throws Exception{
        return super.listPaging(pageIndex, request, modelMap);
    }

    /***
    * 预览数据
    */
    @Override
    @PostMapping("/preview")
    public String preview(HttpServletRequest request, ModelMap modelMap) throws Exception {
        return super.preview(request, modelMap);
    }

    /***
    * 预览数据
    */
    @Override
    @PostMapping("/previewSave")
    public String previewSave(HttpServletRequest request, ModelMap modelMap) throws Exception {
        return super.previewSave(request, modelMap);
    }

    /***
    * 显示查看详细页面
    * @return view
    * @throws Exception
    */
    @Override
    @GetMapping("/view/{importUid}")
    public String viewPage(@PathVariable("importUid")Object importUid, HttpServletRequest request, ModelMap modelMap) throws Exception {
        return super.viewPage(importUid, request, modelMap);
    }

    /***
    * 创建的后台处理
    * @return view
    * @throws Exception
    */
    @Override
    @PostMapping("/upload")
    public String upload(HttpServletRequest request, ModelMap modelMap) throws Exception {
        return super.upload(request, modelMap);
    }

    /***
    * Excel数据导入-数据转换辅助类
    */
    @Override
    protected List<String[]> transform(List<ExcelColumn> excelColumns, List<String[]> dataList, boolean isPreview) throws Exception{
        if(V.isEmpty(excelColumns)){
            return dataList;
        }
        return dataList;
    }

    @Override
    protected Class<?> getModelClass() {
        return Student.class;
    }

    @Override
    protected BaseService getBusinessService() {
       return studentService;
    }
}
```

### 创建views文件

* 创建相关views文件，在views下的业务文件夹下新建import文件夹，在import文件中添加相关页面文件，比如在该示例中为 [webapps/WEB-INF/views/student/import/]() 文件夹下，也可以自定义其他路径，但需要更改以上示例代码中的 [getViewPrefix()]() 方法，返回相对应的自定义路径。
* 在创建的相关import文件夹中，需要有三个文件，list.ftl, preview.ftl, view.ftl。
    * [list.ftl]() 显示上传列表记录的页面，并且提供选择文件和提交文件等功能。
    * [preview.ftl]() 如果需要有上传数据预览功能，则需要有该页面，显示上传数据列表。
    * [view.ftl]() 对上传后的数据进行查看。

## BaseExcelImportController相关属性与方法

### 属性

* [baseFileService]() 文件处理Service实例，可以调用相关方法对数据库file表进行增删改查等操作。
* [excelColumnService]() Excel上传列定义Service实例，可以调用相关方法对excel_column标进行增删改查等操作。
* [excelImportRecordService]() 导入记录Service实例，可以调用相关方法对excel_import_record表进行相关操作。BaseExcelImportController类中的处理流程在上传成功后也将自动添加上传记录。
* [PARAM_IMPORT_UUID]() 导入记录字段，内容默认是"importUid"，上传预览页面，在提交时候需要提交该字段和相对应的值。

### 方法

* [getModelClass()]() 获取业务Model类，继承后必须重写该方法，示例如下。

```java
@Override
protected Class<?> getModelClass() {
    return Student.class;
}
```

* [getBusinessService()]() 获取业务Service实例，集成后必须重写该方法，实例如下。

```java
@Override
protected BaseService getBusinessService() {
    return studentService;
}
```

* [listPaging()]() 获取上传记录列表数据，并根据页码来进行分页，并添加到modelMap中[modelList]()字段，使用示例如下：

```java
@Override
@GetMapping("/list")
public String list(HttpServletRequest request, ModelMap modelMap) throws Exception {
    // 加载第一页
    return listPaging(1, request, modelMap);
}

@Override
@GetMapping("/list/{pageIndex}")
public String listPaging(@PathVariable("pageIndex")int pageIndex, HttpServletRequest request, ModelMap modelMap) throws Exception{
    return super.listPaging(pageIndex, request, modelMap);
}
```

* [preview()]() 预览上传数据，并将上传数据添加到modelMap中[dataList]()字段，上传记录添加到[PARAM_IMPORT_UUID]()常量定义的字段中，使用示例如下：

```java
@Override
@PostMapping("/preview")
public String preview(HttpServletRequest request, ModelMap modelMap) throws Exception {
    return super.preview(request, modelMap);
}
```

* [previewSave()]() 预览保存数据，用来在预览后进行保存数据的操作，使用该方法，需要在前端将预览时候传入到modelMap中的[PARAM_IMPORT_UUID]()常量字段表示的值提交到后端，然后获取相关的dataList，保存到数据库，使用示例如下：

```java
@Override
@PostMapping("/previewSave")
public String previewSave(HttpServletRequest request, ModelMap modelMap) throws Exception {
    return super.previewSave(request, modelMap);
}
```

* [upload()]() 直接上传文件数据，直接从request读取上传excel文件，然后读取该文件中的数据列表，并保存到数据库，使用示例如下：

```java
@Override
@PostMapping("/upload")
public String upload(HttpServletRequest request, ModelMap modelMap) throws Exception {
    return super.upload(request, modelMap);
}
```

* [transform()]() 转换数据，以及对相关字段数据进行处理，如果对某些字段的数据有自定义处理的需求，可以重写该方法，示例如下：

```java
@Override
protected List<String[]> transform(List<ExcelColumn> excelColumns, List<String[]> dataList, boolean isPreview) throws Exception{
    if(V.isEmpty(excelColumns)){
        return dataList;
    }
    return dataList;
}
```