package com.dadam.inventory.warehouse.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.inbound.service.InboundService;

@Controller
@RequestMapping("/erp/inventory")
public class InventoryWarehouseFormController {
	
	@Autowired
	InboundService inboundservice;
	
	@GetMapping("/warehouse")
	public String warehouseList() {
		return "inventory/warehouse";
	}
}
