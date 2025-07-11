package com.dadam.standard.vender.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.standard.vender.service.VenderService;
import com.dadam.standard.vender.service.VenderVO;

@RestController
@RequestMapping("/erp/standard")
public class VenderRestController {
	
	@Autowired
	VenderService venderservice;
	
	@GetMapping("/venderAll")
	public List<VenderVO> venderAll(@RequestParam (defaultValue = "",required = false) String type,@RequestParam(defaultValue = "",required = false) String value) {
		return venderservice.venderFindAll(type,value);
	}
	@GetMapping("/venderAllList")
	public List<VenderVO> venderAllList(@RequestParam (defaultValue = "",required = false) String type,@RequestParam(defaultValue = "",required = false) String value) {
		return venderservice.venderFindAllList(type,value);
	}
}
