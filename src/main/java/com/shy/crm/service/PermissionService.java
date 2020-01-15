package com.shy.crm.service;

import com.shy.base.BaseService;
import com.shy.crm.dao.PermissionMapper;
import com.shy.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService extends BaseService<Permission,Integer> {
    @Resource
    private PermissionMapper permissionMapper;
    public List<String> queryUserHasRolesHasPermissions(int userId) {
        return permissionMapper. queryUserHasRolesHasPermissions(userId);
    }
}
