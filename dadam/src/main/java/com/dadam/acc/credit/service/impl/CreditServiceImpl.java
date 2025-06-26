package com.dadam.acc.credit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.acc.credit.mapper.CreditMapper;
import com.dadam.acc.credit.service.CreditService;
import com.dadam.acc.credit.service.CreditVO;

@Service
public class CreditServiceImpl implements CreditService{
	
	@Autowired CreditMapper creditMapper;

	@Override
	public List<CreditVO> creditFindAll() {
		return creditMapper.creditFindAll();
	}

	@Override
	public List<CreditVO> creditFindCode(String vdrCode) {
		List<CreditVO> result = creditMapper.creditFindCode(vdrCode);
		return result;
	}

}
