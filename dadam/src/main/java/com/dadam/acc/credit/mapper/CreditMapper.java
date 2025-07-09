package com.dadam.acc.credit.mapper;

import java.util.List;
import java.util.Map;

import com.dadam.acc.credit.service.CreditVO;

public interface CreditMapper {
	public List<CreditVO> creditFindAll(String comId);
	public List<CreditVO> creditFindCode(String vdrCode, String comId);
	public List<CreditVO> creditSearch(Map<String, Object> params);
}
