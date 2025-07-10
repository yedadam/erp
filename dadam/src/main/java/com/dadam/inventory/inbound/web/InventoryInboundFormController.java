package com.dadam.inventory.inbound.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 입고 등록 화면 컨트롤러
 */
@Controller
@RequestMapping("/erp/inventory")
public class InventoryInboundFormController {
	/**
	 * 입고 등록 화면 반환
	 * @return 입고 등록 템플릿
	 */
	@GetMapping("/inbound")
	public String warehouse() {
		return "inventory/inbound";
	}
}
