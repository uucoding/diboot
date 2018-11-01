package com.diboot.framework.utils;


import com.diboot.framework.config.BaseCons;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseTreeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.cglib.beans.BeanCopier;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Model相关处理Helper类
 * @author Mazc@dibo.ltd
 * @version 2017/8/12
 * Copyright @ www.dibo.ltd
 */
public class BeanUtils{
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 连接符号
     */
    private static final String CHANGE_FLAG = "->";
    /**
     * 关联连接符号
     */
    private static final String REF_FLAG = "=";

    /**
     * 忽略对比的字段
     */
    private static final Set<String> IGNORE_FIELDS = new HashSet<String>(){{
        add(BaseModel.F.createTime);
        add("new");
        add("modelName");
    }};

    /***
     * 缓存BeanCopier
     */
    private static Map<String, BeanCopier> BEAN_COPIER_INST_MAP = new ConcurrentHashMap<>();

    /***
     * 获取实例
     * @param source
     * @param target
     * @return
     */
    private static BeanCopier getBeanCopierInstance(Object source, Object target){
        //build key
        String beanCopierKey =  source.getClass().toString() +"_"+ target.getClass().toString();
        BeanCopier copierInst =  BEAN_COPIER_INST_MAP.get(beanCopierKey);
        if(copierInst == null){
            copierInst = BeanCopier.create(source.getClass(), target.getClass(), false);
            BEAN_COPIER_INST_MAP.put(beanCopierKey, copierInst);
        }
        return copierInst;
    }

    /**
     * Copy属性到另一个对象
     * @param source
     * @param target
     */
    public static Object copyProperties(Object source, Object target){
        BeanCopier copierInst =  getBeanCopierInstance(source, target);
        copierInst.copy(source, target, null);
        return target;
    }

    /**
     * 将字符串转换为指定类型的对象
     * @param type
     * @param value
     * @return
     * @throws Exception
     */
    public static Object convertValue2Type(String value, String type) throws Exception{
        if (V.isEmpty(type)){
            throw new Exception("参数传递错误：类型未定义!");
        }
        // 如果值为空  返回包装类型null
        if (value == null){
            return value;
        }
        type = S.trim(type);
        // 处理boolean值
        if (Boolean.class.getSimpleName().equalsIgnoreCase(type)){
            return V.isTrue(value);
        }
        // 其他情况交由BeanUtils转换
        // 其他情况直接返回string
        return value;
    }

    /***
     * 附加Map中的属性值到Model
     * @param model
     * @param propMap
     */
    public static void bindProperties(BaseModel model, Map<String, Object> propMap){
        try{// 获取类属性
            BeanInfo beanInfo = Introspector.getBeanInfo(model.getClass());
            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (propMap.containsKey(propertyName)){
                    Object value = propMap.get(propertyName);
                    Class type = descriptor.getWriteMethod().getParameterTypes()[0];
                    Object[] args = new Object[1];
                    String fieldType = type.getSimpleName();
                    // 类型不一致，需转型
                    if(!value.getClass().getTypeName().equals(fieldType)){
                        if(value instanceof String){
                            // String to Date
                            if(fieldType.equalsIgnoreCase(Date.class.getSimpleName())){
                                args[0] = D.fuzzyConvert((String)value);
                            }
                            // Map中的String型转换为其他型
                            else if(fieldType.equalsIgnoreCase(Boolean.class.getSimpleName())){
                                args[0] = V.isTrue((String)value);
                            }
                            else if (fieldType.equalsIgnoreCase(Integer.class.getSimpleName()) || "int".equals(fieldType)) {
                                args[0] = Integer.parseInt((String)value);
                            }
                            else if (fieldType.equalsIgnoreCase(Long.class.getSimpleName())) {
                                args[0] = Long.parseLong((String)value);
                            }
                            else if (fieldType.equalsIgnoreCase(Double.class.getSimpleName())) {
                                args[0] = Double.parseDouble((String)value);
                            }
                            else if (fieldType.equalsIgnoreCase(Float.class.getSimpleName())) {
                                args[0] = Float.parseFloat((String)value);
                            }
                            else{
                                args[0] = value;
                                logger.warn("类型不一致，暂无法自动绑定，请手动转型一致后调用！字段类型="+fieldType);
                            }
                        }
                        else{
                            args[0] = value;
                            logger.warn("类型不一致，且Map中的value非String类型，暂无法自动绑定，请手动转型一致后调用！value="+value);
                        }
                    }
                    else{
                        args[0] = value;
                    }
                    descriptor.getWriteMethod().invoke(model, args);
                }
            }
        }
        catch (Exception e){
            logger.warn("复制Map属性到Model异常: " + e.getMessage(), e);
        }
    }

    /***
     * 附加属性值到Model (已废弃，请调用bindProperties)
     * @param model
     * @param propMap
     */
    @Deprecated
    public static void populate(BaseModel model, Map<String, Object> propMap){
        bindProperties(model, propMap);
    }

    /***
     * 将请求参数值绑定成Model
     * @param request
     */
    public static void buildModel(BaseModel model, HttpServletRequest request){
        Map<String, Object> propMap = BaseHelper.convertParams2Map(request);
        bindProperties(model, propMap);
    }

    /***
     * 获取对象的属性值
     * @param obj
     * @param field
     * @return
     */
    public static Object getProperty(Object obj, String field){
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        return wrapper.getPropertyValue(field);
    }

    /***
     * 设置属性值
     * @param model
     * @param field
     * @param value
     */
    public static void setProperty(Object model, String field, Object value) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(model);
        wrapper.setPropertyValue(field, value);
    }

    /***
     * Key-Model对象Map
     * @param allLists
     * @return
     */
    public static <T extends BaseModel> Map<Object, T> convert2KeyModelMap(List<T> allLists, String... fields){
        if(allLists == null || allLists.isEmpty()){
            return null;
        }
        Map<Object, T> allListMap = new LinkedHashMap<>(allLists.size());
        // 转换为map
        if(V.isEmpty(fields)){
            for(T model : allLists){
                Object key = model.getPk();
                if(key != null){
                    allListMap.put(key, model);
                }
            }
        }
        else{
            try{
                for(T model : allLists){
                    List list = new ArrayList();
                    for(String fld : fields){
                        list.add(getProperty(model, fld));
                    }
                    allListMap.put(S.join(list), model);
                }
            }
            catch(Exception e){
                logger.warn("转换key-model异常", e);
            }
        }
        return allListMap;
    }

    /***
     * 将List<Model>转换为ID-List<Model>的对象Map
     * @param allLists
     * @return
     */
    public static <T extends BaseModel> Map<Object, List<T>> convert2KeyModelListMap(List<T> allLists, String field){
        if(V.isEmpty(allLists) || V.isEmpty(field)){
            return null;
        }

        Map<Object, List<T>> allListMap = new LinkedHashMap<>();
        try{
            for(BaseModel model : allLists){
                Object key = getProperty(model, field);
                if(key != null){
                    List list = allListMap.get(key);
                    if(list == null){
                        list = new ArrayList<>();
                        allListMap.put(key, list);
                    }
                    list.add(model);
                }
            }
        }
        catch(Exception e){
            logger.warn("转换key-model异常", e);
        }

        return allListMap;
    }

    /***
     * 构建上下级关联的树形结构的model
     * @param allModels
     * @param <T>
     * @return
     */
    public static <T extends BaseTreeModel> List<T> buildTreeModels(List<T> allModels){
        if(V.isEmpty(allModels)){
            return null;
        }
        // 提取所有的top level对象
        List<T> topLevelModels = new ArrayList();
        for(T model : allModels){
            if(model.isTopLevel()){
                topLevelModels.add(model);
            }
        }
        if(V.isEmpty(topLevelModels)){
            return topLevelModels;
        }
        // 提取向下一层的对象
        buildDeeperLevelTreeModel(topLevelModels, allModels);

        return topLevelModels;
    }

    /***
     * 构建下一层级树形结构
     * @param parentModels
     * @param allModels
     * @param <T>
     */
    private static <T extends BaseTreeModel> void buildDeeperLevelTreeModel(List<T> parentModels, List<T> allModels){
        List<T> deeperLevelModels = new ArrayList();
        Map<Object, T> parentLevelModelMap = convert2KeyModelMap(parentModels);
        for(T model : allModels){
            if(parentLevelModelMap.keySet().contains(model.getParentId()) && !model.getParentId().equals(model.getId())){
                deeperLevelModels.add(model);
            }
        }
        if(V.isEmpty(deeperLevelModels)){
            return;
        }
        for(T model : deeperLevelModels){
            T parentModel = parentLevelModelMap.get(model.getParentId());
            if(parentModel!=null){
                List children = parentModel.getChildren();
                if(children == null){
                    children = new ArrayList();
                    parentModel.setChildren(children);
                }
                children.add(model);
            }
        }
        // 递归进入下一层级
        buildDeeperLevelTreeModel(deeperLevelModels, allModels);
    }

    /***
     * 提取两个model的差异值
     * @param oldModel
     * @param newModel
     * @return
     */
    public static String extractDiff(BaseModel oldModel, BaseModel newModel){
        return extractDiff(oldModel, newModel, null);
    }

    /***
     * 提取两个model的差异值，只对比指定字段
     * @param oldModel
     * @param newModel
     * @return
     */
    public static String extractDiff(BaseModel oldModel, BaseModel newModel, Set<String> fields){
        if(newModel == null || oldModel == null){
            logger.warn("调用错误，Model不能为空！");
            return null;
        }
        Map<String, Object> oldMap = oldModel.toMap();
        Map<String, Object> newMap = newModel.toMap();
        Map<String, Object> result = new HashMap<String, Object>(oldMap.size()+newMap.size());
        for(Map.Entry<String, Object> entry : oldMap.entrySet()){
            if(IGNORE_FIELDS.contains(entry.getKey())){
                continue;
            }
            String oldValue = entry.getValue()!=null ? String.valueOf(entry.getValue()) : "";
            Object newValueObj = newMap.get(entry.getKey());
            String newValue = newValueObj!=null? String.valueOf(newValueObj) : "";
            // 设置变更的值
            boolean checkThisField = fields == null || fields.contains(entry.getKey());
            if(checkThisField && !oldValue.equals(newValue)){
                result.put(entry.getKey(), S.join(oldValue, CHANGE_FLAG, newValue));
            }
            // 从新的map中移除该key
            if(newValueObj!=null){
                newMap.remove(entry.getKey());
            }
        }
        if(!newMap.isEmpty()){
            for(Map.Entry<String, Object> entry : newMap.entrySet()){
                if(IGNORE_FIELDS.contains(entry.getKey())){
                    continue;
                }
                Object newValueObj = entry.getValue();
                String newValue = newValueObj!=null? String.valueOf(newValueObj) : "";
                // 设置变更的值
                if(fields==null || fields.contains(entry.getKey())){
                    result.put(entry.getKey(), S.join("", CHANGE_FLAG, newValue));
                }
            }
        }
        oldMap = null;
        newMap = null;
        // 转换结果为String
        return JSON.toJSONString(result);
    }

    /***
     * 从对象列表中提取某个字段值，包装成List返回
     * @param dataList
     * @param field
     * @return
     */
    public static List extractField(List dataList, String field){
        if(V.isEmpty(dataList)){
            return null;
        }
        List fieldValueList = new ArrayList();
        try{
            for(Object obj : dataList){
                Object value = getProperty(obj, field);
                fieldValueList.add(value);
            }
        }
        catch (Exception e){
            logger.warn("提取属性值异常, field="+field, e);
        }
        return fieldValueList;
    }

    /***
     * 绑定一对一关联
     * @param leftModelList 左侧对象
     * @param leftModelBindField 属性字段
     * @param rightModelList 右侧对象
     * @param left2rightMapping 左侧的key->右侧的key
     */
    public static void bindOne2One(List<? extends BaseModel> leftModelList, String leftModelBindField, List<? extends BaseModel> rightModelList, String left2rightMapping) {
        if(V.isEmpty(leftModelList) || V.isEmpty(rightModelList)){
            return;
        }
        if(V.isEmpty(left2rightMapping) || !left2rightMapping.contains(REF_FLAG)){
            logger.warn("调用错误，关联对象映射设置有误！合理的写法为: customerId=id");
            return;
        }
        String[] left2rightFields = left2rightMapping.split(REF_FLAG);
        String leftModelField = left2rightFields[0], rightModelField = left2rightFields[1];
        // 组装
        Map<Object, List<BaseModel>> rightModelId2LeftModelListMap = new HashMap<Object, List<BaseModel>>(rightModelList.size());
        try{
            // 遍历左侧对象列表，生成右侧对象key-左侧model列表的map
            for(BaseModel leftModel : leftModelList){
                Object key = getProperty(leftModel, leftModelField);
                List<BaseModel> list = rightModelId2LeftModelListMap.get(key);
                if(list == null){
                    list = new ArrayList<BaseModel>();
                    rightModelId2LeftModelListMap.put(key, list);
                }
                list.add(leftModel);
            }
            // 遍历右侧model列表，逐一设置左侧对象的关联对象
            for(BaseModel rightModel : rightModelList){
                Object key = getProperty(rightModel, rightModelField);
                List<BaseModel> list = rightModelId2LeftModelListMap.get(key);
                if(V.notEmpty(list)){
                    for(BaseModel leftModel : list){
                        setProperty(leftModel, leftModelBindField, rightModel);
                    }
                }
            }
            rightModelId2LeftModelListMap = null;
        }
        catch(Exception e){
            logger.error("绑定对象属性异常", e);
        }
    }

    /***
     * 单个model对象绑定一对多关联
     * @param model 一 model
     * @param manyModelList 多 model列表
     * @param left2rightMapping 关联对象属性名（映射到主model的主键）
     * @param oneModelField 绑定到主model的哪个属性
     */
    public static void bindOne2Many(BaseModel model, String oneModelField, List<? extends BaseModel> manyModelList, String left2rightMapping) {
        // 有对象为空  则无需绑定
        if(model == null || V.isEmpty(manyModelList)){
            return;
        }
        List<BaseModel> modelList = new ArrayList<BaseModel>();
        modelList.add(model);
        bindOne2Many(modelList, oneModelField, manyModelList, left2rightMapping);
    }

    /***
     * 多个model对象绑定一对多关联
     * @param oneModelList 一 model列表
     * @param manyModelList 多 model列表
     * @param oneModelField 绑定到主model的哪个属性
     * @param left2rightMapping 关联对象属性名（映射到主model的主键）
     */
    public static void bindOne2Many(List<? extends BaseModel> oneModelList, String oneModelField, List<? extends BaseModel> manyModelList, String left2rightMapping) {
        // 有对象为空  则无需绑定
        if(V.isEmpty(oneModelList) || V.isEmpty(manyModelList)){
            return;
        }
        if(V.isEmpty(left2rightMapping) || !left2rightMapping.contains(REF_FLAG)){
            logger.warn("调用错误，关联对象映射设置有误！合理的写法为: customerId=id");
            return;
        }
        String[] left2rightFields = left2rightMapping.split(REF_FLAG);
        String leftModelField = left2rightFields[0], rightModelField = left2rightFields[1];
        if(V.notEmpty(manyModelList)){
            Map<Object, BaseModel> oneModelMap = new HashMap<>(oneModelList.size());
            Map<Object, List> manyModelMap = new HashMap<>(manyModelList.size());
            try{
                for(BaseModel model : oneModelList){
                    Object key = getProperty(model, leftModelField);
                    oneModelMap.put(key, model);
                }
                for(BaseModel obj : manyModelList){
                    Object key = getProperty(obj, rightModelField);
                    List manyList = manyModelMap.get(key);
                    if(manyList == null){
                        manyList = new ArrayList();
                        manyModelMap.put(key, manyList);
                    }
                    manyList.add(obj);
                }
                for(Map.Entry entry : manyModelMap.entrySet()){
                    BaseModel oneModel = oneModelMap.get(entry.getKey());
                    if(oneModel != null){
                        setProperty(oneModel, oneModelField, entry.getValue());
                    }
                }
            }
            catch(Exception e){
                logger.error("绑定对象属性异常", e);
            }
            oneModelMap = null;
            manyModelMap = null;
        }
    }


    /***
     * 构造左右字段映射
     * @param left
     * @param right
     * @return
     */
    public static String buildL2RMapping(String left, String right){
       return left + REF_FLAG + right;
    }

    /***
     * 转换key-value对的List<Map>为Map
     * @param mapList
     * @return
     */
    public static List<Map<String, Object>> convert2KVMapList(List<Map<String, Object>> mapList, String valueField, String labelField) {
        if(mapList != null) {
            List<Map<String, Object>> list = new ArrayList<>(mapList.size());
            for(Map<String, Object> kvmap : mapList){
                Map<String, Object> map = new HashMap(2);
                map.put(BaseCons.KEY, kvmap.get(labelField));
                map.put(BaseCons.VALUE, kvmap.get(valueField));
                list.add(map);
            }
            return list;
        }
        return null;
    }

}
