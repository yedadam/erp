package com.dadam.acc.credit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dadam.acc.credit.mapper.TransactionsMapper;
import com.dadam.acc.credit.service.TransactionsService;
import com.dadam.acc.credit.service.TransactionsVO;
import com.dadam.security.service.LoginUserAuthority;

@Service
public class TransactionsServiceImpl implements TransactionsService {
	
	@Autowired
	TransactionsMapper transactionsMapper;
	
    String comId = "com-123123";
    public void initAuthInfo() {
        //로그인 객체값 연결
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        if(auth == null) {
        	return;
        }
        //로그인 객체 가져오기
        Object principal = auth.getPrincipal();
		System.out.println("auth:" + auth);
        if (principal instanceof LoginUserAuthority) {
        	LoginUserAuthority user = (LoginUserAuthority) principal;
            comId = user.getComId();
            System.out.println("회사명: " + comId);
        }
    }
    

    @Override
    public List<TransactionsVO> getAll(String comId) {
    
    	initAuthInfo();
        return transactionsMapper.selectAll(comId);
    }


    @Override
    public void add(TransactionsVO vo) {
    	initAuthInfo();
    	vo.setComId(comId);
        transactionsMapper.insert(vo);
    }


} 