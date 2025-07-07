package com.dadam.inventory.outbound.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.hold.service.HoldVO;
import com.dadam.inventory.hold.service.LotVO;
import com.dadam.inventory.outbound.service.OutboundService;
import com.dadam.inventory.outbound.service.OutboundVO;

@RestController
@RequestMapping("/erp/inventory")
public class OutboundRestController {

	@Autowired
	OutboundService outboundservice;
	
	// 출고 리스트
	@GetMapping("/outboundList")
	public List<OutboundVO> selectOutBoundList(OutboundVO vo) {
		return outboundservice.selectOutBoundList(vo);
	}
	
	// 출고상세 리스트
	@GetMapping("/outboundDetailList")
	public List<OutboundVO> selectOutBoundDetailList(OutboundVO vo) {
	return outboundservice.selectOutBoundDetailList(vo);
		}
	
	// 홀드 리스트
	@GetMapping("/outboundHoldList")
	public List<HoldVO> selectHoldOutboundList(HoldVO vo) {
	return outboundservice.selectHoldOutboundList(vo);
	}
	
	// 홀드상세 리스트
	@GetMapping("/outboundHoldDetailList")
	public List<LotVO> selectOutboundHoldDetailList(LotVO vo) {
	return outboundservice.selectOutboundHoldDetailList(vo);
	}
}
