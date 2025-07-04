package com.dadam.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAuthConfig implements  WebMvcConfigurer{
	 @Autowired
	    private AuthInterceptor authInterceptor;
	 
	 @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(authInterceptor)
	                .addPathPatterns("/erp/**")        // 검사할 경로
	                .excludePathPatterns("/erp/login", "/css/**", "/js/**", "/images/**"); // 로그인/정적 자원 제외
	    }
} 
