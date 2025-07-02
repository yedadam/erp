package com.dadam.standard.item.service;

import java.util.List;

import com.dadam.acc.account.service.ChitVO;
import com.dadam.common.service.GridData;


public interface ChitStandardService {
	public List<ChitVO> chitList(String type, String value);
	
	public List<MoneyVO> moneyList(String type, String value);
	
	public List<MoneyVO> adjList(String type, String value);
	
	public List<MoneyVO> salaryList(String type, String value);
	
	public int chitUpdate(GridData<ChitVO> vo);
}
