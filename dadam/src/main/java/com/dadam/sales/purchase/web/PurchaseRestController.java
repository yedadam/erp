package com.dadam.sales.purchase.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.sales.purchase.service.PurchaseOrderVO;
import com.dadam.sales.purchase.service.PurchaseService;


@RestController
@RequestMapping("/erp/sales")
public class PurchaseRestController {
	
	@Autowired
	PurchaseService service;
    	  
	//조회
	@GetMapping("/purMainList")
	public List<PurchaseOrderVO> purchaseList(@RequestParam(name = "param", required = false) PurchaseOrderVO param) {
		
		List<PurchaseOrderVO> result = service.findPurchaseList(param);
		
		return result;
	}
}
