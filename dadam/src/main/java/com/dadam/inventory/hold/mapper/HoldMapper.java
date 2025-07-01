package com.dadam.inventory.hold.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.inventory.hold.service.HoldVO;
import com.dadam.inventory.outbound.service.OutboundVO;

@Mapper
public interface HoldMapper {
	
	// 조회
	public List<HoldVO> selectHoldList(HoldVO vo);
	// 발주서 모달 조회
	public List<OutboundVO> selectShipRequestModal(HoldVO vo);
	// lot 모달 조회
	public List<HoldVO> selectHoldLotList(HoldVO vo);
}
