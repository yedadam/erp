package com.dadam.inventory.inbound.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dadam.sales.purchaseorder.service.PurchaseOrderVO;

public interface InboundService {

	// 조회
	public List<PurchaseOrderVO> inboundPurchaseFindAll();
	
	// 등록
	public void purchaseInbound(PurchaseOrderVO purchaseVO);
}
