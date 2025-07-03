package com.dadam.standard.item.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
		venderService.insertVender(req);
		System.out.println(req);
		System.out.println("dddddddd");
		return 0; 
	}
	@PutMapping("/vender/update")
	public int venderUpd(@RequestBody VenderVO req) {
		System.out.println("!!11확인!!");
		System.out.println(req);
		venderService.updateVender(req);
		return 0 ;
	}
	@DeleteMapping("/vender/delete")
	public int venderDelete(@RequestParam String vdrCode) {
		venderService.deleteVender(vdrCode); 
		return 0; 
	}
}
