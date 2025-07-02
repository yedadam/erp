package com.dadam.standard.item.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.acc.account.service.ChitVO;
import com.dadam.common.service.GridData;
import com.dadam.standard.item.service.ChitStandardService;
import com.dadam.standard.item.service.MoneyVO;

@RestController
@RequestMapping("/erp")
public class ChitStandardRestController {
	
	@Autowired
	ChitStandardService service;
	@GetMapping("/standard/chitList")
	public List<ChitVO> chitList(@RequestParam (defaultValue = "",required = false)String type,@RequestParam (defaultValue = "",required = false)String value){
		List<ChitVO> result = service.chitList(type, value);
		return result;
	}
	
	@GetMapping("/standard/moneyList")
	public List<MoneyVO> moneyList(@RequestParam (defaultValue = "",required = false)String type,@RequestParam (defaultValue = "",required = false)String value){
		List<MoneyVO> result = service.moneyList(type, value);
		return result;
	}
	
	@GetMapping("/standard/adjList")
	public List<MoneyVO> adjList(@RequestParam (defaultValue = "",required = false)String type,@RequestParam (defaultValue = "",required = false)String value){
		List<MoneyVO> result = service.adjList(type, value);
		return result;
	}
	
	@GetMapping("/standard/salaryList")
	public List<MoneyVO> salaryList(@RequestParam (defaultValue = "",required = false)String type,@RequestParam (defaultValue = "",required = false)String value){
		List<MoneyVO> result = service.salaryList(type, value);
		return result;
	}
	
	@PostMapping("/standard/updateChit")
	public int updateChit(@RequestBody GridData<ChitVO> vo){
		int result = service.chitUpdate(vo);
		return result;
	}
}
