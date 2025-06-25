package com.dadam.sales.purchase.mapper;

import java.util.List;

import com.dadam.sales.purchase.service.PurchaseOrderVO;

public interface PurchaseMapper {
	//발주메인 리스트 조회
	public List<PurchaseOrderVO> findPurchaseList(PurchaseOrderVO vo);
	//발주상세조회
	public List<PurchaseOrderVO> findPurListByOrdNo(String param);
	//발주의뢰 코드보기
	public List<PurchaseOrderVO> requestList();
}
