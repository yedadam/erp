package com.dadam.acc.credit.mapper;

import java.util.List;

import com.dadam.acc.credit.service.TransactionsVO;
import com.dadam.standard.vender.service.VenderVO;

public interface TransactionsMapper {
   public List<TransactionsVO> selectAll(String comId);
   public int insert(TransactionsVO vo);
   List<VenderVO> findVenders(String comId, String type, String value);
} 