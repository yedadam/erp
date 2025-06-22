package com.dadam.subscribe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.subscribe.mapper.SubsManagerMapper;
import com.dadam.subscribe.service.ErpUsersVO;
import com.dadam.subscribe.service.SubsManagerService;

@Service
public class SubsManagerServiceImpl implements SubsManagerService{
	
	@Autowired
	SubsManagerMapper managerMapper;
	//유저 구독 정보 조회
	@Override
	public List<ErpUsersVO> erpUserList() {
        List<ErpUsersVO> result = managerMapper.erpUserList();	  
		return result;
	}
}
