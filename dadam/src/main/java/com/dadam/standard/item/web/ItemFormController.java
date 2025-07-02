package com.dadam.standard.item.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.dadam.common.service.CodeService;
import com.dadam.sales.order.service.OrderService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RequestMapping("/erp")

@RequestMapping("/erp/standard")

@Controller
public class ItemFormController {
	
	final CodeService codeService; 
	
	@GetMapping("/item")
	public String item() {
		return "/standard/item";
	}
	@GetMapping("/vender")
	public String vender(Model model) {
		model.addAttribute("type",codeService.getCodeMap("vt"));
		
		return "/standard/vender"; 
	}
}
