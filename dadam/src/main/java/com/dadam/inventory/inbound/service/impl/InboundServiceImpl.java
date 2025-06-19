package com.dadam.inventory.inbound.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.inventory.inbound.mapper.InboundMapper;
import com.dadam.inventory.inbound.service.InboundService;
import com.dadam.sales.purchaseorder.service.PurchaseOrderVO;

@Service
public class InboundServiceImpl implements InboundService{

	@Autowired
	InboundMapper inboundMapper;

	@Override
	public List<PurchaseOrderVO> inboundPurchaseFindAll() {
		List<PurchaseOrderVO> list = inboundMapper.inboundPurchaseFindAll();
		return list;
	}
	
	
	
}
