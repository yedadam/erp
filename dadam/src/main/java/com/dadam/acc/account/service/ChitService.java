package com.dadam.acc.account.service;

import java.util.List;
import java.util.Map;

public interface ChitService {
	public List<ChitVO> chitFindAll();
	public List<Map<String, String>> acctCodeFindByCode(String keyword);
	public List<Map<String, String>> indTypeFindByCode(String keyword);
	public List<Map<String, String>> chitTypeFindByCode(String keyword);
	
	public void saveAll(ChitVO chit);
}
