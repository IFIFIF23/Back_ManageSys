package com.lantu.config;

import com.lantu.interceptor.JwtValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private JwtValidateInterceptor jwtValidateInterceptor;  //将拦截器注入

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration Registration = registry.addInterceptor(jwtValidateInterceptor);
        Registration.addPathPatterns("/**")     //拦截所有
                .excludePathPatterns(           //除去了登录和退出，防止死循环
                        "/user/login",
                        "/user/info",
                        "/user/logout"
                );
    }
}
