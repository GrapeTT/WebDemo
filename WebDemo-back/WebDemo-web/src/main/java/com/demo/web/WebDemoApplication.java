package com.demo.web;

import org.mybatis.spring.annotation.MapperScan;
import cn.hutool.log.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * SpringBoot 启动类
 */
@SpringBootApplication(scanBasePackages = {"com.demo", "cn.hutool.extra.spring"})
@PropertySources(
        value = {
                @PropertySource(value = {"classpath:config/application.properties"}, encoding = "utf-8"),
                @PropertySource(value = {"classpath:config/important.properties"}, encoding = "utf-8")
        }
)
@ImportResource(locations = {
//        "classpath*:spring/spring-redis.xml",
//        "classpath*:spring/spring-hbase.xml",
        "classpath*:spring/spring-config-email.xml"
})
@MapperScan("com.demo.dao.mapper")
@EnableScheduling//使定时任务生效
public class WebDemoApplication extends SpringBootServletInitializer {
    private static final Log LOG = Log.get();

    public static void main(String[] args) {
        SpringApplication.run(WebDemoApplication.class, args);
        LOG.info("Server start up！");
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebDemoApplication.class);
    }
    
    /**
     * 解决跨域问题
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }
    
    /**
     * 解决跨域问题
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
