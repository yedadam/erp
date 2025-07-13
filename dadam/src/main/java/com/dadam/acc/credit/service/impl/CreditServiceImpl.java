package com.dadam.acc.credit.service.impl;

import java.util.List;
import java.util.Map;

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
	public List<CreditVO> creditFindAll(String comId, String type) {
	 	   Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	       System.out.println(auth+"===========");
		initAuthInfo();

		return creditMapper.creditFindAll(comId, type);
	}

	@Override
	public List<CreditVO> creditFindCode(@Param("vdrCode") String vdrCode, @Param("comId") String comId) {
		initAuthInfo();
		List<CreditVO> result = creditMapper.creditFindCode(vdrCode, comId);
		return result;
	}

	@Override
	public List<CreditVO> creditSearch(Map<String, Object> params) {
	    String comId = this.comId;
	    params.put("comId", comId);
	    return creditMapper.creditSearch(params);
	}

	@Override
	public List<CreditVO> detailFindSupplier(String vdrCode, String comId) {
		initAuthInfo();
		return creditMapper.detailFindSupplier(vdrCode, comId);
	}

	@Override
	public List<Map<String, Object>> purchaseSummary(String vdrCode, String comId) {
		return creditMapper.purchaseSummary(vdrCode, comId);
	}
	@Override
	public List<Map<String, Object>> purchaseDetail(String purOrdCode) {
		return creditMapper.purchaseDetail(purOrdCode);
	}
}
