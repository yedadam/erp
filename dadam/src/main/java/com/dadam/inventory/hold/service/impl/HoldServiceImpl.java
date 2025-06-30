package com.dadam.inventory.hold.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.inventory.hold.mapper.HoldMapper;
import com.dadam.inventory.hold.service.HoldService;
import com.dadam.inventory.hold.service.HoldVO;
import com.dadam.inventory.outbound.service.OutboundVO;

@Transactional
@Service
public class HoldServiceImpl implements HoldService{
	
	@Autowired
	HoldMapper holdMapper;
	
	// 발주서 리스트
	@Override
	public List<HoldVO> selectHoldList(HoldVO vo) {
		List<HoldVO> list = holdMapper.selectHoldList(vo);
		return list;
	}
	
	// 발주서리스트
	@Override
	public List<OutboundVO> selectShipRequestModal(HoldVO vo) {
		List<OutboundVO> list = holdMapper.selectShipRequestModal(vo);
		return list;
	}
	
}
