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
	    return accountMapper.accFindAll();
	}

	@Override
	public List<AccountVO> accFindByType(String acctType) {
	    return accountMapper.accFindByType(acctType); // ✔️ 반드시 Mapper에 구현되어야 함
	}

	@Override
	public String codeFind(AccountCodeVO accountCode) {
		String result = accountMapper.codeFind(accountCode);
		return result;
	}

	@Override
	public List<String> getAcctTypes() {
	    return accountMapper.selectAcctTypes();
	}

	@Override
	public List<String> getAcctClasses(String acctCode) {
	    return accountMapper.selectAcctClasses(acctCode);
	}

	@Override
	public List<String> getAcctSubClasses(String classCode) {
		return accountMapper.selectAcctSubClasses(classCode);
	}
	
	// 인설트 IMPL
	@Override
	public void insert(AccountVO acct) {
		accountMapper.insert(acct);
	}
	
	// 대,중,소 분류 입력 데이터 조회(유효성검사)
	// 대중소 분류 테이블에서 코드 요청 -> 계정과목 코드 생성
	@Override
    public void saveAll(AccountVO account) {
    	 List<AccountVO> rows = account.getCreatedRows();
    	 

    	 for (AccountVO acct : rows) {
//    		    if (acct.getNote() == null) acct.setNote("");
//    		    if (acct.getAcctSubclass() == null) acct.setAcctSubclass("");
//    		    if (acct.getAcctYn() == null) acct.setAcctYn("Y"); // 기본값 설정도 가능	
    		    
             // 분류명 기반 코드 조회(대분류)
             String typeCode = accountMapper.findTypeCodeByName(acct.getAcctType());
             if (typeCode == null) {
                 throw new IllegalArgumentException("대분류 '" + acct.getAcctType() + "'에 해당하는 코드가 없습니다.");
             }
             
             // 중분류 코드 유효성검사 후 생성 
             String classCode = accountMapper.findClassCodeByName(acct.getAcctClass());
             if (classCode == null) {
                 throw new IllegalArgumentException("중분류 '" + acct.getAcctClass() + "'에 해당하는 코드가 없습니다.");
             }
             
             // 소분류 초기값 null
             String subclassCode = null;
             // null이면 허용 / db에 값 있으면 유효성 검사
             String subclassName = acct.getAcctSubclass();

             // 1. 소분류 값이 있으면 → 코드 조회 + 유효성 검사
             if (subclassName != null && !subclassName.isBlank()) {
                 subclassCode = accountMapper.findSubclassCodeByName(subclassName);

                 if (subclassCode == null) {
                     throw new IllegalArgumentException("소분류 '" + subclassName + "'에 해당하는 코드가 없습니다.");
                 }
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
    	 if (account.getUpdatedRows() != null) {
    		 for (AccountVO acct : account.getUpdatedRows()) {
    	         accountMapper.update(acct);
    	     }
    	 }

    	 // 3. 삭제
    	 if (account.getDeletedRows() != null) {
    	     for (AccountVO acct : account.getDeletedRows()) {
    	         accountMapper.delete(acct.getAcctCode());
    	     }
    	 }
    	    
    }







}
