package com.dadam.account.main.Control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp")
@Controller
public class AccountMainController {
	
	@GetMapping("/accounting")
	public String test() {
		return "/acc/accountMain";
	}
}
