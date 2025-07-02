package com.dadam.acc.account.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dadam.acc.account.service.ChitVO;

public interface ChitMapper {
	public List<ChitVO> chitFindAll();
	public List<Map<String, String>> acctCodeFindByCode(String keyword);
	public List<Map<String, String>> indTypeFindByCode(String keyword);
	public List<Map<String, String>> chitTypeFindByCode(String keyword);
	
	public void insert(ChitVO chit);
	public void update(ChitVO chit);
	public void delete(ChitVO chit);
	
	public String generateChitCode();
	
	public String getStatusByChitCode(@Param("chitCode") String chitCode);
}