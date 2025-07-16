package com.dadam.inventory.physical.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.inventory.physical.service.PhysicalDetailVO;
import com.dadam.inventory.physical.service.PhysicalVO;
import com.dadam.inventory.warehouse.service.WarehouseVO;

@Mapper
public interface PhysicalMapper {
	// 실사 조회
	public List<PhysicalVO> selectPhysicalList(String comId);
	// 실사 상세 조회
	public List<PhysicalVO> selectPhysicalDetailList(PhysicalVO list);
	// 창고 조회
	public List<WarehouseVO> physicalWarehouseList(String comId);
	// 창고상세 조회
	public List<WarehouseVO> physicalWarehousedetailList(String comId, String whCode);
	// 실사 등록
	public int insertPhysical(PhysicalVO vo);
	public int insertPhysicalDetail(PhysicalDetailVO vo);
}
