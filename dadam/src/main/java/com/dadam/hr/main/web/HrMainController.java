package com.dadam.hr.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HR 메인 페이지 컨트롤러 (단순 View 반환)
 */
@RequestMapping("/erp")
@Controller
public class HrMainController {
	/** HR 메인 페이지 진입 */
	@GetMapping("/hr")
	public String test() {
		return "/hr/hrMain";
	}
}