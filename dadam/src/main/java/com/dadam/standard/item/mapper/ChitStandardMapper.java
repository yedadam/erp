package com.dadam.standard.item.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dadam.acc.account.service.ChitVO;
import com.dadam.standard.item.service.MoneyVO;

public interface ChitStandardMapper {
 
	public List<ChitVO> ChitList(Map<String,Object> map);
	
	public List<MoneyVO> moneyList(@Param("type") String type, @Param("value") String value, @Param("comId") String comId);
	
	public List<MoneyVO> adjList(@Param("type") String type, @Param("value") String value, @Param("comId") String comId);
	
	public List<MoneyVO> salaryList(@Param("type") String type, @Param("value") String value, @Param("comId") String comId);
	
	public int chitStandardAdd(ChitVO vo);
	public int chitStandardUpdate(ChitVO vo);
	public int chitStandardDelete(ChitVO vo);
}
