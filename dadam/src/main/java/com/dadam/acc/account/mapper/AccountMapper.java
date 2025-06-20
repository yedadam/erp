package com.dadam.acc.account.mapper;

import java.util.List;

import com.dadam.acc.account.service.AccountCodeVO;
import com.dadam.acc.account.service.AccountVO;

public interface AccountMapper {
	public List<AccountVO> accFindAll();
	public String codeFind(AccountCodeVO accountCode);
	public void insert(AccountVO acct);

}
