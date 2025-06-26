package com.dadam.inventory.outbound.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp/inventory")
@Controller
public class OutboundFormController {

	@GetMapping("/outbound")
	public String outbound() {
		return "/inventory/outbound";
	}
}
