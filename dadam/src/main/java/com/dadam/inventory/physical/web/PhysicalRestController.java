package com.dadam.inventory.physical.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.inventory.physical.service.PhysicalDetailVO;
import com.dadam.inventory.physical.service.PhysicalService;
import com.dadam.inventory.physical.service.PhysicalVO;
import com.dadam.inventory.warehouse.service.WarehouseVO;


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
	
	// 실사 상세리스트
	@GetMapping("/selectPhysicalDetail")
	public List<PhysicalDetailVO> selectPhysicalDetailList(@RequestParam String comId, @RequestParam String phyCode) {
		return physicalservice.selectPhysicalDetailList(comId, phyCode);
	}
	// 창고 리스트
	@GetMapping("/physicalWarehouseList")
	public List<WarehouseVO> physicalWarehouseList(@RequestParam String comId) {
		return physicalservice.physicalWarehouseList(comId);
	}
	// 창고위치, 품목 리스트
	@GetMapping("/physicalWarehousedetailList")
	public List<WarehouseVO> physicalWarehousedetailList(@RequestParam String comId, @RequestParam String whCode) {
		return physicalservice.physicalWarehousedetailList(comId, whCode);
	}
	// 실사 등록
	@PostMapping("/insertPhysical")
	public int insertPhysical(@RequestBody PhysicalVO vo) {
		return physicalservice.insertPhysical(vo);
	}
}
