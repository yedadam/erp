package com.dadam.inventory.outbound.service;

import java.util.List;

public interface OutboundService {

	public List<OutboundVO> selectShipRequestOutBound(OutboundVO vo);
}
