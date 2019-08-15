package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * SpringBoot 启动类
 */
@ImportResource("classpath:spring/spring-config-all.xml")
@SpringBootApplication
public class WebDemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebDemoApplication.class, args);
    }
}
