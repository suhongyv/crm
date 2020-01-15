package com.shy.crm.dao;

import com.shy.base.BaseMapper;
import com.shy.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    int  countPermissionByRoleId(Integer roleId);

    int  deletePermissionByRoleId(Integer roleId);

    List<String>  queryUserHasRolesHasPermissions(int userId);

    List<Integer>  queryRoleHasAllModulesByRoleId(Integer roleId);

    int  countPermissionByModuleId(Integer id);

    int  deletePermissionByModuleId(Integer id);
}