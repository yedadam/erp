package com.dadam.acc.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.acc.account.mapper.AccountMapper;
import com.dadam.acc.account.service.AccountCodeVO;
import com.dadam.acc.account.service.AccountService;
import com.dadam.acc.account.service.AccountVO;

//service bean등록
@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired AccountMapper accountMapper;

	@Override
	public List<AccountVO> accFindAll() {
		List<AccountVO> result = accountMapper.accFindAll();
		return result;
	}

	@Override
	public String codeFind(AccountCodeVO accountCode) {
		String result = accountMapper.codeFind(accountCode);
		return result;
	}

	@Override
	public void insert(AccountVO acct) {
		accountMapper.insert(acct);
		
	}



}
