package com.dadam.inventory.inbound.service;

import java.util.List;

public interface InboundService {

	// 조회
	public List<PurchaseVO> inboundPurchaseFindAll(PurchaseVO vo);
	// 창고 조회
	public List<InboundVO> warehouseList(String comId);
	// 등록
	public void insertPurchaseInbound(List<PurchaseVO> list);
}
