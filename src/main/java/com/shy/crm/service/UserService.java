package com.shy.crm.service;

import com.shy.base.BaseService;
import com.shy.crm.dao.RoleMapper;
import com.shy.crm.dao.UserMapper;
import com.shy.crm.dao.UserRoleMapper;
import com.shy.crm.model.UserModel;
import com.shy.crm.utils.AssertUtil;
import com.shy.crm.utils.Md5Util;
import com.shy.crm.utils.PhoneUtil;
import com.shy.crm.utils.UserIDBase64;
import com.shy.crm.vo.User;
import com.shy.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author suhongyv
 */
@Service
@SuppressWarnings("all")
public class UserService extends BaseService<User,Integer> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    /**
     * 用户登录
     * @param userName
     * @param userPwd
     * @return
     */
    public UserModel userLogin(String userName,String userPwd){
        //参数校验,判断参数是否为空
        checkLoginParams(userName,userPwd);
        //根据用户名查询用户
        User user=userMapper.findUserByUserName(userName);
        //判断用户是否存在
        AssertUtil.isTrue(null==user,"用户已注销或不存在");
        //判断前台传的密码是否与数据库查的相同
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))),"用户密码错误");
        //返回UserModel对象
        return new UserModel(UserIDBase64.encoderUserID(user.getId()),user.getUserName(),user.getTrueName());
    }

    /**
     * 参数校验
     * @param userName
     * @param userPwd
     */
    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不可为空!");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不可为空!");
    }

    /**
     * 密码更新
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    //开启事务
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        //根据用户id查询用户
        User user = selectByPrimaryKey(userId);
        //参数校验
        checkParams(userId,oldPassword,newPassword,confirmPassword,user);
        //设置密码
        user.setUserPwd(Md5Util.encode(newPassword));
        //更新密码
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"密码更新失败!!");
    }

    /**
     * 参数校验
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @param user
     */
    private void checkParams(Integer userId, String oldPassword, String newPassword, String confirmPassword,User user) {
        AssertUtil.isTrue(null==userId||user==null,"用户未登录或不存在");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"请输入旧密码!!!");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"请输入新密码!!!");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"请输入确认密码!!!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"确认密码与新密码不一致!!!");
        AssertUtil.isTrue(!(user.getUserPwd()).equals(Md5Util.encode(oldPassword)),"旧密码不正确!!!");
        AssertUtil.isTrue(oldPassword.equals(newPassword),"新旧密码不能相同!!!");
    }

    /**
     * 用户添加
     * @param user
     */
    //开启事务
    @Transactional(propagation = Propagation.REQUIRED)
    public void  saveUser(User user){
        /**
         * 1.参数校验
         *     用户名  非空   唯一
         *     email  非空  格式合法
         *     手机号 非空  格式合法
         * 2.设置默认参数
         *      isValid 1
         *      createDate   uddateDate
         *      userPwd   123456->md5加密
         * 3.执行添加  判断结果
         */
        checkParams(user.getUserName(),user.getEmail(),user.getPhone());
        User temp = userMapper.findUserByUserName(user.getUserName());
        AssertUtil.isTrue(null!=temp&&(temp.getIsValid()==1),"该用户已存在!!!");
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(insertHasKey(user)<1,"添加失败!!!");
        int userId=user.getId();
        /**
         * 用户角色分配
         *    userId
         *    roleIds
         */
        relationUserRole(userId,user.getRoleIds());

    }

    private void relationUserRole(int userId, List<Integer> roleIds) {
        /**
         * 如何进行角色分配???
         *  如果用户原始角色存在  首先清空原始所有角色
         *  添加新的角色记录到用户角色表
         */
        int count=userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户角色分配失败");
        }
        if(roleIds!=null&&roleIds.size()>0){
            List<UserRole> userRoles=new ArrayList<>();
            roleIds.forEach(roleId->{
                UserRole userRole=new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoles.add(userRole);
            });
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoles)<userRoles.size(),"用户角色分配失败");
        }
    }

    private void checkParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空!!!");
        AssertUtil.isTrue(StringUtils.isBlank(email),"请输入邮箱地址!!!");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)),"请输入正确的手机号!!!");
    }

    /**
     * 用户数据更新
     * @param user
     */
    public void  updateUser(User user){
        /**
         * 1.参数校验
         *     id 非空  记录必须存在
         *     用户名  非空   唯一
         *     email  非空  格式合法
         *     手机号 非空  格式合法
         * 2.设置默认参数
         *        uddateDate
         * 3.执行更新  判断结果
         */
        AssertUtil.isTrue(user.getId()==null||selectByPrimaryKey(user.getId())==null,"待更新记录不存在!!!");
        checkParams(user.getUserName(),user.getEmail(),user.getPhone());
        User temp = userMapper.findUserByUserName(user.getUserName());
        if(null!=temp&&temp.getIsValid()==1){
            AssertUtil.isTrue(!(user.getId().equals(temp.getId())),"用户名已存在!!!");
        }
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"更新失败!!!");
        relationUserRole(user.getId(),user.getRoleIds());
    }

    /**
     * 用户删除
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void  deleteUser(Integer id){
        User user = userMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(id==null||user==null,"待删除记录不存在!!!");
        int count=userRoleMapper.countUserRoleByUserId(id);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(id)!=count,"删除失败!!!");
        }
        user.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"删除失败!!!");
    }
}
