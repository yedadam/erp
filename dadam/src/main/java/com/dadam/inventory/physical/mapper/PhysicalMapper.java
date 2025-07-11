package com.dadam.inventory.physical.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.inventory.physical.service.PhysicalVO;

@Mapper
public interface PhysicalMapper {
	// 실사 조회
	public List<PhysicalVO> selectPhysicalList(String comId);
	// 실사 상세 조회
	public List<PhysicalVO> selectPhysicalDetailList(PhysicalVO list);
}
