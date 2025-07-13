package com.dadam.acc.credit.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CreditService {
	public List<CreditVO> creditFindAll(@Param("comId") String comId, @Param("type") String type);
	public List<CreditVO> creditFindCode(@Param("vdrCode") String vdrCode, @Param("comId") String comId);
	public List<CreditVO> creditSearch(Map<String, Object> params);
	public List<CreditVO> detailFindSupplier(String vdrCode, String comId);
	List<Map<String, Object>> purchaseSummary(String vdrCode, String comId);
	List<Map<String, Object>> purchaseDetail(String purOrdCode);
}
