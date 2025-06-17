package com.dadam.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    // 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
// 인증 및 인가 설정
    @Bean
//첫번째 시큐리티 필터체인
    @Order(1)
    public SecurityFilterChain mainFilterChain(HttpSecurity http) throws Exception {
        http
            //main에서만 적용
        	.securityMatcher("/main/**") 
            .authorizeHttpRequests(auth -> auth
                // 정적 리소스와 로그인 페이지는 모든 사용자에게 허용
                .requestMatchers( "/", 
                        "/main/**",
                        "/css/**", 
                        "/js/**", 
                        "/images/**", 
                        "/webjars/**").permitAll()
                
                // 나머지 모든 요청은 인증 필요
                .anyRequest().authenticated()
            )
            
            .formLogin(form -> form
                // 커스텀 로그인 페이지
                .loginPage("/main/login")
                //원래는 username으로만 security가 인식하는데 만약 바꾸고 싶다면
                //아래처럼 변경하여야 한다. 
                .usernameParameter("userId") 
                .passwordParameter("pwd")
                .permitAll()
                // 로그인 성공시 리다이렉트 URL
                .defaultSuccessUrl("/main", true)
                // 로그인 실패시 리다이렉트 URL (선택사항)
                .failureUrl("/main/login?error=true")
            )
            
            .logout(logout -> logout
                // 로그아웃 URL
                .logoutUrl("/main/logout")
                // 로그아웃 성공시 리다이렉트 URL
                .logoutSuccessUrl("/main")
                .permitAll()
                // 세션 무효화
                .invalidateHttpSession(true)
                // 쿠키 삭제
                .deleteCookies("MAINSESSIONID")
            )
            
            // CSRF 보호 (필요에 따라 비활성화)
//            .csrf(csrf -> csrf.disable()) // API나 개발 환경에서만 사용
            
            // iframe 보안 설정
            .headers(headers -> headers
                .frameOptions().sameOrigin()
            )
            
            // 예외 처리 (선택사항)
            .exceptionHandling(exceptions -> exceptions
                .accessDeniedPage("/error/403")
            );
           

        return http.build();
    }
    
    
    
    // 인증 및 인가 설정
    @Bean
    //두번재 시큐리티 필터체인
    @Order(2)
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        http
            //erp에서만 적용
        	.securityMatcher("/erp/**") 
            .authorizeHttpRequests(auth -> auth
                // 정적 리소스와 로그인 페이지는 모든 사용자에게 허용
                .requestMatchers("/**","/**/**").permitAll()
                
                // 나머지 모든 요청은 인증 필요
                .anyRequest().authenticated()
            )
            
            .formLogin(form -> form
                // 커스텀 로그인 페이지
                .loginPage("/erp/login")
                .permitAll()
                // 로그인 성공시 리다이렉트 URL
                .defaultSuccessUrl("/erp", true)
                // 로그인 실패시 리다이렉트 URL (선택사항)
                .failureUrl("/erp/login?error=true")
            )
            
            .logout(logout -> logout
                // 로그아웃 URL
                .logoutUrl("/erp/logout")
                // 로그아웃 성공시 리다이렉트 URL
                .logoutSuccessUrl("/erp/login")
                .permitAll()
                // 세션 무효화
                .invalidateHttpSession(true)
                // 쿠키 삭제
                .deleteCookies("ERPSESSIONID")
            )
            
            // CSRF 보호 (필요에 따라 비활성화)
//            .csrf(csrf -> csrf.disable()) // API나 개발 환경에서만 사용
            
            // iframe 보안 설정
            .headers(headers -> headers
                .frameOptions().sameOrigin()
            )
            
            // 예외 처리 (선택사항)
            .exceptionHandling(exceptions -> exceptions
                .accessDeniedPage("/error/403")
            );
           
        	
        return http.build();
    }
}
