package com.dadam.hr.salary.service;

import java.util.List;

/**
 * 급여명세 리스트 관리 Service 인터페이스
 * 
 * @author 팀장
 * @since 2024-01-01
 */
public interface SalaryListService {
    
    /**
     * 급여명세 목록 조회
     * 
     * @param searchVO 검색 조건
     * @return 급여명세 목록
     */
    List<SalaryListVO> getSalaryList(SalaryListVO searchVO);
    
    /**
     * 급여명세 일괄승인
     * 
     * @param salaryIds 승인할 급여명세 ID 목록
     * @return 처리된 건수
     */
    int approveSalaryBatch(List<String> salaryIds);
    
    /**
     * 급여명세 일괄지급
     * 
     * @param salaryIds 지급할 급여명세 ID 목록
     * @return 처리된 건수
     */
    int paySalaryBatch(List<String> salaryIds);
    
    /**
     * 급여명세 개별승인
     * 
     * @param salaryId 승인할 급여명세 ID
     * @return 처리된 건수
     */
    int approveSalary(String salaryId);
    
    /**
     * 급여명세 개별지급
     * 
     * @param salaryId 지급할 급여명세 ID
     * @return 처리된 건수
     */
    int paySalary(String salaryId);
    
    /**
     * 급여명세 삭제
     * 
     * @param salaryId 삭제할 급여명세 ID
     * @return 처리된 건수
     */
    int deleteSalary(String salaryId);
} 