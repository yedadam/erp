package com.dadam.inventory.hold.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		// 홀드 등록
		for(HoldVO vo : list) {
			holdMapper.insertHoldList(vo);
			// ht01 = 재고이동 ht02 = 출하의뢰
			if(vo.getType() == "ht01") {
				vo.setShipReqDtlCode(vo.getCode());
				// 홀드 중
				if(vo.getStatus() == "hs01") {
					vo.setShipstatus("srd04");
				// 홀드 완료
				}else if(vo.getStatus() == "hs02") {
					vo.setShipstatus("srd05");
				}
				holdMapper.updateHoldShipRequestDetail(vo);
				
			// 재고이동 구현한다면...
			}else if(vo.getType() == "ht02"){
				
			}
			List<LotVO> lotList = vo.getLotList();
			int holdQty = 0;
			// 홀드 디테일 등록
			for(LotVO lot : lotList) {
				// holdDetail에 lot등록
				lot.setHoldCode(vo.getHoldCode());
				lot.setComId(vo.getComId());
				holdMapper.insertHoldLotList(lot);
				// 조회 후 등록된 값을 val에 담고. 
				holdQty = holdMapper.selectHoldStockHoldQty(lot);
				// 증감 입력처리
				holdQty = lot.getQuantity() + holdQty;
				lot.setHoldQty(holdQty);
				holdMapper.updateHoldStock(lot);
			}
		}
		
		return 0;
	}
	
	// hold상세 조회
	@Override
	public List<LotVO> selectHoldDetailList(LotVO vo) {
		return holdMapper.selectHoldDetailList(vo);
		
	}
}
