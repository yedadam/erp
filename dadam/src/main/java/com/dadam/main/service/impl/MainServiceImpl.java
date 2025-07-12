package com.dadam.main.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dadam.main.mapper.MainMapper;
import com.dadam.main.service.MainService;
import com.dadam.main.service.MainVO;
import com.dadam.security.service.LoginUserAuthority;

@Service
public class MainServiceImpl implements MainService{
	@Autowired
	MainMapper mapper;
	
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
	@Override
	public ArrayList<Integer> today() {
		initAuthInfo();
		ArrayList<Integer> result = new ArrayList<>();
		  
		result.add(mapper.todayOrderPrice(comId));
		result.add(mapper.todayPurchasePrice(comId));
		result.add(mapper.todayPurchaseQunatity(comId));
		result.add(mapper.todayTranPrice(comId));
		return result;
	}
	
	@Override
	public List<MainVO> lineChart() {
		initAuthInfo();
		List<MainVO> result = mapper.lineChart(comId); 
		return result;
	}
	
	@Override
	public List<MainVO> pieChart() {
		initAuthInfo();
		List<MainVO> result = mapper.pieChart(comId); 
		return result;
	}
	
	@Override
	public List<MainVO> safeList() {
		initAuthInfo();
		List<MainVO> result = mapper.safeList(comId);
		return result;
	}
	
	@Override
	public List<MainVO> barChart() {
		initAuthInfo();
		List<MainVO> result = mapper.barChart(comId);
		return result;
	}
}
