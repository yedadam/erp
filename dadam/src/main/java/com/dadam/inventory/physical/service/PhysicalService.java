package com.dadam.inventory.physical.service;

import java.util.List;

import com.dadam.inventory.warehouse.service.WarehouseVO;

public interface PhysicalService {
	// 실사 조회
	public List<PhysicalVO> selectPhysicalList(String comId);
	// 실사 상세 조회
	public List<PhysicalVO> selectPhysicalDetailList(PhysicalVO list);
	// 창고 조회
	public List<WarehouseVO> physicalWarehouseList(String comId);
	// 창고상세 조회
	public List<WarehouseVO> physicalWarehousedetailList(String comId, String whCode);
}
