package com.dadam.inventory.outbound.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dadam.inventory.outbound.service.OutboundService;
import com.dadam.inventory.outbound.service.OutboundVO;

@Controller
@RequestMapping("/erp/inventory")
public class OutboundRestController {

	@Autowired
	OutboundService outboundservice;
	
	@GetMapping("/shipRequestList")
	public List<OutboundVO> selectShipRequestOutBound(OutboundVO vo){
		return outboundservice.selectShipRequestOutBound(vo); 
	}
}
