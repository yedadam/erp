package com.dadam.acc.account.service.impl;

import java.util.List;

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

    @Autowired
    private AccountMapper accountMapper;



    private String getComIdFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof LoginUserAuthority) {
            String comId = ((LoginUserAuthority) principal).getComId();
            return comId;
        }
        return "com-101";
    }

    @Override
    public List<AccountVO> accFindAll() {
        String comId = getComIdFromAuth();
        return accountMapper.accFindAll(comId);
    }

    @Override
    public List<AccountVO> accFindByType(String acctType, String unused) {
        String comId = getComIdFromAuth();
        return accountMapper.accFindByType(acctType, comId);
    }

    @Override
    public String codeFind(AccountCodeVO accountCode) {
        return accountMapper.codeFind(accountCode);
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

    @Override
    public void insert(AccountVO acct) {
        String comId = getComIdFromAuth();
        acct.setComId(comId);
        accountMapper.insert(acct);
    }

    @Override
    public void saveAll(AccountVO account) {
        String comId = getComIdFromAuth();

        List<AccountVO> rows = account.getCreatedRows();
        if (rows != null) {
            for (AccountVO acct : rows) {
                String typeCode = accountMapper.findTypeCodeByName(acct.getAcctType());
                if (typeCode == null) {
                    throw new IllegalArgumentException("대분류 '" + acct.getAcctType() + "'에 해당하는 코드가 없습니다.");
                }

                String classCode = accountMapper.findClassCodeByName(acct.getAcctClass());
                if (classCode == null) {
                    throw new IllegalArgumentException("중분류 '" + acct.getAcctClass() + "'에 해당하는 코드가 없습니다.");
                }

                String subclassCode = null;
                String subclassName = acct.getAcctSubclass();
                if (subclassName != null && !subclassName.isBlank()) {
                    subclassCode = accountMapper.findSubclassCodeByName(subclassName);
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
                acct.setComId(comId);
                accountMapper.update(acct);
            }
        }

        if (account.getDeletedRows() != null) {
            for (AccountVO acct : account.getDeletedRows()) {
                acct.setComId(comId);
                accountMapper.delete(acct.getAcctCode());
            }
        }
    }
}
