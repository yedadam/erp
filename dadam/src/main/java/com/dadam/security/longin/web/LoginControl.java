package com.dadam.security.longin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dadam.security.service.EmployeesVO;

import jakarta.servlet.http.HttpSession;
/* 
 * @author 신현욱
 * @since 2025.06.18
 * @desc 로그인 화면 스타일
 * @history
 *   - 2025.06.18 신현욱: 최초 작성
 */
@Controller
public class LoginControl {
	
	@Autowired
	
	
	@GetMapping("/main/login")
	public String mainLoginPage() {
		return "account/mainLogin";
	}
   @GetMapping("/erp/login")
     public String userLoginPage() {
	   return "account/login";
   }
   
	
}
