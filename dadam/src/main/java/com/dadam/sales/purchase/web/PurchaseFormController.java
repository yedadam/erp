package com.dadam.sales.purchase.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/erp/sales")
public class PurchaseFormController {
	
	
	@GetMapping("/purchase")
	public String purchase() {
		return "sales/purchase";
	}
}
