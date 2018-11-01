# Excel数据导出

## 添加数据导出地址

* 创建一个controller，或使用其他需要导出业务的controller。
* 添加导出地址和方法，如下：

```java
@GetMapping("/export")
public String export(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception{
}
```

## 获取导出数据

### mapper相关

* 在相应的Service的getMapper()对应的Mapper类及Mapper.xml中添加获取导出数据的接口及SQL。

```java
List<LinkedHashMap> getExportMapList(@Param("c") Map<String, Object> var1);
```

```xml
<select id="getExportMapList" parameterType="Map" resultType="java.util.LinkedHashMap">
    SELECT self.name AS '姓名', self.wechat AS '微信', self.phone AS '电话', t.name AS '班主任'
    FROM student self
    LEFT JOIN teacher t ON self.teacher_id=t.id
    <where>
        <include refid="conditions" />
    </where>
    ORDER BY self.id DESC
    <if test="c.COUNT != null">
        LIMIT <if test="c.OFFSET != null">#{c.OFFSET}, </if>#{c.COUNT}
    </if>
</select>
```

### service接口

* 在相对应的Service中添加获取导出的接口及实现。

```java
/**
* 获取导出数据
* @param criteria
* @return
*/
List<LinkedHashMap> getExportMapList(Map<String, Object> criteria, int... page);
```

```java
@Override
public List<LinkedHashMap> getExportMapList(Map<String, Object> criteria, int... page){
    if (criteria == null) {
        criteria = new HashMap(8);
    }

    if (page != null && page.length > 0) {
        int pageSize = page.length > 1 ? page[1] : BaseConfig.getPageSize();
        ((Map)criteria).put("OFFSET", pageSize * (page[0] - 1));
        ((Map)criteria).put("COUNT", pageSize);
    }

    List<LinkedHashMap> list = studentMapper.getExportMapList((Map)criteria);

    return list;
}
```

### controller中添加获取数据方法

* 处理请求参数以及查询条件等，获取导出数据列表。

```java
/**
* 获取导出数据列表
* @param request
* @param modelMap
* @return
* @throws Exception
*/
private List<LinkedHashMap> getExportDataList(HttpServletRequest request, ModelMap modelMap) throws Exception{

    Integer pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
    Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
    Map<String, Object> criteria = super.buildQueryCriteria(request, pageIndex);

    modelMap.addAttribute("criteria", criteria);

    List<LinkedHashMap> mapList = studentService.getExportMapList(criteria, new int[]{pageIndex, pageSize});

    if (V.isEmpty(mapList)){
        addResultMsg(request, false, "未查询到相关导出数据");
        return null;
    }

    return mapList;
}
```

## 导出Excel文件

* [FileHelper.buildExcelFile(mapList, filename)]() 生成一个excel文件到服务器中。
* [FileHelper.downloadLocalFile(fullpath, request, response)]() 该方法执行后，将开始下载服务器上fullpath路径下的文件文件到本地。
* 整个下载流程示例如下：

```java
@GetMapping("/export")
public String export(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception{
    List<LinkedHashMap> mapList = getExportDataList(request, modelMap);
    if (V.notEmpty(mapList)){
        for (LinkedHashMap map : mapList){
            // 如果对相关字段，有特殊的处理，可以在此处进行处理。
        }

        Date now = new Date();
        String filename = "学生数据导出 " + D.getDate(now) + ".xls";
        String fullpath = FileHelper.buildExcelFile(mapList, filename);

        if (V.notEmpty(fullpath)){
            FileHelper.downloadLocalFile(fullpath, request, response);
            return null;
        }

        addResultMsg(request, false, "导出数据失败");
        return list(request, modelMap);
    }

    // build Excel失败，返回页面并提示
    addResultMsg(request, false, "未查询到相关数据");
    return list(request, modelMap);
}
```