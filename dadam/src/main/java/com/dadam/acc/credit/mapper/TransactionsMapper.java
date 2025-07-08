package com.dadam.acc.credit.mapper;

import java.util.List;

import com.dadam.acc.credit.service.TransactionsVO;

public interface TransactionsMapper {
   public List<TransactionsVO> selectAll(String comId);
   public int insert(TransactionsVO vo);
} 