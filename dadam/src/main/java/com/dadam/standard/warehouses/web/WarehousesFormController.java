package com.dadam.standard.warehouses.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp/standard")
@Controller
public class WarehousesFormController {
	
	@GetMapping("/warehouses")
	public String warehouses() {
		return "standard/warehouses";
	}
}
