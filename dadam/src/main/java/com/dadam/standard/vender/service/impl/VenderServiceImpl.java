package com.dadam.standard.vender.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dadam.security.service.LoginUserAuthority;
import com.dadam.standard.vender.mapper.VenderMapper;
import com.dadam.standard.vender.service.VenderService;
import com.dadam.standard.vender.service.VenderVO;
@Service
public class VenderServiceImpl implements VenderService {
	//comName 가져오기
    String comId = "com-101";
    String empId="e1014";
    public void initAuthInfo() {
        //로그인 객체값 연결
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //로그인 객체 가져오기
        Object principal = auth.getPrincipal();

        if (principal instanceof LoginUserAuthority) {
        	LoginUserAuthority user = (LoginUserAuthority) principal;
            comId = user.getComId();
        
            user.getUserId(); 
            System.out.println("회사명: " + comId);
        }
    }
	@Autowired
	VenderMapper mapper; 
	
	@Override
	public List<VenderVO> venderFindAll(String type,String value) {
		List<VenderVO> result=mapper.venderFindAll(comId,type,value); 
		return result;
	}

}
