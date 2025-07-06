package com.dadam.acc.account.mapper;

import java.util.List;
import java.util.Map;

import com.dadam.acc.account.service.BalanceSheetDTO;

public interface BalanceSheetMapper {
	public List<BalanceSheetDTO> selectBalanceSheet(Map<String, Object> param);
}
