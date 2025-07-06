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
	
    
    
	@Autowired BalanceSheetMapper balanceSheetMapper;
	
	

	@Override
	public List<BalanceSheetDTO> selectBalanceSheet(Map<String, Object> params) {
		return balanceSheetMapper.selectBalanceSheet(params);
	}
	
}
