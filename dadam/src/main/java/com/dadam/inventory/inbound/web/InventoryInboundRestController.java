package com.dadam.inventory.inbound.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.inbound.service.InboundService;
import com.dadam.sales.purchaseorder.service.PurchaseOrderVO;
import com.dadam.sample.service.SampleVO;

@RestController // json형식으로 반환
@RequestMapping("/erp/inventory")
public class InventoryInboundRestController {

	@Autowired
	InboundService inboundservice;
	
	@GetMapping("/purchaseList")
	public List<PurchaseOrderVO> inboundPurchaseOrderFindAll() {
		
		return inboundservice.inboundPurchaseFindAll();
	}
}
