package com.dadam.hr.salary.service;

import com.dadam.hr.salary.service.SalaryStatementVO;
import java.util.List;

/**
 * 급여명세서 Service 인터페이스
 */
public interface SalaryStatementService {
    SalaryStatementVO getSalaryStatementById(java.util.Map<String, Object> param);
    int addSalaryStatement(SalaryStatementVO vo);
    int modifySalaryStatement(SalaryStatementVO vo);
    int removeSalaryStatement(java.util.Map<String, Object> param);
    List<SalaryStatementVO> getSalaryStatementList(String empId, String comId);
    List<SalaryStatementVO> getSalaryStatementList(java.util.Map<String, Object> param);
    /** salId 중복 체크 */
    boolean existsBySalId(String salId);
} 