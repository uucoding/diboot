package com.diboot.framework.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Spring上下文帮助类
 * @author Mazc@dibo.ltd
 * @version 2017/8/12
 * Copyright @ www.dibo.ltd
 */
@Component
@Lazy(false)
public class ContextHelper implements ApplicationContextAware {
    /***
     * ApplicationContext上下文
     */
    private static ApplicationContext APPLICATION_CONTEXT = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(APPLICATION_CONTEXT == null){
            APPLICATION_CONTEXT = applicationContext;
        }
    }

    /***
     * 获取ApplicationContext上下文
     */
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    /***
     * 获取Bean实例
     * @param beanId
     * @return
     */
    public static Object getBean(String beanId){
        ApplicationContext wac = getApplicationContext();
        if(wac != null){
            return wac.getBean(beanId);
        }
        return null;
    }

    /***
     * 获取bean实例
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> getBeans(Class<T> type){
        // 获取所有的定时任务实现类
        List<T> beanList = new ArrayList<>();
        ApplicationContext wac = getApplicationContext();
        Map<String, T> map = wac.getBeansOfType(type);
        if(V.notEmpty(map)){
            for(Map.Entry<String, T> entry : map.entrySet()){
                beanList.add(entry.getValue());
            }
        }
        return beanList;
    }

    /***
     * 根据注解获取beans
     * @param annotationType
     * @return
     */
    public static List<Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType){
        Map<String, Object> resultMap = getApplicationContext().getBeansWithAnnotation(annotationType);
        if(V.notEmpty(resultMap)){
            List<Object> beanList = new ArrayList<>();
            for(Map.Entry<String, Object> entry : resultMap.entrySet()){
                beanList.add(entry.getValue());
            }
            return beanList;
        }
        return null;
    }
}