package com.dadam.acc.account.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dadam.acc.account.service.ChitVO;

public interface ChitMapper {
	//전표 조회, 계정과목 조회
	public List<ChitVO> chitFindAll(String comId);
	public List<Map<String, String>> acctCodeFindByCode(String keyword, String comId);
	public List<Map<String, String>> indTypeFindByCode(String keyword, String comId);
	public List<Map<String, String>> chtTypeFindByCode(String keyword, String comId);

	
	
	//전표-분개 등록
	public void insert(ChitVO chit);
	public void update(ChitVO chit);
	public void delete(ChitVO chit);
	public String generateChitCode();
	
	public String getStatusByChitCode(@Param("chitCode") String chitCode, @Param("comId") String comId);

	//전표 결제
	public void modifySta(String chitCode,String comId);
	public void modifyVarBal(String articleCode, int totPrice,String comId);
	
	public String getTtypeByChitCode(@Param("chitCode") String chitCode, @Param("comId") String comId);
	
	//분개 자동 입력
	public List<ChitVO> selectAutoChitRules(String comId);
	public void mergeAutoChitRule(ChitVO vo);
	public String findTypeCodeByName(String name);     // 거래유형 (ex. 매입 → cht01)
	public String findIndTypeCodeByName(String name);  // 차/대변 (ex. 차변 → itp01)
	public String findAcctCodeByName(String name);     // 계정과목 (ex. 현금 → 110101)
	public void insertRule(ChitVO rule);
	public void updateRule(ChitVO rule);
	public void deleteRule(String ruleId);
	public String getNextRuleId(String comId);
	
	public List<Map<String, Object>> selectAutoRules(@Param("chitType") String chitType, @Param("comId") String comId);

}