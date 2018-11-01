package com.diboot.framework.service.mapper;

import com.diboot.framework.model.BaseUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Mazc@dibo.ltd
 * @version 2017/4/18
 * Copyright @ www.dibo.ltd
 */
@Component
public interface BaseUserMapper extends BaseMapper{

    int deleteUserRoles(@Param("userIdList") List<Long> userIdList);

    int createUserRoles(@Param("userId") Object userId, @Param("roles") List<String> roles);

    int batchCreateUserRoles(@Param("userRoleList") List<Map<String, Object>> userRoleList);

    List<String> getUserRoles(@Param("userId") Object userId);

    List<Map<String, Object>> getUserIdRoles(@Param("usernameList") List<String> usernameList);

    <T extends BaseUser>T getUserByUsername(String username);

    int updateUserPwd(@Param("id") Object id, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);

    <T extends BaseUser>T getUserByWechat(String wechat);

}