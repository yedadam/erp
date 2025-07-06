package com.dadam.inventory.inbound.service;

import java.util.List;

public interface InboundService {
	// 입고리스트 조회
	public List<InboundVO> selectPurchaseList(String comId);
	// 조회
	public List<PurchaseVO> inboundPurchaseFindAll(PurchaseVO vo);
	// 창고 조회
	public List<InboundVO> warehouseList(String comId);
	// 등록
	public int insertPurchaseInbound(List<PurchaseVO> list);
	// 현재 수량 조회
	public List<PurchaseVO> purchaseCurrQty(List<PurchaseVO> list);
}
