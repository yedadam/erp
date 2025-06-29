package com.dadam.inventory.outbound.service;

import java.util.List;

import com.dadam.inventory.hold.service.HoldVO;

public interface OutboundService {
	// 조회
	public List<HoldVO> selectHoldOutboundList(HoldVO vo);
	
}
