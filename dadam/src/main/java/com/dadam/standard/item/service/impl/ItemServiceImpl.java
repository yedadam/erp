package com.dadam.standard.item.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dadam.security.service.LoginUserAuthority;
import com.dadam.standard.item.mapper.ItemMapper;
import com.dadam.standard.item.service.ItemService;
import com.dadam.standard.item.service.ItemVO;

@Service
public class ItemServiceImpl implements ItemService{
	
	  //comName 가져오기
    String comId = "com-101";
    public void initAuthInfo() {
        //로그인 객체값 연결
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //로그인 객체 가져오기
        Object principal = auth.getPrincipal();

        if (principal instanceof LoginUserAuthority) {
        	LoginUserAuthority user = (LoginUserAuthority) principal;
            comId = user.getComId();
            System.out.println("회사명: " + comId);
        }
    }
    
	@Autowired
	ItemMapper mapper;

	// 전체조회
	@Override
	public List<ItemVO> itemFindAll(String type,String value) {
		initAuthInfo(); 
		List<ItemVO> result = mapper.itemFindAll(type,value,comId);
		return result;
	}

	@Override
	public List<ItemVO> itemFindit02(String type, String value) {
		initAuthInfo();
		List<ItemVO> result = mapper.itemFindit02(type,value,comId);
		return result;
	}
}
