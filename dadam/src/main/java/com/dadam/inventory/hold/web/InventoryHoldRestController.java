package com.dadam.inventory.hold.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.hold.service.HoldService;
import com.dadam.inventory.hold.service.HoldVO;
import com.dadam.inventory.outbound.service.OutboundVO;


@RestController // json형식으로 반환
@RequestMapping("/erp/inventory")
public class InventoryHoldRestController {

	@Autowired
	HoldService holdservice;
	
	// 홀드 리스트
	@GetMapping("/holdList")
	public List<HoldVO> selectHoldList(HoldVO vo) {
		return holdservice.selectHoldList(vo);
	}
	
	// 발주서 리스트
	@GetMapping("/holdshipRequestList")
	public List<OutboundVO> selectShipRequestModal(HoldVO vo) {
		return holdservice.selectShipRequestModal(vo);
	}
	
	// 로트 리스트
	@GetMapping("/holdlotList")
	public List<HoldVO> selectHoldLotList(HoldVO vo){
		return holdservice.selectHoldLotList(vo);
	}
	
}
