package com.dadam.inventory.physical.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.physical.service.PhysicalService;
import com.dadam.inventory.physical.service.PhysicalVO;


@RestController // json형식으로 반환
@RequestMapping("/erp/inventory")// 앞에주소
public class PhysicalRestController {

	@Autowired
	PhysicalService physicalservice;
	
	// 실사 리스트
	@GetMapping("/selectPhysical")
	public List<PhysicalVO> selectPhysicalList(@RequestParam String comId) {
		return physicalservice.selectPhysicalList(comId);
	}
	
	// 실사 리스트
	@GetMapping("/selectPhysicalDetail")
	public List<PhysicalVO> selectPhysicalDetailList(PhysicalVO vo) {
		return physicalservice.selectPhysicalDetailList(vo);
	}
}
