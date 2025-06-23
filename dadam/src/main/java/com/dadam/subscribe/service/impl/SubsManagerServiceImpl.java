package com.dadam.subscribe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.security.service.ErpUserVO;
import com.dadam.subscribe.mapper.SubsManagerMapper;
import com.dadam.subscribe.service.ErpUsersVO;
import com.dadam.subscribe.service.SubsListVO;
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
	
	@Override
	public List<SubsListVO> subsList(String comId) {
	   List<SubsListVO> result = managerMapper.subsInfo(comId);
		return result;
	}
	
	@Transactional
	@Override
	public int managerUpdate(ErpUsersVO vo) {
		
		String check = managerMapper.subsCheck(vo.getComId());
		System.out.println(check);
		int result = 0;
		vo.getSubsList().get(0).setSubsCode(check);
		
		vo.getSubsList().get(0).setComId(vo.getComId());
		if(check==null) {
			result = managerMapper.subsManagerAdd(vo.getSubsList().get(0));
		}else {
			result = managerMapper.subsManagerUpdate(vo.getSubsList().get(0));
		}
		result = managerMapper.erpManagerUpdate(vo);
		return result;
	}
	
	
}
