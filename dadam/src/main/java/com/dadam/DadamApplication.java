package com.dadam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//dadam
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.dadam.**.mapper")  
public class DadamApplication {

	public static void main(String[] args) {
		SpringApplication.run(DadamApplication.class, args);
	}

}
