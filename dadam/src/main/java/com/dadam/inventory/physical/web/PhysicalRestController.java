package com.dadam.inventory.physical.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.physical.service.PhysicalService;


@RestController // json형식으로 반환
@RequestMapping("/erp/inventory")// 앞에주소
public class PhysicalRestController {

	@Autowired
	PhysicalService physicalservice;
	
}
