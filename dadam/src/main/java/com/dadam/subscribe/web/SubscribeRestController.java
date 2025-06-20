package com.dadam.subscribe.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.subscribe.service.SubsListVO;
import com.dadam.subscribe.service.SubscribeService;

@RequestMapping("/main")
@RestController
public class SubscribeRestController {

	@Autowired
	SubscribeService service;
	
	@PostMapping("/billingSave")
	public int billingSave(@RequestBody SubsListVO info) {
		int r = service.subsAdd(info);
		return r;
	}
}
