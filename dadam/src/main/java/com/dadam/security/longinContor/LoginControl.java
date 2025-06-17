package com.dadam.security.longinContor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControl {
	@GetMapping("/main/login")
	public String mainLoginPage() {
		return "account/mainLogin";
	}
   @GetMapping("/erp/login")
     public String userLoginPage() {
	   return "account/login";
   }

}
