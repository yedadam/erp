package com.dadam.hr.salary.service;

import java.util.List;
import java.util.Map;

/**
 * 급여 계산 서비스 인터페이스
 * - 근태 연동 급여 계산
 * - 지각/조퇴 공제 자동 계산
 * - 연차수당/연장근무수당 자동 계산
 * - 급여 지급 처리
 */
public interface SalaryCalculationService {
    
    /**
     * 급여 계산 (근태 연동)
     * @param empId - 사원번호
     * @param payMonth - 지급년월 (YYYY-MM)
     * @param comId - 회사ID
     * @return 계산된 급여 정보
     */
    SalaryStatementVO calculateSalary(String empId, String payMonth, String comId);
    
    /**
     * 기본급 계산
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 기본급
     */
    double calculateBaseSalary(String empId, String comId);
    
    /**
     * 근무일수 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @param comId - 회사ID
     * @return 근무일수 정보
     */
    Map<String, Object> calculateWorkDays(String empId, String payMonth, String comId);
    
    /**
     * 지각/조퇴 공제 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @param comId - 회사ID
     * @return 공제 정보
     */
    Map<String, Object> calculateLateEarlyDeduction(String empId, String payMonth, String comId);
    
    /**
     * 연차수당 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @param comId - 회사ID
     * @return 연차수당
     */
    double calculateAnnualLeavePay(String empId, String payMonth, String comId);
    
    /**
     * 연장근무수당 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @param comId - 회사ID
     * @return 연장근무수당
     */
    double calculateOvertimePay(String empId, String payMonth, String comId);
    
    /**
     * 급여 지급 처리
     * @param statementId - 급여명세서 ID
     * @param comId - 회사ID
     * @return 지급 결과
     */
    String processSalaryPayment(Long statementId, String comId);
    
    /**
     * 급여 지급 승인/반려
     * @param paymentId - 지급 ID
     * @param status - 상태 (APPROVED/REJECTED)
     * @param approverId - 승인자 ID
     * @param rejectReason - 반려사유
     * @return 처리 결과
     */
    String approveSalaryPayment(Long paymentId, String status, String approverId, String rejectReason);
    
    /**
     * 급여 통계 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @param payMonth - 지급년월
     * @return 통계 정보
     */
    Map<String, Object> getSalaryStatistics(String comId, String deptCode, String payMonth);
    
    /**
     * 급여 지급 이력 조회
     * @param comId - 회사ID
     * @param empId - 사원번호 (선택사항)
     * @param fromMonth - 시작년월
     * @param toMonth - 종료년월
     * @return 지급 이력
     */
    List<Map<String, Object>> getSalaryPaymentHistory(String comId, String empId, String fromMonth, String toMonth);
} 