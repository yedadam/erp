package com.dadam.inventory.hold.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.inventory.hold.mapper.HoldMapper;
import com.dadam.inventory.hold.service.HoldService;
import com.dadam.inventory.hold.service.HoldVO;
import com.dadam.inventory.hold.service.LotVO;
import com.dadam.inventory.outbound.service.OutboundVO;

@Transactional
@Service
public class HoldServiceImpl implements HoldService{
	
	@Autowired
	HoldMapper holdMapper;
	
	// 발주서 리스트
	@Override
	public List<HoldVO> selectHoldList(HoldVO vo) {
		List<HoldVO> list = holdMapper.selectHoldList(vo);
		return list;
	}
	
	// 발주서리스트
	@Override
	public List<OutboundVO> selectShipRequestModal(HoldVO vo) {
		List<OutboundVO> list = holdMapper.selectShipRequestModal(vo);
		return list;
	}
	
	// lot리스트
	@Override
	public List<HoldVO> selectHoldLotList(HoldVO vo) {
		List<HoldVO> list = holdMapper.selectHoldLotList(vo);
		return list;
	}
	// 홀드리스트 등록 등록처리하기위해서 누적량을 조회해서 가져와야함.
	@Override
	public int insertHoldList(List<HoldVO> list) {
		for(HoldVO vo : list) {
			holdMapper.insertHoldList(vo);
			List<LotVO> lotList = vo.getLotList();
			for(LotVO lot : lotList) {
				lot.setHoldCode(vo.getHoldCode());
				lot.setComId(vo.getComId());
				holdMapper.insertHoldLotList(lot);
			}
		}
		return 0;
	}
}
