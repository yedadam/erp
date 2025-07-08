package com.dadam.acc.credit.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TransactionsService {
	public List<TransactionsVO> getAll(String comId);
	public void add(TransactionsVO vo);
} 