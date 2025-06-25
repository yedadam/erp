package com.dadam.inventory.inbound.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.inventory.inbound.mapper.InboundMapper;
import com.dadam.inventory.inbound.service.InboundService;
import com.dadam.inventory.inbound.service.InboundVO;
import com.dadam.inventory.inbound.service.PurchaseVO;

@Service
public class InboundServiceImpl implements InboundService{

	@Autowired
	InboundMapper inboundMapper;
	// 발주서 리스트
	@Override
	public List<PurchaseVO> inboundPurchaseFindAll() {
		List<PurchaseVO> list = inboundMapper.inboundPurchaseFindAll();
		return list;
	}
	// 입고 등록
	@Override
	public void purchaseInbound(PurchaseVO purchaseVO) {
		inboundMapper.purchaseInbound(purchaseVO);
	}
	// 창고 리스트
	@Override
	public List<InboundVO> warehouseList() {
		List<InboundVO> list = inboundMapper.warehouseList();
		return list;
	}
	
}
