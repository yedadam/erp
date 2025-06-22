package com.dadam.subscribe.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class SubsManagerFormController {
	
	@GetMapping("/subManager")
	public String subManagerForm() {
		
		return "/subscribe/manager";
	}
}
