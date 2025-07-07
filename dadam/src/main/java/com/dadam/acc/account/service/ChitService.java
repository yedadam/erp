package com.dadam.acc.account.service;

import java.util.List;
import java.util.Map;

public interface ChitService {
	public List<ChitVO> chitFindAll();
	public List<Map<String, String>> acctCodeFindByCode(String keyword);
	public List<Map<String, String>> indTypeFindByCode(String keyword);
	public List<Map<String, String>> chtTypeFindByCode(String keyword);
	
	public void saveAll(ChitVO chit);
	
	public void modifyChitPay(String chitCode, String articleCode, int totPrice);

	public List<ChitVO> getAutoChitRules(String comId);

	public void saveAllRules(List<ChitVO> rules);
	
	public List<Map<String, Object>> getAutoRules(String chitType, String comId);

	// 자동분개 규칙 일괄 저장 (Account 방식)
	void saveAllRulesSeparated(List<ChitVO> createdRows, List<ChitVO> updatedRows, List<ChitVO> deletedRows);
}
