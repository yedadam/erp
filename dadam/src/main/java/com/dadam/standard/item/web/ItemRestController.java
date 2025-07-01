package com.dadam.standard.item.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.standard.item.service.ItemService;
import com.dadam.standard.item.service.ItemVO;

@RestController
@RequestMapping("/erp/standard")
public class ItemRestController {
	
	@Autowired
	ItemService itemservice;
	
	@GetMapping("/itemAll")
	public List<ItemVO> itemAll(@RequestParam(defaultValue = "",required = false) String type,@RequestParam(defaultValue = "",required = false) String value) {
		return itemservice.itemFindAll(type,value);
	}
}
