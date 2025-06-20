package com.dadam.inventory.inbound.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.sales.purchaseorder.service.PurchaseOrderVO;

@Mapper
public interface InboundMapper {
	
	// 조회
	public List<PurchaseOrderVO> inboundPurchaseFindAll();
	// 등록
	public void purchaseInbound(PurchaseOrderVO purchaseVO);
}
