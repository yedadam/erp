package com.dadam.hr.salary.service.impl;

import com.dadam.hr.salary.service.SalaryStatementService;
import com.dadam.hr.salary.mapper.SalaryStatementMapper;
import com.dadam.hr.salary.service.SalaryStatementVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 급여명세서 Service 구현체
 */
@Service
public class SalaryStatementServiceImpl implements SalaryStatementService {
    @Autowired
    private SalaryStatementMapper salaryStatementMapper;

    @Override
    public List<SalaryStatementVO> getSalaryStatementList(String empId, String comId) {
        return salaryStatementMapper.selectSalaryStatementList(empId, comId);
    }
    @Override
    public List<SalaryStatementVO> getSalaryStatementList(java.util.Map<String, Object> param) {
        return salaryStatementMapper.selectSalaryStatementList(param);
    }
    @Override
    public SalaryStatementVO getSalaryStatementById(java.util.Map<String, Object> param) {
        return salaryStatementMapper.selectSalaryStatementById(param);
    }
    @Override
    public int addSalaryStatement(SalaryStatementVO vo) {
        return salaryStatementMapper.insertSalaryStatement(vo);
    }
    @Override
    public int modifySalaryStatement(SalaryStatementVO vo) {
        return salaryStatementMapper.updateSalaryStatement(vo);
    }
    @Override
    public int removeSalaryStatement(java.util.Map<String, Object> param) {
        return salaryStatementMapper.deleteSalaryStatement(param);
    }
} 