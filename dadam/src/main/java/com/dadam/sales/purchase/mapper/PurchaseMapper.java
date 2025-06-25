package com.dadam.sales.purchase.mapper;

import java.util.List;

import com.dadam.sales.purchase.service.PurchaseOrderVO;

public interface PurchaseMapper {
	public List<PurchaseOrderVO> findPurchaseList(PurchaseOrderVO vo);
	public List<PurchaseOrderVO> findPurListByOrdNo(String param);
}
