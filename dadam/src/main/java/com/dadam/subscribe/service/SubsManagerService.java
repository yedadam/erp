package com.dadam.subscribe.service;

import java.util.List;
import java.util.Map;

public interface SubsManagerService {
	
	//유저 구독 정보 조회
	public List<ErpUsersVO> erpUserList(Map<String,Object> map);
	
	//구독 정보 상세 조회
	public List<SubsListVO> subsList(String comId);
	
	//매니저가 직접 수정
	public int managerUpdate(ErpUsersVO vo);
}
