package com.shy.crm.dao;

import com.shy.base.BaseMapper;
import com.shy.crm.vo.UserRole;

/**
 * @author suhongyv
 */
public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    int  countUserRoleByUserId(int userId);

    int  deleteUserRoleByUserId(int userId);
}