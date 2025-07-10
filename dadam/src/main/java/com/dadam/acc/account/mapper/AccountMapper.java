package com.dadam.acc.account.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dadam.acc.account.service.AccountCodeVO;
import com.dadam.acc.account.service.AccountVO;

public interface AccountMapper {
	public List<AccountVO> accFindAll(String comId);
	public List<AccountVO> accFindByType(@Param("acctType") String acctType, @Param("comId") String comId);
	public String codeFind(AccountCodeVO accountCode);
	
	public void insert(AccountVO acct);
	public String findTypeCodeByName(@Param("typeName") String typeName, @Param("comId") String comId);
	public String findClassCodeByName(@Param("className") String className, @Param("comId") String comId);
	public String findSubclassCodeByName(@Param("subclassName") String subclassName, @Param("comId") String comId);
	
	public List<String> selectAcctTypes(@Param("comId") String comId);
	public List<String> selectAcctClasses(@Param("acctType") String typeCode, @Param("comId") String comId);
	public List<String> selectAcctSubClasses(@Param("acctClass") String classCode, @Param("comId") String comId);
	
	public void update(AccountVO acct);
	public void delete(String acctCode);
	
	public List<AccountVO> accountSearch(Map<String, Object> params);
	public List<Map<String, String>> accountAutoComplete(@Param("type") String type, @Param("value") String value, @Param("comId") String comId);
}
