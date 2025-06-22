package com.dadam.subscribe.service;

import java.util.List;

public interface SubsManagerService {
	
	//유저 구독 정보 조회
	public List<ErpUsersVO> erpUserList();
	
	//구독 정보 상세 조회
	public List<SubsListVO> subsList(String comId);
}
