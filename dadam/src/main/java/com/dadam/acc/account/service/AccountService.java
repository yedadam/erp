package com.dadam.acc.account.service;

import java.util.List;

public interface AccountService {
	public List<AccountVO> accFindAll();
	public String codeFind(AccountCodeVO accountCode);
	public void insert(AccountVO acct);
	void saveAll(AccountVO container);
}
