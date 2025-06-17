package com.dadam.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	//erp 메인 페이지
	@GetMapping("/erp")
		public String mainPage(HttpServletRequest request, Model model) {

	    return "home";
	}
	// 메인 페이지 컨트롤러
	@GetMapping("/main")
		public String main(Model model) {
			
		return "mainHome"; 
	}
}
