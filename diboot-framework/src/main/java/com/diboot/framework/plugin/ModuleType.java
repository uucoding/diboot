package com.diboot.framework.plugin;

/**
 *  模块类型枚举定义
 * @author Mazc@dibo.ltd
 * @version 2018/3/3
 * Copyright © www.dibo.ltd
 */
public enum ModuleType {
    /***
     * Web功能模块，包含前后端完整功能的模块
     */
    FUNC("功能模块"),
    /***
     * 工具模块，一般用于开发过程中的辅助功能
     */
    TOOL("工具模块");

    private String label;
    ModuleType(String label){
        this.label = label;
    }

    /***
     * 获取模块的标签
     * @return
     */
    public static String getLabel(ModuleType moduleType){
        for(ModuleType type : ModuleType.values()){
            if(type.name().equals(moduleType.name())){
                return type.label;
            }
        }
        return null;
    }

}
