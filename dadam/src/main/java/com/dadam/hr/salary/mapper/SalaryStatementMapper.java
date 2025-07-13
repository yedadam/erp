package com.dadam.hr.salary.mapper;

import com.dadam.hr.salary.service.SalaryStatementVO;
import java.util.List;

/**
 * 급여명세서 Mapper
 */
public interface SalaryStatementMapper {
    /** 전체 급여명세서 목록 조회 */
    public List<SalaryStatementVO> selectSalaryStatementList(String empId, String comId);
    public List<SalaryStatementVO> selectSalaryStatementList(java.util.Map<String, Object> param);
    /** 단일 급여명세서 조회 (SAL_ID, COM_ID) */
    public SalaryStatementVO selectSalaryStatementById(java.util.Map<String, Object> param);
    /** 급여명세서 등록 */
    public int insertSalaryStatement(SalaryStatementVO vo);
    /** 급여명세서 수정 */
    public int updateSalaryStatement(SalaryStatementVO vo);
    /** 급여명세서 삭제 (SAL_ID, COM_ID) */
    public int deleteSalaryStatement(java.util.Map<String, Object> param);
    
    /** 사원별 급여항목(EMP_ALLOWANCE) 조회 */
    public List<java.util.Map<String, Object>> selectEmpAllowances(java.util.Map<String, Object> param);
} 