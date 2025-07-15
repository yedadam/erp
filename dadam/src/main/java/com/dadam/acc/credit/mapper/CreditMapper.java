package com.dadam.acc.credit.mapper;

import java.util.List;

import com.dadam.acc.credit.service.CreditVO;

public interface CreditMapper {
	public List<CreditVO> creditFindAll(String comId);
	public List<CreditVO> creditFindCode(String vdrCode, String comId);

}
