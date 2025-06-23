package com.dadam.acc.account.service;

import java.util.List;

public interface AccountService {
	public String codeFind(AccountCodeVO accountCode);
	public void insert(AccountVO acct);
	public void saveAll(AccountVO accountVO);
	List<AccountVO> accFindAll(); // 
	List<AccountVO> accFindByType(String acctType); // 
}
