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
			System.out.println(vo);
			//result = outboundmapper.insertOutbound(vo);
		}
		return result;
	}

	

}
