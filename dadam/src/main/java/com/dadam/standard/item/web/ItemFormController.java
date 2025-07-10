package com.dadam.standard.item.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.dadam.common.service.CodeService;
import com.dadam.sales.order.service.OrderService;
import com.dadam.standard.vender.service.VenderService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RequestMapping("/erp/standard")
@Controller
public class ItemFormController {
	
	final CodeService codeService; 
	final VenderService venderService; 
	
	@GetMapping("/item")
	public String item() {
		return "standard/item";
	}
	@GetMapping("/vender")
	public String vender(Model model) {
		model.addAttribute("type",codeService.getCodeMap("vt"));
		model.addAttribute("venderMaxno",venderService.findVenderMaxno()); 
	//	System.out.println("ddddd");
		return "standard/vender"; 
	}
}
