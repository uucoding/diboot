package com.diboot.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Spring Model自动绑定时的日期格式转换
 * @author Mazc@dibo.ltd
 * @version 2017/8/25
 * Copyright @ www.dibo.ltd
 */
public class DateConverter4Spring implements Converter<String, Date> {
    private static final Logger logger = LoggerFactory.getLogger(DateConverter4Spring.class);

    @Override
    public Date convert(String dateString) {
        return D.fuzzyConvert(dateString);
    }
}