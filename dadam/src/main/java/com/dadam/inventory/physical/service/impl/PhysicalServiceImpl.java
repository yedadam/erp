package com.dadam.inventory.physical.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.inventory.physical.mapper.PhysicalMapper;
import com.dadam.inventory.physical.service.PhysicalService;
import com.dadam.inventory.physical.service.PhysicalVO;

@Transactional
@Service
public class PhysicalServiceImpl implements PhysicalService{
	
	@Autowired
	PhysicalMapper physicalmapper;
	
	// 실사내용조회
	@Override
	public List<PhysicalVO> selectPhysicalList(String comId) {
		List<PhysicalVO> list = physicalmapper.selectPhysicalList(comId);
		return list;
	}
	
	// 실사상세내용조회
	@Override
	public List<PhysicalVO> selectPhysicalDetailList(PhysicalVO list) {
		List<PhysicalVO> lists = physicalmapper.selectPhysicalDetailList(list);
		return lists;
	}
	
}
