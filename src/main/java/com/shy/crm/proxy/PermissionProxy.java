package com.shy.crm.proxy;

import com.shy.crm.annotations.RequirePermission;
import com.shy.crm.exceptions.AuthFailedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Aop加注解实现权限认证
 *
 * @author suhongyv
 */
@Component
@Aspect

public class PermissionProxy {
    @Resource
    private HttpSession session;
    @Around(value="@annotation(com.shy.crm.annotations.RequirePermission)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        List<String> permissions = (List<String>) session.getAttribute("permissions");
        if(permissions==null||permissions.size()==0){
            throw new AuthFailedException();
        }
        Object result=null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        RequirePermission requirePermission = methodSignature.getMethod().getDeclaredAnnotation(RequirePermission.class);
        if(!(permissions.contains(requirePermission.code()))){
            throw new AuthFailedException();
        }
        result=joinPoint.proceed();
        return result;
    }
}
