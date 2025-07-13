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

	    // 요청된 acctType 값 보존
	    String originalAcctType = (String) params.get("acctType");

	    // acctType을 '1,2,3,4,5'로 바꿔서 전체 다 불러오기 (내부 계산용)
	    params.put("acctType", "1,2,3,4,5");

	    List<BalanceSheetDTO> list = balanceSheetMapper.selectBalanceSheet(params);

	    // 수익·비용 → 당기손이익 계산
	    long totalRevenue = list.stream()
	        .filter(d -> "4".equals(d.getAcctType()))
	        .mapToLong(d -> d.getBalance() != null ? d.getBalance() : 0)
	        .sum();

	    long totalExpense = list.stream()
	        .filter(d -> "5".equals(d.getAcctType()))
	        .mapToLong(d -> d.getBalance() != null ? d.getBalance() : 0)
	        .sum();

	    long netIncome = totalRevenue - totalExpense;

	    // 📌 당기손이익 추가 (자본 계정으로 표시)
	    BalanceSheetDTO netIncomeRow = new BalanceSheetDTO();
	    netIncomeRow.setAcctType("3");
	    netIncomeRow.setAcctTypeName("당기손이익");
	    netIncomeRow.setName(""); // 계정명 없음
	    netIncomeRow.setBalance(netIncome);
	    list.add(netIncomeRow);

	    // 📌 요청한 acctType이 있었던 경우 필터링해서 다시 리턴
	    if (originalAcctType != null && !originalAcctType.isBlank()) {
	        String[] types = originalAcctType.split(",");
	        return list.stream()
	                .filter(d -> d.getAcctType() != null && List.of(types).contains(d.getAcctType()))
	                .toList();
	    }

	    return list;
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
