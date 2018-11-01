package com.diboot.framework.service;

import com.diboot.framework.model.BaseUser;

import java.util.List;
import java.util.Map;

/**
 * 单位相关操作Service
 * @author Mazc@dibo.ltd
 * @version 2017/4/18
 * Copyright@www.dibo.ltd
 */
public interface BaseUserService extends BaseService {

    /***
     * 根据用户名获取用户信息（及角色）
     * @param username
     * @return
     */
    <T extends BaseUser>T getUserByUsername(String username);

    /***
     * 根据wechat获取用户信息（及角色）
     * @param wechat
     * @return
     */
    <T extends BaseUser>T getUserByWechat(String wechat);

    /***
     * 根据指定字段及值 获取匹配的用户信息（及角色）
     * @return
     */
    <T extends BaseUser>T getUserByField(String field, String fieldValue);

    /***
     * 获取用户及角色
     * @param id
     * @return
     */
    <T extends BaseUser>T getUserWithRoles(Long id);

    /***
     * 获取当前页的用户（附带角色）
     * @param criteria
     * @param page
     * @return
     */
    <T extends BaseUser> List<T> getUserListWithRoles(Map<String, Object> criteria, int... page);

    /***
     * 更新用户密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    boolean updateUserPwd(Long id, String oldPassword, String newPassword);

}
