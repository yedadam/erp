package com.dadam.acc.credit.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dadam.acc.credit.mapper.CreditMapper;
import com.dadam.acc.credit.service.CreditService;
import com.dadam.acc.credit.service.CreditVO;
import com.dadam.security.service.LoginUserAuthority;

@Service
public class CreditServiceImpl implements CreditService{
	
    String comId = "com-101";
    public void initAuthInfo() {
        //로그인 객체값 연결
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //로그인 객체 가져오기
        System.out.println(auth);
        Object principal = auth.getPrincipal();
		System.out.println("auth:" + auth);
        if (principal instanceof LoginUserAuthority) {
        	LoginUserAuthority user = (LoginUserAuthority) principal;
            comId = user.getComId();
            System.out.println("회사명: " + comId);
        }
    }
	
	@Autowired CreditMapper creditMapper;

	@Override
	public List<CreditVO> creditFindAll(@Param("comId") String comId) {
	 	   Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	       System.out.println(auth+"===========");
		initAuthInfo();

		return creditMapper.creditFindAll(comId);
	}

	@Override
	public List<CreditVO> creditFindCode(@Param("vdrCode") String vdrCode, @Param("comId") String comId) {
		initAuthInfo();
		List<CreditVO> result = creditMapper.creditFindCode(vdrCode, comId);
		return result;
	}

}
