package com.dadam.standard.item.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erp/standard")
@Controller
public class ItemFormController {
	
	@GetMapping("/item")
	public String item() {
		return "/standard/item";
	}
}
