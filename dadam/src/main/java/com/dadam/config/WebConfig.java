package com.dadam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/uploads/**")
    		    .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");

		registry.addResourceHandler("/contracts/**")
				.addResourceLocations("file:" + System.getProperty("user.dir") + "/contracts/");
		registry.addResourceHandler("/signatures/**")
		        .addResourceLocations("file:" + System.getProperty("user.dir") + "/signatures/");
		registry.addResourceHandler("/settlementEle/**")
        .addResourceLocations("file:" + System.getProperty("user.dir") + "/settlementEle/");
    }
} 