package com.dadam.hr.emp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.hr.emp.mapper.EmpMapper;
import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<EmpVO> findEmpList(String keyword, String status) {
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("keyword", keyword);
        param.put("status", status);
        return empMapper.findEmpList(param);
    }

    @Override
    public EmpVO findEmpDetail(String empId) {
        return empMapper.findEmpDetail(empId);
    }

    @Override
    public int insertEmp(EmpVO empVO) {
        // TODO: 비밀번호 암호화 등 추가 비즈니스 로직 필요
        return empMapper.insertEmp(empVO);
    }

    @Override
    public int updateEmp(EmpVO empVO) {
        return empMapper.updateEmp(empVO);
    }

    @Override
    public int deleteEmp(String empId) {
        return empMapper.deleteEmp(empId);
    }

    @Override
    public String getMaxEmpId() {
        return empMapper.getMaxEmpId();
    }
} 