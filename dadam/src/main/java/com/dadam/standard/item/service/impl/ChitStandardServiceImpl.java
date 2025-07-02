package com.dadam.standard.item.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dadam.acc.account.service.ChitVO;
import com.dadam.security.service.LoginUserAuthority;
import com.dadam.standard.item.mapper.ChitStandardMapper;
import com.dadam.standard.item.service.ChitStandardService;
import com.dadam.standard.item.service.MoneyVO;

@Service
public class ChitStandardServiceImpl implements ChitStandardService{
	
	  //comName 가져오기
    String comId = "com-101";
    String deptCode = "de1001";
    public void initAuthInfo() {
        //로그인 객체값 연결
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //로그인 객체 가져오기
        Object principal = auth.getPrincipal();

        if (principal instanceof LoginUserAuthority) {
        	LoginUserAuthority user = (LoginUserAuthority) principal;
            comId = user.getComId();
            deptCode = user.getDeptCode();
        }
    }
	
	
	@Autowired
	ChitStandardMapper mapper;
	
	public List<ChitVO> chitList(String type, String value){
		 List<ChitVO> result =  mapper.ChitList(type, value, comId,deptCode);
		 return result;
	}
	
	@Override
	public List<MoneyVO> moneyList(String type, String value) {
	    List<MoneyVO> result = mapper.moneyList(type, value, comId);
		return result;
	}
	
	@Override
	public List<MoneyVO> adjList(String type, String value) {
	    List<MoneyVO> result = mapper.adjList(type, value, comId);
		return result;
	}
	
	@Override
	public List<MoneyVO> salaryList(String type, String value) {
	    List<MoneyVO> result = mapper.salaryList(type, value, comId);
		return result;
	}
}
