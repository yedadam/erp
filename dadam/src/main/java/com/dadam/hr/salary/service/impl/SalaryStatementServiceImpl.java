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
        SalaryStatementVO vo = salaryStatementMapper.selectSalaryStatementById(param);
        
        // EMP_ALLOWANCE에서 사원별 급여항목 조회하여 추가
        if (vo != null) {
            try {
                List<java.util.Map<String, Object>> empAllowances = salaryStatementMapper.selectEmpAllowances(param);
                vo.setEmpAllowances(empAllowances);
            } catch (Exception e) {
                // EMP_ALLOWANCE 조회 실패 시에도 기본 정보는 반환
                System.err.println("사원별 급여항목 조회 실패: " + e.getMessage());
            }
        }
        
        return vo;
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
    @Override
    public boolean existsBySalId(String salId) {
        return salaryStatementMapper.existsBySalId(salId) > 0;
    }
} 