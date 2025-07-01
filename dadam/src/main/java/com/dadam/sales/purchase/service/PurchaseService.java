package com.dadam.sales.purchase.service;

import java.util.List;

import com.dadam.common.service.GridData;
import com.dadam.standard.vender.service.VenderVO;

public interface PurchaseService {
	
	//발주메인 리스트 조회
	public List<PurchaseOrderVO> findPurchaseList(String type, String value);
	//발주상세조회
	public List<PurchaseOrderDetailVO> findPurListByOrdNo(String param);
	//발주의뢰 코드보기
	public List<PurchaseOrderVO> requestList(String type, String value);
	//발주의뢰 상세코드
	public List<PurchaseOrderVO> requestDeatilList(String param);
	//발주 등록
	public int purchaseOrderAdd(PurReqVO param);
	//발주 메인 업데이트
	public int purOrderUpdate(PurchaseOrderVO param);
	//발주 상세 없데이트
	public int purOderDtUpdate(GridData<PurchaseOrderDetailVO> vo);
	//발주 메인 삭제
	public int purDelete(String purOrdCode);
	//모달창 거래처 조회
	public List<VenderVO> venderList(String type,String value);
	//자동 완성 기능
	public List<VenderVO> autoVender(String type, String value);
	
}
