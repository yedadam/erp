package com.dadam.inventory.inbound.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.inbound.service.InboundService;
import com.dadam.inventory.inbound.service.InboundVO;
import com.dadam.inventory.inbound.service.PurchaseVO;


@RestController // json형식으로 반환
@RequestMapping("/erp/inventory")
public class InventoryInboundRestController {

	@Autowired
	InboundService inboundservice;
	
	
	// 입고 리스트
	@GetMapping("/purchaseList")
	public List<InboundVO> inboundPurchaseOrderFindAll(@RequestParam String comId) {
		return inboundservice.selectPurchaseList(comId);
	}
	
	// 발주서 리스트
	@GetMapping("/purchaseOrderList")
	public List<PurchaseVO> inboundPurchaseOrderFindAll(PurchaseVO vo) {
		return inboundservice.inboundPurchaseFindAll(vo);
	}
	// 입고 등록
	@PostMapping("/purchaseRegister")
	public String updatePurchaseOrderDetailInbound(@RequestBody List<PurchaseVO> list) {
		inboundservice.insertPurchaseInbound(list);
		return "redirect:/erp/inventory/inbound";
	}
	// 창고 리스트
	@GetMapping("/warehouseList")
	public List<InboundVO> warehouseList(@RequestParam String comId) {
		return inboundservice.warehouseList(comId);
	}
	// 현재수량
	@PostMapping("/purchaseCurrQty")
	public List<PurchaseVO> purchaseCurrQty(@RequestBody List<PurchaseVO> list) {
		return inboundservice.purchaseCurrQty(list);
	}
}
