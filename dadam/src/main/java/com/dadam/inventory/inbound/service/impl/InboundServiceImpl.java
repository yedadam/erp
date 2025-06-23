package com.dadam.inventory.inbound.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.inventory.inbound.mapper.InboundMapper;
import com.dadam.inventory.inbound.service.InboundService;
import com.dadam.inventory.inbound.service.PurchaseVO;
import com.dadam.sales.purchaseorder.service.PurchaseOrderVO;

@Service
public class InboundServiceImpl implements InboundService{

	@Autowired
	InboundMapper inboundMapper;

	@Override
	public List<PurchaseVO> inboundPurchaseFindAll() {
		List<PurchaseVO> list = inboundMapper.inboundPurchaseFindAll();
		return list;
	}

	@Override
	public void purchaseInbound(PurchaseVO purchaseVO) {
		inboundMapper.purchaseInbound(purchaseVO);
	}
	
}
