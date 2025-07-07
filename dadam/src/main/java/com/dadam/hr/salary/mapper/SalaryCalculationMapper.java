package com.dadam.hr.salary.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 급여 계산 매퍼 인터페이스
 * - 근태 연동 급여 계산
 * - 지각/조퇴 공제 계산
 * - 연차수당/연장근무수당 계산
 * - 급여 지급 처리
 */
@Mapper
public interface SalaryCalculationMapper {
    
    /**
     * 사원 기본급 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 기본급
     */
    double getBaseSalary(@Param("empId") String empId, @Param("comId") String comId);
    
    /**
     * 근무일수 계산 (출근일수, 지각일수, 조퇴일수, 결근일수)
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @param comId - 회사ID
     * @return 근무일수 정보
     */
    Map<String, Object> getWorkDays(@Param("empId") String empId, @Param("payMonth") String payMonth, @Param("comId") String comId);
    
    /**
     * 지각/조퇴 공제 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @param comId - 회사ID
     * @return 공제 정보
     */
    Map<String, Object> getLateEarlyDeduction(@Param("empId") String empId, @Param("payMonth") String payMonth, @Param("comId") String comId);
    
    /**
     * 연차수당 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @param comId - 회사ID
     * @return 연차수당
     */
    double getAnnualLeavePay(@Param("empId") String empId, @Param("payMonth") String payMonth, @Param("comId") String comId);
    
    /**
     * 연장근무수당 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @param comId - 회사ID
     * @return 연장근무수당
     */
    double getOvertimePay(@Param("empId") String empId, @Param("payMonth") String payMonth, @Param("comId") String comId);
    
    /**
     * 급여 지급 등록
     * @param payment - 급여 지급 정보
     * @return 등록 결과
     */
    int insertSalaryPayment(Map<String, Object> payment);
    
    /**
     * 급여 지급 수정
     * @param payment - 급여 지급 정보
     * @return 수정 결과
     */
    int updateSalaryPayment(Map<String, Object> payment);
    
    /**
     * 급여 지급 상세 조회
     * @param paymentId - 지급 ID
     * @param comId - 회사ID
     * @return 급여 지급 정보
     */
    Map<String, Object> getSalaryPayment(@Param("paymentId") Long paymentId, @Param("comId") String comId);
    
    /**
     * 급여 지급 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호 (선택사항)
     * @param payMonth - 지급년월 (선택사항)
     * @param payStatus - 지급상태 (선택사항)
     * @return 급여 지급 목록
     */
    List<Map<String, Object>> getSalaryPaymentList(@Param("comId") String comId, 
                                                   @Param("empId") String empId,
                                                   @Param("payMonth") String payMonth,
                                                   @Param("payStatus") String payStatus);
    
    /**
     * 급여 통계 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @param payMonth - 지급년월
     * @return 통계 정보
     */
    Map<String, Object> getSalaryStatistics(@Param("comId") String comId, 
                                           @Param("deptCode") String deptCode, 
                                           @Param("payMonth") String payMonth);
    
    /**
     * 급여 지급 이력 조회
     * @param comId - 회사ID
     * @param empId - 사원번호 (선택사항)
     * @param fromMonth - 시작년월
     * @param toMonth - 종료년월
     * @return 지급 이력
     */
    List<Map<String, Object>> getSalaryPaymentHistory(@Param("comId") String comId,
                                                      @Param("empId") String empId,
                                                      @Param("fromMonth") String fromMonth,
                                                      @Param("toMonth") String toMonth);
} 