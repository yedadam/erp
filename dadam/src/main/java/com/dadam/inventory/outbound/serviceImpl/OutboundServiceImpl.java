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
	@Override
	public int insertOutbound(List<OutboundVO> list) {
		int result = 0;
		for(OutboundVO vo : list) {
			String status = vo.getStatus(); 
			// 메인 출고 등록
			result = outboundmapper.insertOutbound(vo);
			/*
			출하의뢰 status
			srd01	출하의뢰
			srd02	부분출고 
			srd03	출하완료
			srd04	홀드중
			srd05	홀드완료 필요한가..?
			출고 type
			obt01	거래처
			obt02	공장
			출고 status
			obs01	대기 필요한가..?
			obs02	부분출고
			obs03	출고완료 */
			// 출하의뢰 상태값 변경
			// 출고상태값이 출고완료라면.. 전부 완료처리
			if(status == "obs03"){
				vo.setShipstatus("srd03");
				vo.setHoldstatus("hs02");
				outboundmapper.updateOutboundShipRequestDetail(vo);
				outboundmapper.updateOutboundHoldStatus(vo);
			// 출고 상태값이 부분출고라면..
			}else {
				vo.setShipstatus("srd02");
			}
			// 홀드 수량 반영(미출고량) quantity = 의뢰수량 qty= 입력수량
			vo.setHoldQty(vo.getQuantity() - vo.getQty());
			outboundmapper.updateOutboundHOldDetail(vo);

			// 값 조회
			HoldVO ho = new HoldVO();
			ho = outboundmapper.selectOutboundStock(vo);
			System.out.println("ho:" + ho);
			System.out.println("vo:" + vo);
			ho.setHoldQty(ho.getHoldQty() - vo.getQty());
			ho.setQuantity(ho.getQuantity() - vo.getQty());
			System.out.println("holdQty:" + ho.getHoldQty());
			System.out.println("quantity:" + ho.getQuantity());
			
			// 출고 후 lot 재고수량 홀드수량 반영
			outboundmapper.updateOutboundStock(ho);
		}
		return result;
	}

	

}
