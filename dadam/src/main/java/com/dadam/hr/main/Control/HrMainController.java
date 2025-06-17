package com.dadam.hr.main.Control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp")
@Controller
public class HrMainController {
	@GetMapping("/hr")
	public String test() {
		return "/hr/hrMain";
	}
}
