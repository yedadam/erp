package com.dadam.inventory.inbound.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.inventory.inbound.service.PurchaseVO;

@Mapper
public interface InboundMapper {
	
	// 조회
	public List<PurchaseVO> inboundPurchaseFindAll();
	// 등록
	public void purchaseInbound(PurchaseVO purchaseVO);
}
