package com.dadam.acc.credit.mapper;

import java.util.List;

import com.dadam.acc.credit.service.TransactionsVO;

public interface TransactionsMapper {
   public List<TransactionsVO> selectAll();
   public TransactionsVO selectById(String txn_hist_code);
   public int insert(TransactionsVO vo);
   public int update(TransactionsVO vo);
   public int delete(String txn_hist_code);
} 