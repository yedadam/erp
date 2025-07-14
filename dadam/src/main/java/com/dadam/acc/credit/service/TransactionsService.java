package com.dadam.acc.credit.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.dadam.standard.vender.service.VenderVO;

public interface TransactionsService {
	public List<TransactionsVO> getAll(String comId);
	public void add(TransactionsVO vo);
	public List<VenderVO> findVenders(String comId, String type, String value);
} 