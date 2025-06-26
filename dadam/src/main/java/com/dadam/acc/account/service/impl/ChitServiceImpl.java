package com.dadam.acc.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.acc.account.mapper.ChitMapper;
import com.dadam.acc.account.service.ChitService;
import com.dadam.acc.account.service.ChitVO;

@Service
public class ChitServiceImpl implements ChitService{
	
	@Autowired ChitMapper chitMapper;
	
	@Override
	public List<ChitVO> chitFindAll() {
		return chitMapper.chitFindAll();
	}

}
