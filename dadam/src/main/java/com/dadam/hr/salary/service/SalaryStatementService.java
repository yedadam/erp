package com.dadam.hr.salary.service;

import com.dadam.hr.salary.service.SalaryStatementVO;
import java.util.List;

/**
 * 급여명세서 Service 인터페이스
 */
public interface SalaryStatementService {
    SalaryStatementVO getSalaryStatementById(Long id);
    int addSalaryStatement(SalaryStatementVO vo);
    int modifySalaryStatement(SalaryStatementVO vo);
    int removeSalaryStatement(Long id);
    List<SalaryStatementVO> getSalaryStatementList(String empId, String comId);
} 