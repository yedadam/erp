package com.dadam.hr.main.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dadam.main.service.MainService;
import com.dadam.main.service.MainVO;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 인사관리 메인 컨트롤러
 * - /erp/hr 진입 시 인사관리 대시보드/포털 화면 반환
 */
@Controller
@RequestMapping("/erp/hr")
public class HrMainController {
	@Autowired
	MainService service;
	/** HR 메인 페이지 진입 */
	@GetMapping("")
	public String mainPage(HttpServletRequest request, Model model) {
		List<Integer> result = service.today();
		model.addAttribute("todayOrderPrice", result.get(0));
		model.addAttribute("todayPurchasePrice", result.get(1));
		model.addAttribute("todayPurchaseQunatity", result.get(2));
		model.addAttribute("todayTranPrice", result.get(3));
		List<MainVO> safeResult = service.safeList();
		model.addAttribute("safeList", safeResult);
	    return "home";
	}
}