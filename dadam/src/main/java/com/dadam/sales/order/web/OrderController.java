package com.dadam.sales.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/erp/sales")
public class OrderController {
  
	@GetMapping("/order")
	public String orderList() {
		return "sales/salesMain";
	}
	@GetMapping("/test")
	public String test() {
		return "sales/test";
	}
}
