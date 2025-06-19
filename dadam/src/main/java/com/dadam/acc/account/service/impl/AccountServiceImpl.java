package com.dadam.acc.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.acc.account.mapper.AccountMapper;
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

}
