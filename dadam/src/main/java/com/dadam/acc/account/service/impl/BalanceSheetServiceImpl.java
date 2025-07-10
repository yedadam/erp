package com.dadam.acc.account.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dadam.acc.account.mapper.BalanceSheetMapper;
import com.dadam.acc.account.service.BalanceSheetDTO;
import com.dadam.acc.account.service.BalanceSheetService;
import com.dadam.security.service.LoginUserAuthority;

@Service
public class BalanceSheetServiceImpl implements BalanceSheetService{
	
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
    
	@Autowired BalanceSheetMapper balanceSheetMapper;
	
	

	@Override
	public List<BalanceSheetDTO> selectBalanceSheet(Map<String, Object> params) {
		initAuthInfo();
		params.put("comId", comId); 
		return balanceSheetMapper.selectBalanceSheet(params);
	}



	@Override
	public Map<String, Object> getNetIncome(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	public Map<String, Object> getNetIncome(Map<String, Object> param) {
//	    Map<String, Object> incomeParam = new HashMap<>(param);
//	    incomeParam.put("acctType", "4,5"); // 수익(4), 비용(5)
//
//	    List<BalanceSheetDTO> incomeList = balanceSheetMapper.selectBalanceSheet(incomeParam);
//
//	    int revenue = incomeList.stream()
//	        .filter(d -> "4".equals(d.getAcctType()))
//	        .mapToInt(BalanceSheetDTO::getBalance)
//	        .sum();
//
//	    int expense = incomeList.stream()
//	        .filter(d -> "5".equals(d.getAcctType()))
//	        .mapToInt(BalanceSheetDTO::getBalance)
//	        .sum();
//
//	    int netIncome = revenue - expense;
//
//	    Map<String, Object> result = new HashMap<>();
//	    result.put("revenue", revenue);
//	    result.put("expense", expense);
//	    result.put("netIncome", netIncome);
//
//	    return result;
//	}

}
