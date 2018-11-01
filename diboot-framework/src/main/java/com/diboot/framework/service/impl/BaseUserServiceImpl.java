package com.diboot.framework.service.impl;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.Metadata;
import com.diboot.framework.service.BaseUserService;
import com.diboot.framework.service.MetadataService;
import com.diboot.framework.service.mapper.BaseMapper;
import com.diboot.framework.service.mapper.BaseUserMapper;
import com.diboot.framework.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mazc@dibo.ltd
 * @version 2017/4/17
 * Copyright @ www.dibo.ltd
 */
@Service("baseUserService")
public class BaseUserServiceImpl extends BaseServiceImpl implements BaseUserService {
    private static final Logger logger = LoggerFactory.getLogger(BaseUserServiceImpl.class);

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Autowired
    private MetadataService metadataService;

    @Override
    protected BaseMapper getMapper(){
        return baseUserMapper;
    }

    protected BaseUserMapper getUserMapper(){return baseUserMapper;}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createModel(BaseModel model){
        BaseUser user = (BaseUser) model;
        // 密码加密
        Encryptor.encryptPassword(user);
        boolean success = getMapper().create(user) > 0;
        if(success && V.notEmpty(user.getRoleList())){
            // 添加角色
            getUserMapper().createUserRoles(user.getId(), user.getRoleList());
        }

        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateModel(BaseModel model, String...  fields){
        // 是否更新密码
        BaseUser user = (BaseUser) model;
        if(V.notEmpty(user.getPassword()) && user.getPassword().length() < 32){
            Encryptor.encryptPassword(user);
        }
        boolean success = super.updateModel(user, fields);
        if(success){
            // 是否需要更新角色
            boolean validateRole = V.isEmpty(fields); //全字段更新
            if(!validateRole){
                //或更新了roles
                Map<String, Boolean> updateFields = super.convertArray2Map(model, fields);
                validateRole = V.notEmpty(updateFields) && updateFields.containsKey(BaseUser.F.roles);
            }
            // 更新角色
            if(validateRole){
                List<String> roles = getUserMapper().getUserRoles(user.getId());
                if(user.getRoleList() == null || !V.equal(user.getRoleList(), roles)){
                    // 重新初始化角色
                    List<Long> userIdList = new ArrayList<>();
                    userIdList.add(user.getId());
                    getUserMapper().deleteUserRoles(userIdList);
                    if (user.getRoleList() != null){
                        getUserMapper().createUserRoles(user.getId(), user.getRoleList());
                    }
                }
            }
        }
        return success;
    }

    @Override
    public <T extends BaseUser>T getUserByUsername(String username) {
        BaseUser user = getUserMapper().getUserByUsername(username);
        if(user != null){
            user.setRoleList(getUserMapper().getUserRoles(user.getId()));
        }
        return (T)user;
    }

    @Override
    public <T extends BaseUser>T getUserByWechat(String wechat) {
        BaseUser user = getUserMapper().getUserByWechat(wechat);
        if(user != null){
            user.setRoleList(getUserMapper().getUserRoles(user.getId()));
        }
        return (T)user;
    }

    @Override
    public <T extends BaseUser> T getUserByField(String field, String fieldValue) {
        Query query = new Query();
        query.add(field, fieldValue);
        query.add(BaseUser.F.active, true);
        query.limit(2);
        List<T> userList = getUserMapper().getList(query.toMap());
        T user = null;
        if(V.notEmpty(userList)){
            user = userList.get(0);
            if(userList.size() > 1){
                logger.warn("根据指定字段条件 {"+field+":"+fieldValue+"} 查询到至少2个符合条件的User，请检查！");
            }
            user.setRoleList(getUserMapper().getUserRoles(user.getId()));
        }
        return user;
    }

    @Override
    public <T extends BaseUser>T getUserWithRoles(Long id) {
        BaseUser user = getMapper().get(id);
        if(user != null){
            user.setRoleList(getUserMapper().getUserRoles(user.getId()));
        }
        Map<String, String> allMap = getRoleKvMap();
        List<String> roleNames = new ArrayList<>();
        bindRoleNames(user, roleNames, allMap);
        return (T)user;
    }

    @Override
    public <T extends BaseUser> List<T> getUserListWithRoles(Map<String, Object> criteria, int... page) {
        List<BaseUser> userList = getModelList(criteria, page);
        if(V.notEmpty(userList)){
            Map<String, String> allMap = getRoleKvMap();
            List<String> roleNames = new ArrayList<>();
            for(BaseUser user : userList){
                bindRoleNames(user, roleNames, allMap);
            }
        }
        return (List<T>)userList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserPwd(Long id, String oldPassword, String newPassword) {
        // 清空缓存
        BaseUser user = getUserMapper().get(id);
        if(user != null){
            oldPassword = Encryptor.encryptPassword(oldPassword, user.getSalt());
            newPassword = Encryptor.encryptPassword(newPassword, user.getSalt());
            return getUserMapper().updateUserPwd(id, oldPassword, newPassword) > 0;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends BaseModel> boolean batchCreateModels(List<T> modelList){
        if(modelList == null){
            return false;
        }
        for(BaseModel model : modelList){
            BaseUser user = (BaseUser)model;
            Encryptor.encryptPassword(user);
        }
        boolean success = super.batchCreateModels(modelList);
        if(success){
            List<String> usernameList = BeanUtils.extractField(modelList, BaseUser.F.username);
            List<Map<String, Object>> userIdRoleList = getUserMapper().getUserIdRoles(usernameList);
            if(V.notEmpty(userIdRoleList)){
                List<Map<String, Object>> userRoleList = new ArrayList<>();
                for(Map<String, Object> map : userIdRoleList){
                    Long userId = Long.parseLong(String.valueOf(map.get("id")));
                    String roles = (String)map.get("roles");
                    for(String role : roles.split(",")){
                        Map<String, Object> userRole = new HashMap<>(4);
                        userRole.put("userId", userId);
                        userRole.put("role", role);
                        // 添加到list
                        userRoleList.add(userRole);
                    }
                }
                if(V.notEmpty(userRoleList)){
                    try{
                        baseUserMapper.batchCreateUserRoles(userRoleList);
                    }
                    catch (Exception e){
                        logger.warn("同步user_role异常", e);
                    }
                }
            }

        }
        return success;
    }

    //TODO 暂时缓存角色配置
    Map<String, String> roleKVMap = null;
    private Map<String, String> getRoleKvMap(){
        if(roleKVMap != null){
            return roleKVMap;
        }
        roleKVMap = new HashMap<>();
        List<Map<String, Object>> roleMapList = metadataService.getKeyValuePairListByType(Metadata.TYPE.ROLE.name());
        if(V.notEmpty(roleMapList)){
            for(Map<String, Object> map : roleMapList){
                roleKVMap.put((String)map.get("v"), (String)map.get("k"));
            }
        }
        return roleKVMap;
    }

    private void bindRoleNames(BaseUser user, List<String> roleNames, Map<String, String> kvMap){
        if(V.notEmpty(user.getRoleList())){
            roleNames.clear();
            for(String role : user.getRoleList()){
                String roleName = kvMap.get(role);
                if(roleName != null){
                    roleNames.add(roleName);
                }
            }
            user.setRoleNames(S.join(roleNames));
        }
    }
}
