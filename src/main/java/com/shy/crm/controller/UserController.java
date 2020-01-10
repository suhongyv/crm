package com.shy.crm.controller;
import com.shy.base.BaseController;
import com.shy.crm.model.ResultInfo;
import com.shy.crm.model.UserModel;
import com.shy.crm.service.UserService;
import com.shy.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author suhongyv
 */
@Controller
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @param userName  用户名
     * @param userPwd   用户密码
     * @return
     */
    @RequestMapping("user/login")
    @ResponseBody
    public ResultInfo login(String userName,String userPwd){
        UserModel userModel = userService.userLogin(userName, userPwd);
       return success("用户登录成功",userModel);
    }

    /**
     * 修改密码
     * @param request
     * @param oldPassword  老密码
     * @param newPassword  新密码
     * @param confirmPassword   新密码确认
     * @return
     */
    @ResponseBody
    @RequestMapping("user/updatePassword")
    public ResultInfo updatePassword(HttpServletRequest request,String oldPassword,String newPassword,String confirmPassword){
        userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request),oldPassword,newPassword,confirmPassword);
        return success("密码更新成功");
    }
}
