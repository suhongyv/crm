package com.shy.crm.dao;

import com.shy.base.BaseMapper;
import com.shy.crm.vo.User;

/**
 * @author suhongyv
 */
public interface UserMapper extends BaseMapper<User,Integer> {


    User  findUserByUserName(String userName);
}