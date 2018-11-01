package com.diboot.components.plugin;

import com.diboot.framework.plugin.ModuleType;
import com.diboot.framework.plugin.PluggableModule;
import com.diboot.framework.model.BaseMenu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mazc@dibo.ltd
 * @version 2018/3/6
 * Copyright © www.dibo.ltd
 */
@Component
public class MsgPluginModule implements PluggableModule {

    @Override
    public ModuleType getType() {
        return ModuleType.FUNC;
    }

    @Override
    public String getTitle() {
        return "消息管理功能";
    }

    @Override
    public List<String> getDependencyJars() {
        List<String> jars = new ArrayList<>();
        jars.add("// httpclient文件相关依赖");
        jars.add("compile('org.apache.httpcomponents:httpclient:4.5.6')");
        jars.add("compile('org.apache.httpcomponents:httpmime:4.5')");
        return jars;
    }

    @Override
    public BaseMenu getMenu() {
        return null;
    }
}