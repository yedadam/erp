package com.dadam.subscribe.mapper;

import java.util.List;

import com.dadam.subscribe.service.ErpUsersVO;
import com.dadam.subscribe.service.SubsListVO;

public interface SubsManagerMapper {
	
	//유저 구독 정보 조회
	public List<ErpUsersVO> erpUserList();
	
	//구독 정보 상세 조회
	public List<SubsListVO> subsInfo(String cid);
}
