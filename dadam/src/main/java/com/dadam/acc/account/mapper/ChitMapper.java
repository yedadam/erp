package com.dadam.acc.account.mapper;

import java.util.List;
import java.util.Map;

import com.dadam.acc.account.service.ChitVO;

public interface ChitMapper {
	public List<ChitVO> chitFindAll();
	public List<Map<String, String>> acctCodeFindByCode(String keyword);
	public List<Map<String, String>> indTypeFindByCode(String keyword);
	public List<Map<String, String>> chitTypeFindByCode(String keyword);
	
	public void insert(ChitVO chit);
	public void update(ChitVO chit);
	public void dalete(String chitCode);
	
	public String generateChitCode();

}