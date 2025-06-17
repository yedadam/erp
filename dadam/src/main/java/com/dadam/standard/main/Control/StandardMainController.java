package com.dadam.standard.main.Control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp")
@Controller
public class StandardMainController {
	
	@GetMapping("/standard")
	public String test() {
		return "/standard/standardMain";
	}
}
