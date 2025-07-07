package com.dadam.hr.salary.mapper;

import com.dadam.hr.salary.service.SalaryStatementVO;
import java.util.List;

/**
 * 급여명세서 Mapper
 */
public interface SalaryStatementMapper {
    /** 전체 급여명세서 목록 조회 */
    List<SalaryStatementVO> selectSalaryStatementList(String empId, String comId);
    /** 단일 급여명세서 조회 */
    SalaryStatementVO selectSalaryStatementById(Long id);
    /** 급여명세서 등록 */
    int insertSalaryStatement(SalaryStatementVO vo);
    /** 급여명세서 수정 */
    int updateSalaryStatement(SalaryStatementVO vo);
    /** 급여명세서 삭제 */
    int deleteSalaryStatement(Long id);
} 