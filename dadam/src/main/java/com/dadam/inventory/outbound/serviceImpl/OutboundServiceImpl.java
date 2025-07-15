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
	 * 이전 출고량 조회
	 * 출고테이블 등록
	 * 출하의뢰상세 상태값 변경
	 * 홀드상세 조회(수량을 빼기위해서)
	 * 홀드상세테이블 홀드수량 변경
	 * 재고테이블 수량 조회
	 * 재고테이블 수량 변경
	 * 출하의뢰 상태값 변경 프로시저
	 * */
	@Override
	public int insertOutbound(List<OutboundVO> list) {
		int result = 0;
		System.out.println("list: " + list);
		for(OutboundVO vo : list) {
			
		}
		return result;
	}

	

}
