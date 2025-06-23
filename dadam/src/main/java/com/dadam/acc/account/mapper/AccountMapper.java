package com.dadam.acc.account.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dadam.acc.account.service.AccountCodeVO;
import com.dadam.acc.account.service.AccountVO;

public interface AccountMapper {
	public List<AccountVO> accFindAll();
	public List<AccountVO> accFindByType(@Param("acctType") String acctType);
	public String codeFind(AccountCodeVO accountCode);
	
	public void insert(AccountVO acct);
	public String findTypeCodeByName(String typeName);
	public String findClassCodeByName(String className);
	public String findSubclassCodeByName(String subclassName);
	
    List<String> selectAcctTypes();

    List<String> selectAcctClasses(@Param("typeCode") String typeCode);

    List<String> selectAcctSubclasses(@Param("typeCode") String typeCode, @Param("classCode") String classCode);
	
	public void update(AccountVO acct);
	public void delete(String acctCode);

}
