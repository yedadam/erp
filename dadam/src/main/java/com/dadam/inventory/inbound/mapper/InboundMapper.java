package com.dadam.inventory.inbound.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.inventory.inbound.service.InboundVO;
import com.dadam.inventory.inbound.service.PurchaseVO;

@Mapper
public interface InboundMapper {
	
	// 조회
	public List<PurchaseVO> inboundPurchaseFindAll(PurchaseVO vo);
	// 창고 조회
	public List<InboundVO> warehouseList();
	// 등록
	public void insertPurchaseInbound(PurchaseVO vo);
	// 상태값 업데이트
	public void updatePurchaseOrderDetailInbound(PurchaseVO vo);
	// 재고테이블 반영
	public void updateStockInbound(PurchaseVO vo);
}
