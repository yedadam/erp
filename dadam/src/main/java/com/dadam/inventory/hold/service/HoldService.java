package com.dadam.inventory.hold.service;

import java.util.List;

import com.dadam.inventory.outbound.service.OutboundVO;


public interface HoldService {

	// 조회
	public List<HoldVO> selectHoldList(HoldVO vo);
	// 다건조회
	public List<LotVO> selectHoldDetailList(LotVO vo);
	// 발주서 모달 조회
	public List<OutboundVO> selectShipRequestModal(HoldVO vo);
	// lot 모달 조회
	public List<HoldVO> selectHoldLotList(HoldVO vo);
	// 홀드등록
	public int insertHoldList(List<HoldVO> list);
	// 홀드 값가져오기
	public int selectHoldDetailHoldQty(HoldVO vo);
}
