package com.dadam.hr.salary.mapper;

import com.dadam.hr.salary.service.SalaryStatementVO;
import java.util.List;

/**
 * 급여명세서 Mapper
 */
public interface SalaryStatementMapper {
    /** 전체 급여명세서 목록 조회 */
    List<SalaryStatementVO> selectSalaryStatementList(String empId, String comId);
    /** 단일 급여명세서 조회 (SAL_ID, COM_ID) */
    SalaryStatementVO selectSalaryStatementById(java.util.Map<String, Object> param);
    /** 급여명세서 등록 */
    int insertSalaryStatement(SalaryStatementVO vo);
    /** 급여명세서 수정 */
    int updateSalaryStatement(SalaryStatementVO vo);
    /** 급여명세서 삭제 (SAL_ID, COM_ID) */
    int deleteSalaryStatement(java.util.Map<String, Object> param);
} 