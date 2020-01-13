package com.shy.crm.dao;

import com.shy.base.BaseMapper;
import com.shy.crm.vo.Role;

import java.util.List;
import java.util.Map;


/**
 * @author suhongyv
 */
public interface RoleMapper extends BaseMapper<Role,Integer> {

    List<Map<String, Object>>  queryAllRoles();

    Role  queryRoleByRoleName(String roleName);
}