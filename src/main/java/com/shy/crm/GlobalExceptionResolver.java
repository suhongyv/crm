package com.shy.crm;

import com.alibaba.fastjson.JSON;
import com.shy.crm.exceptions.NoLoginException;
import com.shy.crm.exceptions.ParamsException;
import com.shy.crm.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 全局异常
 * @author suhongyv
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        /**
         * 首先判断异常类型
         *   如果异常类型为未登录异常  执行视图转发
         */
        ModelAndView modelAndView =new ModelAndView();
        if(e instanceof NoLoginException){
            NoLoginException noLoginException= (NoLoginException) e;
            modelAndView.setViewName("no_login");
            modelAndView.addObject("msg",noLoginException.getMsg());
            modelAndView.addObject("ctx",httpServletRequest.getContextPath());
            return modelAndView;

        }
        /**方法返回值类型判断:
         *    如果方法级别存在@ResponseBody 方法响应内容为json  否则视图
         *    handler 参数类型为HandlerMethod
         * 返回值
         *    视图:默认错误页面
         *
         *
         *
         *    json:错误的json信息
         */
        modelAndView.setViewName("error");
        modelAndView.addObject("code",400);
        modelAndView.addObject("msg","系统异常,请稍后重试!!!");
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod= (HandlerMethod) handler;
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            if(null==responseBody){
                /*
                * 方法返回视图
                * */
               if(e instanceof ParamsException){
                   ParamsException paramsException= (ParamsException) e;
                   modelAndView.addObject("msg",paramsException.getMsg());
                   modelAndView.addObject("code",paramsException.getCode());
               }
               return modelAndView;
            }else{
                /*
                * 方法返回json
                *
                * */
                ResultInfo resultInfo=new ResultInfo();
                resultInfo.setMsg("系统异常,请稍后重试!!!");
                resultInfo.setCode(300);
                if(e instanceof ParamsException){
                    ParamsException paramsException= (ParamsException) e;
                    resultInfo.setCode(paramsException.getCode());
                    resultInfo.setMsg(paramsException.getMsg());
                }
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter printWriter=null;
                try {
                    printWriter=httpServletResponse.getWriter();
                    printWriter.write(JSON.toJSONString(resultInfo));
                    printWriter.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }finally {
                    if(printWriter!=null){
                        printWriter.close();
                    }
                }
                return null;
            }
        }else{
            return modelAndView;
        }
    }
}
