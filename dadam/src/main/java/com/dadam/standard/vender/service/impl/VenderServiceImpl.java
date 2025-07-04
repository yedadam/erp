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
            empId=user.getUserId(); 
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

	@Override
	public int insertVender(VenderVO vender) {
	  vender.setEmpId(empId); //사용자 empId 부여 
	  vender.setComId(comId);
	  int result=mapper.insertVender(vender); //vender insert 
		return result;
	}

	@Override
	public int updateVender(VenderVO vender) {
	    vender.setEmpId(empId); //사용자 empId 부여 
		vender.setComId(comId);  
		int result=mapper.updateVender(vender); 
		return result;
	}

	@Override
	public int deleteVender(String vdrCode) {
	    mapper.deleteVender(comId, vdrCode);  //회사에서 부여한 comId를 넣음 
		return 0;
	}

	@Override
	public String findVenderMaxno() {
		String result=mapper.findVenderMaxno(comId); 
		return result;
	}

	@Override
	public List<VenderVO> venderFindAllList(String type, String value) {
		List<VenderVO> result=mapper.venderFindAllList(comId,type,value); 
		return result;
	}

}
