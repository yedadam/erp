package com.dadam.inventory.inbound.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp/inventory")
@Controller
public class InventoryInboundFormController {
	
	@GetMapping("/inbound")
	public String warehouse() {
		return "/inventory/inbound";
	}
}
