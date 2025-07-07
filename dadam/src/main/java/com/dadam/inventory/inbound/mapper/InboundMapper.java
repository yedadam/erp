package com.dadam.inventory.inbound.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.inventory.inbound.service.InboundVO;
import com.dadam.inventory.inbound.service.PurchaseVO;

@Mapper
public interface InboundMapper {
	// 입고리스트 조회
	public List<InboundVO> selectPurchaseList(String comId);
	// 조회
	public List<PurchaseVO> inboundPurchaseFindAll(PurchaseVO vo);
	// 창고 조회
	public List<InboundVO> warehouseList(String comId);
	// 등록
	public int insertPurchaseInbound(PurchaseVO vo);
	// 현재 수량 조회
	public int purchaseCurrQty(PurchaseVO vo);
	// 상태값 업데이트
	public int updatePurchaseOrderDetailInbound(PurchaseVO vo);
	// 재고테이블 반영
	public int updateStockInbound(PurchaseVO vo);
	// 프로시저
	public int prcPurchaseOrderStatus(PurchaseVO vo);
}
