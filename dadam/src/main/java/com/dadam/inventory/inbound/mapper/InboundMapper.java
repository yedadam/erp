package com.dadam.inventory.inbound.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.sales.purchaseorder.service.PurchaseOrderVO;

@Mapper
public interface InboundMapper {
	
	public List<PurchaseOrderVO> inboundPurchaseFindAll();
}
