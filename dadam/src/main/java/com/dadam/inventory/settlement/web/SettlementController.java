package com.dadam.inventory.settlement.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/erp/inventory")
public class SettlementController {
	
	
	@GetMapping("/settlementForm")
	public String settlementForm() {
		return "/inventory/settlement";
	}
	
	@GetMapping("/setTest")
	public String setTest() {
		return "/settlementEle/settlement_1751784710304.html";
	}
	
	@GetMapping("/preview")
	public String preview(@RequestParam String setImage) {
		System.out.println(setImage);
		return "/settlementEle/"+setImage;
	}
}
