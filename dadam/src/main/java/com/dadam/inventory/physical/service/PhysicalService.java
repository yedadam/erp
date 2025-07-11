package com.dadam.inventory.physical.service;

import java.util.List;

public interface PhysicalService {
	// 실사 조회
	public List<PhysicalVO> selectPhysicalList(String comId);
	// 실사 상세 조회
	public List<PhysicalVO> selectPhysicalDetailList(PhysicalVO list);
}
