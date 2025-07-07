package com.dadam.acc.credit.service;

import java.util.List;

public interface TransactionsService {
	public List<TransactionsVO> getAll();
	public void add(TransactionsVO vo);
	public void update(TransactionsVO vo);
	void saveAll(TransactionsVO vo);
} 