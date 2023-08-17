package com.lantu.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


//异步跨域配置文件
@Configuration
public class MyCorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration configuration = new CorsConfiguration();   //配置对象
        configuration.addAllowedOrigin("http://localhost:8888");//允许谁来异步访问
        configuration.setAllowCredentials(true);//是否发送cookie
        configuration.addAllowedMethod("*");//允许的请求方式
        configuration.addAllowedHeader("*");//允许的头信息

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",configuration);

        return  new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
