package com.dadam.acc.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.acc.account.mapper.AccountMapper;
import com.dadam.acc.account.service.AccountCodeVO;
import com.dadam.acc.account.service.AccountService;
import com.dadam.acc.account.service.AccountVO;

//service bean등록
@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired AccountMapper accountMapper;
	
	// 리스트 IMPL
	@Override
	public List<AccountVO> accFindAll() {
		List<AccountVO> result = accountMapper.accFindAll();
		return result;
	}

	@Override
	public String codeFind(AccountCodeVO accountCode) {
		String result = accountMapper.codeFind(accountCode);
		return result;
	}
	
	// 인설트 IMPL
	@Override
	public void insert(AccountVO acct) {
		accountMapper.insert(acct);
	}

	@Override
    public void saveAll(AccountVO container) {
    	 List<AccountVO> rows = container.getCreatedRows();

    	 for (AccountVO acct : rows) {

             // 분류명 기반 코드 조회
             String typeCode = accountMapper.findTypeCodeByName(acct.getAcctType());
             if (typeCode == null) {
                 throw new IllegalArgumentException("❌ 대분류 '" + acct.getAcctType() + "'에 해당하는 코드가 없습니다.");
             }

             String classCode = accountMapper.findClassCodeByName(acct.getAcctClass());
             if (classCode == null) {
                 throw new IllegalArgumentException("❌ 중분류 '" + acct.getAcctClass() + "'에 해당하는 코드가 없습니다.");
             }

             String subclassCode = accountMapper.findSubclassCodeByName(acct.getAcctSubclass());
             if (subclassCode == null) {
                 throw new IllegalArgumentException("❌ 소분류 '" + acct.getAcctSubclass() + "'에 해당하는 코드가 없습니다.");
             }

             // 코드 세팅 및 acct_code 생성
             AccountCodeVO codeVO = new AccountCodeVO();
             codeVO.setTypeCode(typeCode);
             codeVO.setClassCode(classCode);
             codeVO.setSubclassCode(subclassCode);

             String acctCode = accountMapper.codeFind(codeVO);
             acct.setAcctCode(acctCode);
             
             

             // 코드 필드 세팅 (원래 분류명 → 코드로 변환해서 저장)
             acct.setAcctType(typeCode);
             acct.setAcctClass(classCode);
             acct.setAcctSubclass(subclassCode);

             // 저장
             accountMapper.insert(acct);
         }
    	 
    	// 2. 수정
    	 if (container.getUpdatedRows() != null) {
    		 for (AccountVO acct : container.getUpdatedRows()) {
    	         accountMapper.update(acct);
    	     }
    	 }

    	 // 3. 삭제
    	 if (container.getDeletedRows() != null) {
    	     for (AccountVO acct : container.getDeletedRows()) {
    	         accountMapper.delete(acct.getAcctCode());
    	     }
    	 }
    	    
    }



}
