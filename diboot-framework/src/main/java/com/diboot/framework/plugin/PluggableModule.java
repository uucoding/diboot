package com.diboot.framework.plugin;

import com.diboot.framework.model.BaseMenu;

import java.util.List;

/**
 * 可插拔的模块接口
 * @author Mazc@dibo.ltd
 * @version 2017/10/23
 * Copyright @ www.dibo.ltd
 */
public interface PluggableModule {

    /***
     * 插件模块类型
     * @return
     */
    ModuleType getType();

    /***
     * 功能模块名称
     * @return
     */
    String getTitle();

    /**
     * 依赖的jar包
     * @return
     */
    List<String> getDependencyJars();

    /**
     * 获取插件菜单
     * @return
     */
    BaseMenu getMenu();

}
