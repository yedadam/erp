package com.dadam.acc.credit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dadam.acc.credit.mapper.TransactionsMapper;
import com.dadam.acc.credit.service.TransactionsService;
import com.dadam.acc.credit.service.TransactionsVO;
import com.dadam.security.service.LoginUserAuthority;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    

    @Autowired
    private TransactionsMapper transactionsMapper;

    @Override
    public List<TransactionsVO> getAll() {
        return transactionsMapper.selectAll();
    }

    private String getComIdFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof LoginUserAuthority) {
            return ((LoginUserAuthority) principal).getComId();
        }
        return "com-101";
    }

    @Override
    public void add(TransactionsVO vo) {
        vo.setComId(getComIdFromAuth());
        transactionsMapper.insert(vo);
    }

    @Override
    public void update(TransactionsVO vo) {
        vo.setComId(getComIdFromAuth());
        transactionsMapper.update(vo);
    }


    @Override
    public void saveAll(TransactionsVO vo) {

    }
} 