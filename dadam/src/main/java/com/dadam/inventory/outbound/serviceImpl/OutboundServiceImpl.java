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
	 * 홀드 테이블 상태값 변경
	 * 홀드상세 조회
	 * 홀드상세테이블 홀드수량, 상태값 변경
	 * 재고테이블 수량 조회
	 * 재고테이블 수량 변경
	 * 출하의뢰 상태값 변경 프로시저
	 * */
	@Override
	public int insertOutbound(List<OutboundVO> list) {
		int result = 0;
		
		for(OutboundVO vo : list) {
			String status = vo.getStatus();
			HoldVO ho = new HoldVO();
			// 메인 출고 등록
			vo.setQuantity(vo.getHdqty());
			result = outboundmapper.insertOutbound(vo);
			// 홀드 수량 반영(미출고량) quantity = 의뢰수량 qty= 입력수량
			ho = outboundmapper.selectOutboundHoldDetailCurrQty(vo);
			// 출하의뢰 상태값 변경
			// 출고상태값이 출고완료라면.. 전부 완료처리
			if(status == "obs03"){
				vo.setShipstatus("srd03");
				// 홀드테이블 상태값 변경
				vo.setHstatus("hs02");
				ho.setHdstatus("hds02");
				outboundmapper.updateOutboundShipRequestDetail(vo);
				outboundmapper.updateOutboundHoldStatus(vo);
			// 출고 상태값이 부분출고라면..
			}else {
				vo.setShipstatus("srd02");
				ho.setHdstatus("hds01");
			}
			// HoldQty = 현재입력량 + 출고된 홀드수량
			ho.setHoldQty(ho.getCurrQty() + vo.getQty());
			// 홀드 수량 조회 후 값 비교
			if(ho.getHoldQty() == vo.getHdqty()) {
				ho.setHdstatus("hds02");
			}
			// 홀드상세 수량, 상태값 변경 쿼리
			outboundmapper.updateOutboundHOldDetail(ho);

			// 값 조회
			ho = outboundmapper.selectOutboundStock(vo);
			
			ho.setHoldQty(ho.getHoldQty() - vo.getQty());
			ho.setQuantity(ho.getQuantity() - vo.getQty());
			
			// 출고 후 lot 재고수량 홀드수량 반영
			outboundmapper.updateOutboundStock(ho);
			outboundmapper.prcShipRequestStatus(vo);
		}
		return result;
	}

	

}
