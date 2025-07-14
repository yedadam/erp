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
	
	// 홀드 리스트
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
		int result = 0;
		// 홀드 등록
		for(HoldVO vo : list) {
			// 홀드 디테일 먼저 등록
			List<LotVO> lotList = vo.getLotList();
			int qty = 0;
			int holdQty = 0;
			// 홀드 디테일 등록
			for(LotVO qtys : lotList) {
				// Qty가 입력한 수량
				qty += qtys.getQty();
			}
			// 현재 홀드한 수량에 입력값 더하기
			qty += vo.getTotqty();
				// 입력수량이 필요수량이랑 같을경우 = 홀드완료
				if(qty == vo.getQuantity()) {
					vo.setHstatus("hs02");
					vo.setShipstatus("srd05");
				}else {
					vo.setHstatus("hs01");
					vo.setShipstatus("srd04");
				}
				
			// 홀드 등록
			result = holdMapper.insertHoldList(vo);
			System.out.println(result);
			// ht01 = 재고이동 ht02 = 출하의뢰
			// 재고이동
			 if(vo.getType() == "ht01") {
				
			// 출하의뢰
			}else if(vo.getType() == "ht02"){
				vo.setShipReqDtlCode(vo.getCode());
				// 홀드 중
				holdMapper.updateHoldShipRequestDetail(vo);
			}
			 System.out.println(lotList);
				// 홀드 디테일 등록
				for(LotVO lot : lotList) {
					// holdDetail에 lot등록
					lot.setHoldCode(vo.getHoldCode());
					lot.setComId(vo.getComId());
					holdMapper.insertHoldLotList(lot);
					// 조회 후 등록된 값을 val에 담고. 
					holdQty = holdMapper.selectHoldStockHoldQty(lot);
					// 증감 입력처리
					holdQty = lot.getQty() + holdQty;
					lot.setHoldQty(holdQty);
					holdMapper.updateHoldStock(lot);
				}
		} // for문 종료.
		
		return result;
	}
	
	// hold상세 조회
	@Override
	public List<LotVO> selectHoldDetailList(LotVO vo) {
		return holdMapper.selectHoldDetailList(vo);
	}

	@Override
	public int selectHoldDetailHoldQty(HoldVO vo) {
		return holdMapper.selectHoldDetailHoldQty(vo);
	}
}
