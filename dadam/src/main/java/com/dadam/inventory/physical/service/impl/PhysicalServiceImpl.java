package com.dadam.inventory.physical.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.inventory.physical.mapper.PhysicalMapper;
import com.dadam.inventory.physical.service.PhysicalDetailVO;
import com.dadam.inventory.physical.service.PhysicalService;
import com.dadam.inventory.physical.service.PhysicalVO;
import com.dadam.inventory.warehouse.service.WarehouseVO;

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
	public List<PhysicalDetailVO> selectPhysicalDetailList(String comId, String phyCode) {
		List<PhysicalDetailVO> lists = physicalmapper.selectPhysicalDetailList(comId, phyCode);
		return lists;
	}

	@Override
	public List<WarehouseVO> physicalWarehouseList(String comId) {
		return physicalmapper.physicalWarehouseList(comId);
	}

	@Override
	public List<WarehouseVO> physicalWarehousedetailList(String comId, String whCode) {
		return physicalmapper.physicalWarehousedetailList(comId,whCode);
	}

	@Override
	public int insertPhysical(PhysicalVO vo) {
		// 실사등록
		int result = physicalmapper.insertPhysical(vo);
		List<PhysicalDetailVO> detail = vo.getSub();
		// 실사 상세 등록
		String phyDtlCode = physicalmapper.selectPhysicalDetailKey();
		String str = phyDtlCode.substring(0,5);
		int number = Integer.parseInt(phyDtlCode.substring(5));
		String newCode = null;
		for(PhysicalDetailVO list : detail) {
			if(newCode != null) {
				number ++;
			}
			newCode = str + String.format("%03d", number);
			list.setPhyDtlCode(newCode);
			list.setPhyCode(vo.getPhyCode());
			list.setComId(vo.getComId());
			physicalmapper.insertPhysicalDetail(list);
		}
		return result;
	}
	
}
