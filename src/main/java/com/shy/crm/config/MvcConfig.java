package com.shy.crm.config;

import com.shy.crm.interceptors.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置类
 * @author suhongyv
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 把拦截器交给spring
     * @return
     */
    @Bean
    public NoLoginInterceptor noLoginInterceptor(){
        return new NoLoginInterceptor();
    }

    /**
     * 初始化拦截器,试拦截器生效
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(noLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/index","/static/**");
    }
}
