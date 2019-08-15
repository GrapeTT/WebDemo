package com.demo.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author：涛
 * @Descripition：拦截配置
 * @Date：2018/3/23 18:33
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求，判断是否需要登录，排除静态资源请求
        registry.addInterceptor(LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/js/**", "/css/**", "/resources/**");
    }
    
    @Bean
    public LoginInterceptor LoginInterceptor() {
        return new LoginInterceptor();
    }
}
