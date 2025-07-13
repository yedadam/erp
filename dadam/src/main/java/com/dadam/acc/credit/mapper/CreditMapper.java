package com.dadam.acc.credit.mapper;

import java.util.List;
import java.util.Map;

import com.dadam.acc.credit.service.CreditVO;

public interface CreditMapper {
	public List<CreditVO> creditFindAll(String comId, String type);
	public List<CreditVO> creditFindCode(String vdrCode, String comId);
	public List<CreditVO> creditSearch(Map<String, Object> params);
	public List<CreditVO> detailFindSupplier(String vdrCode, String comId);
	List<Map<String, Object>> purchaseSummary(String vdrCode, String comId);
	List<Map<String, Object>> purchaseDetail(String purOrdCode);
}
