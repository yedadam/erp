package com.dadam.hr.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 인사관리 메인 컨트롤러
 * - /erp/hr 진입 시 인사관리 대시보드/포털 화면 반환
 */
@Controller
@RequestMapping("/erp/hr")
public class HrMainController {
	/** HR 메인 페이지 진입 */
	@GetMapping("")
	public String hrMain() {
		return "hr/hrMain";
	}
}