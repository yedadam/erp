package com.dadam.hr.emp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

@Configuration
public class HrMailConfig {

    @Value("${hr.mail.host:smtp.gmail.com}")
    private String host;

    @Value("${hr.mail.port:587}")
    private int port;

    @Value("${hr.mail.username:ricefirstcode@gmail.com}")
    private String username;

    @Value("${hr.mail.password:}")
    private String password;

    @Bean
    @Primary
    public JavaMailSender hrMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
} 