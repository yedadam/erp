package com.dadam.inventory.outbound.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.hold.service.HoldVO;
import com.dadam.inventory.outbound.service.OutboundService;
import com.dadam.inventory.outbound.service.OutboundVO;

@RestController
@RequestMapping("/erp/inventory")
public class OutboundRestController {

	@Autowired
	OutboundService outboundservice;
	
	// 발주서 리스트
		@GetMapping("/holdOutList")
		public List<HoldVO> selectHoldOutboundList(HoldVO vo) {
			return outboundservice.selectHoldOutboundList(vo);
		}
}
