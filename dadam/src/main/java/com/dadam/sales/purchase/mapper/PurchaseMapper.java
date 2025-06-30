package com.dadam.sales.purchase.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dadam.sales.purchase.service.PurchaseOrderDetailVO;
import com.dadam.sales.purchase.service.PurchaseOrderVO;
import com.dadam.standard.vender.service.VenderVO;

public interface PurchaseMapper {
	//발주메인 리스트 조회
	public List<PurchaseOrderVO> findPurchaseList(PurchaseOrderVO vo);
	//발주상세조회
	public List<PurchaseOrderDetailVO> findPurListByOrdNo(@Param("param") String param, @Param("comId") String comId);
	//발주의뢰 코드보기
	public List<PurchaseOrderVO> requestList(@Param("comId") String comId,@Param("type") String type, @Param("value") String value);
	//발주의뢰 상세코드
	public List<PurchaseOrderVO> requestDeatilList(@Param("param") String param, @Param("comId") String comId);
	//발주 헤더 등록
	public int purchaseOrderAdd(PurchaseOrderVO param);
	//발주 바디 등록
	public int purchaseOrderDetailAdd(PurchaseOrderDetailVO item);
	//발주 의뢰 상태값 변경
	public int reqStatusUpdate(@Param("status") String status,@Param("purReqCode") String purReqCode,@Param("comId") String comId);
	//발주 메인 업데이트
	public int purOrderUpdate(PurchaseOrderVO param);
	//발주 상세 업데이트 
	public int purOrderUpdateRows(PurchaseOrderDetailVO param);
	//발주 상세 업데이트
	public int purOrderDeleteRows(PurchaseOrderDetailVO param);
	//발주 메인 삭제
	public int purMainDelete(@Param("purOrdCode") String purOrdCode, @Param("comId") String comId);
	//발주 상세 삭제
	public int purDTtlDelete(@Param("purOrdCode") String purOrdCode, @Param("comId") String comId);
	//모달창 거래처 조회
	public List<VenderVO> venderList(@Param("comId") String comId, @Param("type") String type, @Param("value") String value);
}	
