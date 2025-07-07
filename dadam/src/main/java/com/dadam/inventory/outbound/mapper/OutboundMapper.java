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
}
