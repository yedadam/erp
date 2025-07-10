package com.dadam.inventory.physical.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.inventory.physical.service.PhysicalVO;

@Mapper
public interface PhysicalMapper {
	public List<PhysicalVO> selectPhysicalList(PhysicalVO list);
}
