package com.dadam.inventory.inbound.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.inbound.service.InboundService;
import com.dadam.inventory.inbound.service.InboundVO;
import com.dadam.inventory.inbound.service.PurchaseVO;

/**
 * 입고 관련 Rest 컨트롤러 (JSON 반환)
 */
@RestController
@RequestMapping("/erp/inventory")
public class InventoryInboundRestController {
	@Autowired
	private InboundService inboundService;

	/**
	 * 발주서 리스트 조회
	 * @param vo 검색 조건
	 * @return 발주서 리스트
	 */
	@GetMapping("/purchaseList")
	public List<PurchaseVO> inboundPurchaseOrderFindAll(PurchaseVO vo) {
		return inboundService.inboundPurchaseFindAll(vo);
	}

	/**
	 * 입고 등록
	 * @param list 입고 등록 데이터
	 * @return 등록 결과(success, message)
	 */
	@PostMapping("/purchaseRegister")
	public String updatePurchaseOrderDetailInbound(@RequestBody List<PurchaseVO> list) {
		inboundService.insertPurchaseInbound(list);
		return "redirect:/erp/inventory/inbound";
	}

	/**
	 * 창고 리스트 조회
	 * @param comId 회사코드
	 * @return 창고 리스트
	 */
	@GetMapping("/warehouseList")
	public List<InboundVO> warehouseList(@RequestParam String comId) {
		return inboundService.warehouseList(comId);
	}
}
