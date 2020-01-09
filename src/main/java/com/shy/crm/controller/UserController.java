package com.shy.crm.controller;
import com.shy.base.BaseController;
import com.shy.crm.exceptions.ParamsException;
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
        ResultInfo resultInfo=new ResultInfo();
        try {
            UserModel userModel = userService.userLogin(userName, userPwd);
            resultInfo.setResult(userModel);
        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setCode(e.getCode());
            resultInfo.setMsg(e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(500);
            resultInfo.setMsg("faild");
        }
        return resultInfo;
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
        ResultInfo resultInfo = new ResultInfo();
        try {
            userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request),oldPassword,newPassword,confirmPassword);
        } catch (ParamsException e) {
            resultInfo.setMsg(e.getMsg());
            resultInfo.setCode(e.getCode());
        }catch (Exception e) {
            resultInfo.setCode(500);
            resultInfo.setMsg("Faild");
        }
        return resultInfo;
    }
}
