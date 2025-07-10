package com.dadam.acc.account.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dadam.acc.account.mapper.AccountMapper;
import com.dadam.acc.account.service.AccountCodeVO;
import com.dadam.acc.account.service.AccountService;
import com.dadam.acc.account.service.AccountVO;
import com.dadam.security.service.LoginUserAuthority;

@Service
public class AccountServiceImpl implements AccountService {

	
    //comName 가져오기
    String comId = "com-101";
    public void initAuthInfo() {
        //로그인 객체값 연결
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //로그인 객체 가져오기
        Object principal = auth.getPrincipal();

        if (principal instanceof LoginUserAuthority) {
        	LoginUserAuthority user = (LoginUserAuthority) principal;
            comId = user.getComId();
            System.out.println("회사명: " + comId);
        }
    }
	
	
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<AccountVO> accFindAll() {
        initAuthInfo();
        return accountMapper.accFindAll(comId);
    }

    @Override
    public List<AccountVO> accFindByType(String acctType, String unused) {
        initAuthInfo();
        return accountMapper.accFindByType(acctType, comId);
    }

    @Override
    public String codeFind(AccountCodeVO accountCode) {
    	initAuthInfo();
        return accountMapper.codeFind(accountCode);
    }

    @Override
    public List<String> getAcctTypes() {
    	initAuthInfo();
        return accountMapper.selectAcctTypes(comId);
    }

    @Override
    public List<String> getAcctClasses(String acctType) {
    	initAuthInfo();
        return accountMapper.selectAcctClasses(acctType, comId);
    }

    @Override
    public List<String> getAcctSubClasses(String classCode) {
    	initAuthInfo();
        return accountMapper.selectAcctSubClasses(classCode, comId);
    }

    @Override
    public void insert(AccountVO acct) {
        initAuthInfo();
        acct.setComId(comId);
        accountMapper.insert(acct);
    }

    @Override
    public void saveAll(AccountVO account) {
        initAuthInfo();

        List<AccountVO> rows = account.getCreatedRows();
        if (rows != null) {
            for (AccountVO acct : rows) {
                String typeCode = accountMapper.findTypeCodeByName(acct.getAcctType(), comId);
                if (typeCode == null) {
                    throw new IllegalArgumentException("대분류 '" + acct.getAcctType() + "'에 해당하는 코드가 없습니다.");
                }

                String classCode = accountMapper.findClassCodeByName(acct.getAcctClass(), comId);
                if (classCode == null) {
                    throw new IllegalArgumentException("중분류 '" + acct.getAcctClass() + "'에 해당하는 코드가 없습니다.");
                }

                String subclassCode = null;
                String subclassName = acct.getAcctSubclass();
                if (subclassName != null && !subclassName.isBlank()) {
                    subclassCode = accountMapper.findSubclassCodeByName(subclassName, comId);
                    if (subclassCode == null) {
                        throw new IllegalArgumentException("소분류 '" + subclassName + "'에 해당하는 코드가 없습니다.");
                    }
                }

                AccountCodeVO codeVO = new AccountCodeVO();
                codeVO.setTypeCode(typeCode);
                codeVO.setClassCode(classCode);
                codeVO.setSubclassCode(subclassCode);

                String acctCode = accountMapper.codeFind(codeVO);
                acct.setAcctCode(acctCode);

                acct.setAcctType(typeCode);
                acct.setAcctClass(classCode);
                acct.setAcctSubclass(subclassCode);

                acct.setComId(comId);

                accountMapper.insert(acct);
            }
        }

        if (account.getUpdatedRows() != null) {
            for (AccountVO acct : account.getUpdatedRows()) {
            	initAuthInfo();
                accountMapper.update(acct);
            }
        }

        if (account.getDeletedRows() != null) {
            for (AccountVO acct : account.getDeletedRows()) {
            	initAuthInfo();
                accountMapper.delete(acct.getAcctCode());
            }
        }
    }

    @Override
    public List<AccountVO> accountSearch(Map<String, Object> params) {
        initAuthInfo();
        params.put("comId", comId);
        return accountMapper.accountSearch(params);
    }

    @Override
    public List<Map<String, String>> accountAutoComplete(String type, String value) {
        initAuthInfo();
        return accountMapper.accountAutoComplete(type, value, comId);
    }
}
