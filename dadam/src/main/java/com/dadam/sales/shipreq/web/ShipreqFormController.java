package com.dadam.sales.shipreq.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/erp/sales")
public class ShipreqFormController {
	
	
	@GetMapping("/shipreq")
	public String purchase() {
		return "sales/shipreq";
	}
}
