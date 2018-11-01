package com.diboot.framework.plugin;

import com.diboot.framework.model.BaseMenu;

import java.util.List;

/**
 * 插件管理器
 * @author Mazc@dibo.ltd
 * @version 2017/12/11
 * Copyright © www.dibo.ltd
 */
public interface PluginManager {
    /***
     * 插件菜单
     * @return
     */
    List<BaseMenu> getPluginMenus();

}
