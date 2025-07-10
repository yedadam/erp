package com.dadam.inventory.history.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp/inventory")
@Controller
public class HistoryFormController {
	
	@GetMapping("/history")
	public String history() {
		return "inventory/history";
	}
}
