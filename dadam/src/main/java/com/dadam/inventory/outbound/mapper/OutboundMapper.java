package com.dadam.inventory.outbound.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.inventory.hold.service.HoldVO;
import com.dadam.inventory.hold.service.LotVO;
import com.dadam.inventory.outbound.service.OutboundVO;

@Mapper
public interface OutboundMapper {
	
	// 출고 조회
	public List<OutboundVO> selectOutBoundList(OutboundVO vo);
	// 출고상세 조회
	public List<OutboundVO> selectOutBoundDetailList(OutboundVO vo);
	// 홀드 조회
	public List<HoldVO> selectHoldOutboundList(HoldVO vo);
	// 홀드상세 조회
	public List<LotVO> selectOutboundHoldDetailList(LotVO vo);
	// 출고 등록
	public int insertOutbound(OutboundVO vo);
	// 발주서 상태변경
	public int updateOutboundShipRequestDetail(OutboundVO vo);
	// 홀드 상태변경
	public int updateOutboundHoldStatus(OutboundVO vo);
	// 재고 수량 변경 위한 조회
	public HoldVO selectOutboundStock(OutboundVO vo);
	// 재고 수량 변경
	public int updateOutboundStock(OutboundVO vo);
	// 출하의뢰 상태값 변경 프로시저
	public int prcShipRequestStatus(OutboundVO ho);
	// 상태값체크위한 수량파악
	public int sumOutbound(OutboundVO ho);
}
