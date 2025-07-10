package com.dadam.inventory.physical.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp/inventory")
@Controller
public class PhysicalFormController {
	
	@GetMapping("/physical")
	public String physical() {
		return "inventory/physical";
	}
}
