package com.shy.crm.service;

import com.shy.base.BaseService;
import com.shy.crm.dao.UserMapper;
import com.shy.crm.model.UserModel;
import com.shy.crm.utils.AssertUtil;
import com.shy.crm.utils.Md5Util;
import com.shy.crm.utils.UserIDBase64;
import com.shy.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author suhongyv
 */
@Service
@SuppressWarnings("all")
public class UserService extends BaseService<User,Integer> {
    @Autowired
    private UserMapper userMapper;

    public UserModel userLogin(String userName,String userPwd){
        checkLoginParams(userName,userPwd);
        User user=userMapper.findUserByUserName(userName);
        AssertUtil.isTrue(null==user,"用户已注销或不存在");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))),"用户密码错误");
        return new UserModel(UserIDBase64.encoderUserID(user.getId()),user.getUserName(),user.getTrueName());
    }
    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不可为空!");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不可为空!");
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        User user = selectByPrimaryKey(userId);
        checkParams(userId,oldPassword,newPassword,confirmPassword,user);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"密码更新失败!!");
    }

    private void checkParams(Integer userId, String oldPassword, String newPassword, String confirmPassword,User user) {
        AssertUtil.isTrue(null==userId||user==null,"用户未登录或不存在");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"请输入旧密码!!!");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"请输入新密码!!!");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"请输入确认密码!!!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"确认密码与新密码不一致!!!");
        AssertUtil.isTrue(!(user.getUserPwd()).equals(Md5Util.encode(oldPassword)),"旧密码不正确!!!");
        AssertUtil.isTrue(oldPassword.equals(newPassword),"新旧密码不能相同!!!");
    }
}
