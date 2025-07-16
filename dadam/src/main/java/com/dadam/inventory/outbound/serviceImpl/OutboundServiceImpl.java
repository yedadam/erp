package com.dadam.inventory.outbound.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.inventory.hold.service.HoldVO;
import com.dadam.inventory.hold.service.LotVO;
import com.dadam.inventory.outbound.mapper.OutboundMapper;
import com.dadam.inventory.outbound.service.OutboundService;
import com.dadam.inventory.outbound.service.OutboundVO;
@Service
@Transactional
public class OutboundServiceImpl implements OutboundService{

	@Autowired
	OutboundMapper outboundmapper;
	
	// 출고 리스트
	@Override
	public List<OutboundVO> selectOutBoundList(OutboundVO vo) {
		List<OutboundVO> list = outboundmapper.selectOutBoundList(vo);
		return list;
	}

	// 출고상세 리스트
	@Override
	public List<OutboundVO> selectOutBoundDetailList(OutboundVO vo) {
		List<OutboundVO> list = outboundmapper.selectOutBoundDetailList(vo);
		return list;
	}
	
	// 발주서 리스트
	@Override
	public List<HoldVO> selectHoldOutboundList(HoldVO vo) {
		List<HoldVO> list = outboundmapper.selectHoldOutboundList(vo);
		return list;
	}
	// 홀드 리스트
	@Override
	public List<LotVO> selectOutboundHoldDetailList(LotVO vo) {
		List<LotVO> list = outboundmapper.selectOutboundHoldDetailList(vo);
		return list;
	}
	// 출고등록 
	/*
	 * 출고테이블 등록
	 * 출하의뢰상세 상태값 변경
	 * 홀드테이블 상태값 변경
	 * 재고테이블 수량 조회
	 * 재고테이블 수량 변경
	 * 출하의뢰 상태값 변경 프로시저
	 * */
	@Override
	public int insertOutbound(List<OutboundVO> list) {
		HoldVO ho = new HoldVO();
		int result = 0;
		int quantity = 0;
		int sum = 0;
		for(OutboundVO vo : list) {
			// 입력한 수량 + 현재출고수량
			quantity = vo.getQty() + vo.getCurrQty();
			vo.setCurrQty(quantity);
			// 상태값 처리 출고 상태값
			// ht01 == 재고이동 ht02 == 출하의뢰
			// 총 갯수 holdCode에 속한 lot들의 총 갯수
			sum = outboundmapper.sumOutbound(vo);
			// 출하의뢰라면...
			if("ht02".equals(vo.getType())) {
				vo.setType("obt01");
				// status 값 변경
				if(sum == vo.getHqty()) {
					// 홀드종료상태라면 수량도 채웠다면 출고완료 상태로 변경
					vo.setStatus("obs03");
					// 출하의뢰 상태값 입력
					vo.setShipstatus("srd03");
					// 홀드상태 출고완료로 변경
					vo.setHstatus("hs03");
				}else {
					vo.setHstatus("hs02");
					// 출하의뢰 상태값 입력
					vo.setShipstatus("srd02");
					vo.setStatus("obs02");
				}
			// 재고이동이라면...
			}else{
				vo.setType("obt02");
			}

			// 출고등록
			result += outboundmapper.insertOutbound(vo);
			// 출하의뢰상세 상태값 변경
			result += outboundmapper.updateOutboundShipRequestDetail(vo);
			// 홀드 상태값 변경
			result += outboundmapper.updateOutboundHoldStatus(vo);
			// 출고가 된다면 홀드수량과 재고 수량을 차감해야함..
			
			// 재고 수정을 위한 조회
			ho = outboundmapper.selectOutboundStock(vo);
			// 수량조절해야함
			ho.setHoldQty(ho.getHoldQty() - vo.getCurrQty());
			ho.setQuantity(ho.getQuantity() - vo.getCurrQty());
			result += outboundmapper.updateOutboundStock(vo);
			result += outboundmapper.prcShipRequestStatus(vo);
		}
		return result;
	}

	

}
