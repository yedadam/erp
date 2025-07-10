package com.dadam.inventory.hold.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp/inventory")
@Controller
public class InventoryHoldFormController {
	
	@GetMapping("/hold")
	public String warehouse() {
		return "inventory/hold";
	}
}
