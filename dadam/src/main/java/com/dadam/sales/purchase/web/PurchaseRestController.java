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
	//발주 의뢰 조회
	@GetMapping("/purRequestList")
	public List<PurchaseOrderVO> purRequestList(@RequestParam(name = "param", required = false) PurchaseOrderVO param) {

		List<PurchaseOrderVO> result = service.requestList();

		return result;
	}
	
	//발주서 의뢰 상세 조회
	@GetMapping("/purRqDetailList")
	public List<PurchaseOrderVO> purRequestDtList(@RequestParam String param){
		List<PurchaseOrderVO> result = service.requestDeatilList(param);
		System.out.println(result);
		return result;
	}
}
