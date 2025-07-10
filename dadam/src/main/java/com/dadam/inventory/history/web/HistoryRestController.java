package com.dadam.inventory.history.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.history.service.HistoryService;


@RestController // json형식으로 반환
@RequestMapping("/erp/inventory")// 앞에주소
public class HistoryRestController {

	@Autowired
	HistoryService historyservice;
	
}
