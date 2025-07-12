package com.dadam.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dadam.main.service.MainService;
import com.dadam.main.service.MainVO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	@Autowired
	MainService service;
	//erp 메인 페이지
	@GetMapping("/erp")
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
	
	@GetMapping("/erp/lineChart")
	@ResponseBody
	public List<MainVO> lineChart(){
		List<MainVO> result = service.lineChart();
		return result;
	}
	@GetMapping("/erp/pieChart")
	@ResponseBody
	public List<MainVO> pieChart(){
		List<MainVO> result = service.pieChart();
		return result;
	}
	@GetMapping("/erp/barChart")
	@ResponseBody
	public List<MainVO> barChart(){
		List<MainVO> result = service.barChart();
		return result;
	}
	// 메인 페이지 컨트롤러
	@GetMapping("/main")
		public String main(Model model) {
			
		return "mainHome"; 
	}
}
