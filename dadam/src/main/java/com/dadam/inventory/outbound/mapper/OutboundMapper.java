package com.dadam.inventory.outbound.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.inventory.hold.service.HoldVO;
import com.dadam.inventory.outbound.service.OutboundVO;

@Mapper
public interface OutboundMapper {

	// 조회
	public List<HoldVO> selectHoldOutboundList(HoldVO vo);
}
