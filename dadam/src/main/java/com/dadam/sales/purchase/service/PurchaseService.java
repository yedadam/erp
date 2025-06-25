package com.dadam.sales.purchase.service;

import java.util.List;

public interface PurchaseService {
	
	public List<PurchaseOrderVO> findPurchaseList(PurchaseOrderVO vo);
	public List<PurchaseOrderVO> findPurListByOrdNo(String param);
}
