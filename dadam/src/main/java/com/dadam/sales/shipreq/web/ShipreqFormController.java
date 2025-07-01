package com.dadam.sales.shipreq.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dadam.common.service.CodeService;
import com.dadam.sales.order.service.OrderService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
@RequestMapping("/erp/sales")
public class ShipreqFormController {
	
	
	@GetMapping("/shipreq")
	public String shipreq() {
		return "sales/shipreq";
	}
}
