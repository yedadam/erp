package com.dadam.subscribe.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.subscribe.service.SubsListVO;
import com.dadam.subscribe.service.SubscribeService;

@RequestMapping("/main")
@RestController
public class SubscribeRestController {

	@Autowired
	SubscribeService service;
	
	//중복등록
	@PostMapping("/billingSave")
	public int billingSave(@RequestBody SubsListVO info) {
		int r = service.subsAdd(info);
		return r; 
	}
	
	//중복체크
	@GetMapping("/sameCheck")
	public int sameCheck(@RequestParam String param) {
		System.out.println("파람"+param);
		int r= service.sameCheck(param);
		return r;
	}
}
