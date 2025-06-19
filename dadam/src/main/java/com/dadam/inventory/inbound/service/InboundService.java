package com.dadam.inventory.inbound.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dadam.sales.purchaseorder.service.PurchaseOrderVO;

public interface InboundService {

	public List<PurchaseOrderVO> inboundPurchaseFindAll();
}
