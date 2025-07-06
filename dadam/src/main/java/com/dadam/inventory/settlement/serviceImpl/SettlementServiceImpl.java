package com.dadam.inventory.settlement.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.inventory.settlement.mapper.SettlementMapper;
import com.dadam.inventory.settlement.service.SettlementService;
import com.dadam.inventory.settlement.service.SettlementVO;
import com.dadam.security.service.EmployeesVO;
import com.dadam.security.service.LoginUserAuthority;


@Service
public class SettlementServiceImpl implements SettlementService{
	//comName 가져오기
    String comId = "com-101";
    String empId = "e1001";
    public void initAuthInfo() {
        //로그인 객체값 연결
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //로그인 객체 가져오기
        Object principal = auth.getPrincipal();

        if (principal instanceof LoginUserAuthority) {
        	LoginUserAuthority user = (LoginUserAuthority) principal;
            comId = user.getComId();
            empId = user.getUserId();
            System.out.println("회사명: " + comId);
        }
    }
    
    @Autowired
    SettlementMapper mapper;
    
    @Override
    public List<EmployeesVO> settleEmployeesList() {
    	initAuthInfo();
    	List<EmployeesVO> result = mapper.settleEmployeesList(comId);
    	return result;
    }
	
    @Override
    public List<SettlementVO> autoSettlementList() {
    	initAuthInfo();
    	List<SettlementVO> result = mapper.autoSettlementList(comId);
    	return result;
    }
    
    @Override
    @Transactional
    public String settlementAdd(List<SettlementVO> vo) {
    	initAuthInfo();
    	vo.get(0).setComId(comId);
    	vo.get(0).setEmpId(empId);
    	int result = 0;
    	result += mapper.settlementMainAdd(vo.get(0));
    	
    	for(SettlementVO info : vo) {
    		info.setSetCode(vo.get(0).getSetCode());
    		info.setComId(comId);
    		info.setEmpId(empId);
    		result += mapper.settlementDtlAdd(info);
    	}
    	return vo.get(0).getSetCode();
    }
    
    //결재서 등록
    @Override
    public int eleAdd(List<SettlementVO> vo) {
    	initAuthInfo();
    	int result = 0;
    	for(int i=0; i <vo.size(); i++) {
    		if(i==0) {
    		 vo.get(i).setProceedNum(i+1);
    	     vo.get(i).setApproveStatus("ap02");
    	     vo.get(i).setComId(comId);
    	     result += mapper.eleAdd(vo.get(i));
    		}else {
    		 vo.get(i).setProceedNum(i+1);
    		 vo.get(i).setApproveStatus("ap01");
    		 vo.get(i).setComId(comId);
    		 result += mapper.eleAdd(vo.get(i));
    		}
    	}
    	return result;
    }
    
    //결재목록 조회
    @Override
    public  List<SettlementVO> settleList(String type, String value) {
    	initAuthInfo();
    	List<SettlementVO> result = mapper.settleList(type, value, comId);
    	return result;
    }
    
    @Override
    public List<SettlementVO> mineEle() {
    	initAuthInfo();
    	List<SettlementVO> result = mapper.mineEle(comId, empId);
    	return result;
    }
    
    @Override
    public void prcEletronicUpdate(SettlementVO vo) {
    	initAuthInfo();
    	vo.setAppId(empId);
    	vo.setComId(comId);
    	System.out.println(vo.getPkCode());
    	System.out.println(vo.getPkCode());
    	System.out.println(vo.getPkCode());
    	System.out.println(vo.getApproveStatus());
    	System.out.println(vo.getElecCode());
    	mapper.prcEletronicUpdate(vo);
    }
}
