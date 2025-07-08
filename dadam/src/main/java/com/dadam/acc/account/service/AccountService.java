package com.dadam.acc.account.service;

import java.util.List;
import java.util.Map;

public interface AccountService {
	public String codeFind(AccountCodeVO accountCode);
	public void insert(AccountVO acct);
	public void saveAll(AccountVO accountVO);
	public List<AccountVO> accFindAll();
	public List<AccountVO> accFindByType(String acctType, String comId);
	public List<String> getAcctTypes();
	public List<String> getAcctClasses(String typeCode);
	public List<String> getAcctSubClasses(String classCode);
	public List<AccountVO> accountSearch(Map<String, Object> params);
}
