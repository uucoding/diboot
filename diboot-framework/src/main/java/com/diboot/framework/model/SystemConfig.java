package com.diboot.framework.model;

import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 系统配置 Model对象定义
 * @author Mazc@dibo.ltd
 * @version 2018-06-04
 * Copyright © www.dibo.ltd
 */
public class SystemConfig extends BaseModel{
    private static final long serialVersionUID = 1901L;

    /**
     * 构建查询条件所需参数定义
     */
    public static class F extends BaseModel.F{ public static final String
        relObjType = "relObjType",
        relObjId = "relObjId",
        category = "category",
        subcategory = "subcategory"
    ;}

    /** 关联对象 */
    @Length(max = 100, message = "关联对象长度超出了最大限制！")
    private String relObjType;

    /** 关联数据 */
    private long relObjId;

    /** 类别 */
    @NotNull(message = "类别不能为空！")
    @Length(max = 100, message = "类别长度超出了最大限制！")
    private String category;

    /** 分类 */
    @Length(max = 100, message = "子类别长度超出了最大限制！")
    private String subcategory;

    // 外键关联字段定义
    public String getRelObjType() {
        return relObjType;
    }
    public void setRelObjType(String relObjType) {
        this.relObjType = relObjType;
    }

    public long getRelObjId() {
        return relObjId;
    }
    public void setRelObjId(long relObjId) {
        this.relObjId = relObjId;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    /***
     * 获取配置项的全部参数名
     * @return
     */
    public String getConfigItemKeys(){
        LinkedHashMap map = getExtdataMap();
        if(V.notEmpty(map)){
            return S.join(new ArrayList<>(map.keySet()));
        }
        return null;
    }

    /***
     * 获取配置参数的值
     * @param key
     * @return
     */
    public Object getConfigItemValue(String key){
        return getFromJson(key);
    }

    @Override
    public String getModelName(){
        return "系统配置";
    }

}