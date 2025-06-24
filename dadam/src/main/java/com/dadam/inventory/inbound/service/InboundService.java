package com.dadam.inventory.inbound.service;

import java.util.List;

public interface InboundService {

	// 조회
	public List<PurchaseVO> inboundPurchaseFindAll();
	// 창고 조회
	public List<InboundVO> warehouseList();
	// 등록
	public void purchaseInbound(PurchaseVO purchaseVO);
}
