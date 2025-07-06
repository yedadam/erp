package com.dadam.acc.account.service;

import java.util.List;
import java.util.Map;

public interface BalanceSheetService {
	public List<BalanceSheetDTO> selectBalanceSheet(Map<String, Object> params);
}
