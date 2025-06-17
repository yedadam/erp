package com.dadam.inventory.main.Control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp")
@Controller
public class InventoryMainController {
	@GetMapping("/inventory")
	public String test() {
		return "/inventory/inventoryMain";
	}
}
