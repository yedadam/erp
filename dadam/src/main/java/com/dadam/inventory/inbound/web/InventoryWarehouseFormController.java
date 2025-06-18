package com.dadam.inventory.inbound.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp")
@Controller
public class InventoryWarehouseFormController {
	
	@GetMapping("/warehouse")
	public String warehouse() {
		return "/inventory/warehouse";
	}
}
