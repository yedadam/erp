package com.dadam.subscribe.mapper;

import java.util.List;

import com.dadam.subscribe.service.ErpUsersVO;

public interface SubsManagerMapper {
	
	//유저 구독 정보 조회
	public List<ErpUsersVO> erpUserList();
}
