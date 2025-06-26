package com.dadam.acc.credit.service;

import java.util.List;

public interface CreditService {
	public List<CreditVO> creditFindAll();
	public List<CreditVO> creditFindCode(String vdrCode);
}
