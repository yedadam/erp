package com.dadam.inventory.physical.service;

import java.util.List;

import com.dadam.inventory.warehouse.service.WarehouseVO;

public interface PhysicalService {
	// 실사 조회
	public List<PhysicalVO> selectPhysicalList(String comId);
	// 실사 상세 조회
	public List<PhysicalDetailVO> selectPhysicalDetailList(String comId, String phyCode);
	// 창고 조회
	public List<WarehouseVO> physicalWarehouseList(String comId);
	// 창고상세 조회
	public List<WarehouseVO> physicalWarehousedetailList(String comId, String whCode);
	// 실사 등록
	public int insertPhysical(PhysicalVO vo);
}
