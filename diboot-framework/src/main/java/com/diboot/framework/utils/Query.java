package com.diboot.framework.utils;

import com.diboot.framework.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/***
 * Dibo 常用查询构建，用于辅助创建criteria map
 * @author Mazc@dibo.ltd
 * @version 2016年12月27日
 * Copyright @ www.dibo.ltd
 */
public class Query {
    private static Logger logger = LoggerFactory.getLogger(Query.class);

    /***
     * IN条件中最大的允许数量
     */
    private static final int MAX_IN_SIZE = 500;
    /***
     * NOT IN条件中最大的允许数量
     */
    private static final int MAX_NOT_IN_SIZE = 100;

    /***
     * 构造附加查询条件: in, like, >=, <= ...
     */
    public static enum C{
        _IN, // in
        _NIN, // not in
        _GT, // >
        _GTE, // >=
        _LT, // <
        _LTE, // <=
        _NE, // !=
        _NN, // not null
        _N, // null
        _LIKE, // like '%%'
        _LLIKE, // like '%XX'
        _RLIKE, // like 'XX%'

        ORDERBY_ // order by
    }

    /**
     * 自定义查询属性
     */
    protected Map<String, Object> criteria;

    public Query(){
    }

    public Query(String field, Object value) {
        if (criteria == null){
            criteria = new HashMap<>();
        }
        criteria.put(field, value);
    }

    /***
     * 添加条件: 相等
     * @param field
     * @param value
     * @return
     */
    public Query add(String field, Object value) {
        if (criteria == null){
            criteria = new HashMap<>(8);
        }
        criteria.put(field, value);
        return this;
    }

    /***
     * 添加 IN 条件
     * @param field
     * @param values
     * @return
     */
    public Query addIn(String field, Collection values) {
        add(field+C._IN.name(), values);
        if(values != null && values.size() > MAX_IN_SIZE){
            logger.warn("IN条件中元素数量超过建议限值"+MAX_IN_SIZE+", 请优化！ field="+field+", size="+ values.size());
        }
        return this;
    }

    /***
     * 添加 IN 条件
     * @param field
     * @param values
     * @return
     */
    public Query addNotIn(String field, Collection values) {
        add(field+C._NIN.name(), values);
        if(values != null && values.size() > MAX_NOT_IN_SIZE){
            logger.warn("NOT IN条件中元素数量超过"+MAX_NOT_IN_SIZE+", 请优化！ field="+field+", size="+ values.size());
        }
        return this;
    }

    /***
     * 添加 LIKE 条件
     * @param field
     * @param value
     * @return
     */
    public Query addLike(String field, Object value) {
        add(field+C._LIKE.name(), value);
        return this;
    }

    /***
     * 添加 LIKE 条件
     * @param field
     * @param value
     * @return
     */
    public Query addLeftLike(String field, Object value) {
        add(field+C._LLIKE.name(), value);
        return this;
    }

    /***
     * 添加 LIKE 条件
     * @param field
     * @param value
     * @return
     */
    public Query addRightLike(String field, Object value) {
        add(field+C._RLIKE.name(), value);
        return this;
    }

    /***
     * 添加 > 条件
     * @param field
     * @param value
     * @return
     */
    public Query addGT(String field, Object value) {
        add(field+C._GT.name(), value);
        return this;
    }

    /***
     * 添加 >= 条件
     * @param field
     * @param value
     * @return
     */
    public Query addGTE(String field, Object value) {
        add(field+C._GTE.name(), value);
        return this;
    }

    /***
     * 添加 < 条件
     * @param field
     * @param value
     * @return
     */
    public Query addLT(String field, Object value) {
        add(field+C._LT.name(), value);
        return this;
    }

    /***
     * 添加 <= 条件
     * @param field
     * @param value
     * @return
     */
    public Query addLTE(String field, Object value) {
        add(field+C._LTE.name(), value);
        return this;
    }

    /***
     * 添加 != 条件
     * @param field
     * @param value
     * @return
     */
    public Query addNotEquals(String field, Object value) {
        add(field+C._NE.name(), value);
        return this;
    }

    /***
     * 添加 is null
     * @param field
     * @return
     */
    public Query addNull(String field) {
        add(field+C._N.name(), null);
        return this;
    }

    /***
     * 添加 is not null
     * @param field
     * @return
     */
    public Query addNotNull(String field) {
        add(field+C._NN.name(), null);
        return this;
    }

    /***
     * 根据指定字段升序排序
     * @param field
     * @return
     */
    public Query orderByAsc(String field){
        add(C.ORDERBY_+field, "ASC");
        return this;
    }

    /***
     * 根据指定字段降序排序
     * @param field
     * @return
     */
    public Query orderByDesc(String field){
        add(C.ORDERBY_+field, "DESC");
        return this;
    }

    /***
     * 限定结果数
     * @param limitCount
     * @return
     */
    public Query limit(int limitCount){
        add(BaseServiceImpl.COUNT, limitCount);
        return this;
    }

    /***
     * 删除条件
     * @param field
     * @return
     */
    public Query remove(String field) {
        if (criteria != null){
            criteria.remove(field);
        }
        return this;
    }

    /***
     * 清空条件
     * @return
     */
    public Query clear(){
        if (criteria != null){
            criteria.clear();
        }
        return this;
    }

    /***
     * 转换为Map
     * @return
     */
    public  Map<String, Object> toMap(){
        return criteria;
    }

    /***
     * 构建，转换为map
     * @return
     */
    public  Map<String, Object> build(){
        return toMap();
    }

}
