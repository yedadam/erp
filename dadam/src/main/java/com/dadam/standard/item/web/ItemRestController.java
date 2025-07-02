package com.dadam.standard.item.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.standard.item.service.ItemService;
import com.dadam.standard.item.service.ItemVO;
import com.dadam.standard.vender.service.VenderService;
import com.dadam.standard.vender.service.VenderVO;

@RestController
@RequestMapping("/erp/standard")
public class ItemRestController {
	
	@Autowired
	ItemService itemservice;
	@Autowired
	VenderService venderService;
	
	@GetMapping("/itemAll")
	public List<ItemVO> itemAll(@RequestParam(defaultValue = "",required = false) String type,@RequestParam(defaultValue = "",required = false) String value) {
		return itemservice.itemFindAll(type,value);
	}
	@PostMapping("/vender/register")
	public int venderReg(@RequestBody VenderVO req) {
		System.out.println("프론트에서 넘어오는지 확인ㅇㅇㅇㅇㅇ");
		System.out.println(req);
		return 0; 
	}
}
