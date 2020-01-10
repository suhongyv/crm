package com.shy.crm.interceptors;

import com.shy.crm.exceptions.NoLoginException;
import com.shy.crm.service.UserService;
import com.shy.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 非法访问拦截
 * @author suhongyv
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取用户id
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //参数判断,如果用户id为空或根据用户id查询的用户为空,则制造异常
        if(userId==0 || userService.selectByPrimaryKey(userId)==null){
            //制造未登录异常
           throw new NoLoginException();
        }
        return true;
    }
}
