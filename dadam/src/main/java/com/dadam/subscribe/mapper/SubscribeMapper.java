package com.dadam.subscribe.mapper;

import java.util.List;

import com.dadam.security.service.EmployeesVO;
import com.dadam.subscribe.service.SubsListVO;
import com.dadam.subscribe.service.SubscribeVO;

public interface SubscribeMapper {
	
	//구독 품목
	public List<SubscribeVO> menuList();
	
	//등록
	public int subsAdd(SubsListVO param);
	
	
	//정기 결제일자 조회
	public List<SubsListVO> selectSubs();
	
	// 계약만료 없데이트
	public int subsEnd(String billingKey);
	
	//첫결제시 추가
	public int firstAdd(EmployeesVO param);
	
	//중복확인
	public int sameCheck(String param);
	
	//계산서등록
	public int taxAdd (SubsListVO param);
}
