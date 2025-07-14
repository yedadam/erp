package com.dadam.inventory.hold.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.inventory.hold.service.HoldVO;
import com.dadam.inventory.hold.service.LotVO;
import com.dadam.inventory.outbound.service.OutboundVO;

@Mapper
public interface HoldMapper {
	
	// 조회
	public List<HoldVO> selectHoldList(HoldVO vo);
	// 다건조회
	public List<LotVO> selectHoldDetailList(LotVO vo);
	// 발주서 모달 조회
	public List<OutboundVO> selectShipRequestModal(HoldVO vo);
	// lot 모달 조회
	public List<HoldVO> selectHoldLotList(HoldVO vo);
	// 홀드등록
	public int selectHoldKey(HoldVO list);
	public String getHoldCode(HoldVO list);
	public int insertHoldList(HoldVO list);
	public int updateHoldList(HoldVO list);
	// 홀드Lot등록
	public int insertHoldLotList(LotVO list);
	// 홀드 등록할때 상태 값 변경
	public int updateHoldShipRequestDetail(HoldVO list);
	// 홀드 등록(stock테이블 예약수량 업데이트)
	public int updateHoldStock(LotVO list);
	// 홀드 등록(현재 예약 재고 조회)
	public int selectHoldStockHoldQty(LotVO list);
	public int selectHoldDetailHoldQty(HoldVO vo);
}
