package com.dadam.common;

import java.time.LocalDate;
import java.sql.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import com.dadam.security.service.LoginMainAuthority;
import com.dadam.security.service.LoginUserAuthority;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class AuthInterceptor implements HandlerInterceptor{
	 //comName 가져오기
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	   
	   String optionCode = "com-101";
	   Date subsExipriation = Date.valueOf(LocalDate.now().plusDays(1));
       //로그인 객체값 연결
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       //로그인 객체 가져오기
       Object principal = auth.getPrincipal();

       if (principal instanceof LoginUserAuthority) {
       	LoginUserAuthority user = (LoginUserAuthority) principal;
       	optionCode = user.getOptionCode();
        subsExipriation = user.getSubsExpiration();
       }
       if(principal instanceof LoginMainAuthority) {
    	 LoginMainAuthority user = (LoginMainAuthority) principal;
    	 subsExipriation = user.getSubsExpiration();
       }
	   
	   // 권한 체크 로직
       String uri = request.getRequestURI();
       
		
		 if(subsExipriation.toLocalDate().isBefore(LocalDate.now())) {
			 response.sendRedirect("/error/403.html");
	         return false;
		 }
       if (uri.startsWith("/erp/sales") && optionCode.equals("oc-101")) {
           response.sendRedirect("/error/403.html");
           return false;
       }
       if (uri.startsWith("/erp/inventory") && optionCode.equals("oc-101")) {
    	   response.sendRedirect("/error/403.html");
    	   return false;
       }
       if (uri.startsWith("/erp/accounting") && optionCode.equals("oc-102")) {
    	   response.sendRedirect("/error/403.html");
           return false;
       }
       if (uri.startsWith("/erp/hr") && optionCode.equals("oc-102")) {
    	   response.sendRedirect("/error/403.html");
           return false;
       }

	return true;
 }
}
