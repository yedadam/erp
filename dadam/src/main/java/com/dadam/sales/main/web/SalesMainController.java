package com.dadam.sales.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp")
@Controller
public class SalesMainController {
	
	@GetMapping("/sales")
	public String test() {
		return "/sales/salesMain";
	}
	//@GetMapping("/quote")
	//public String quote() {
		//return "/sales/quote";	
	//}
	//@GetMapping("/shipreq")
	//public String shipreq() {
		//return "/sales/shipreq";	
  //}
}