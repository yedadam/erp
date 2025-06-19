package com.dadam.inventory.warehouse.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp")
@Controller
public class InventoryInboundFormController {
	
	@GetMapping("/inbound")
	public String warehouse() {
		return "/inventory/inbound";
	}
}
