package com.diboot.framework.service.mapper;

import com.diboot.framework.model.BaseMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/***
 * RoleMenu-角色权限相关Mapper
 * @author Mazc@dibo.ltd
 */
@Component
public interface RoleMenuMapper extends BaseMapper{

	int deleteRoleMenus(String role);

	List<? extends BaseMenu> getAllMenus(@Param("c") Map criteria);

    int createMenu(BaseMenu menu);

    int updateMenu(BaseMenu menu);

    List getMenuListByRoleList(@Param("roleList") List<String> roleList, @Param("c") Map criteria);
}