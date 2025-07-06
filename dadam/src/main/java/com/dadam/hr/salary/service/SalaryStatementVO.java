package com.dadam.hr.salary.service;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 급여명세서 VO
 * - 근태 기반 급여 계산을 위한 개선된 구조
 */
@Data
public class SalaryStatementVO {
    /** 명세서 ID */
    private Long id;
    /** 회사ID */
    private String comId;
    /** 사원 ID */
    private String empId;
    /** 지급년월 */
    private String payMonth;
    /** 기본급 */
    private BigDecimal baseSalary;
    /** 근무일수 */
    private Integer workDays;
    /** 실제근무일수 */
    private Integer actualWorkDays;
    /** 정상출근일수 */
    private Integer normalDays;
    /** 지각일수 */
    private Integer lateDays;
    /** 조퇴일수 */
    private Integer earlyLeaveDays;
    /** 결근일수 */
    private Integer absentDays;
    /** 연장근무시간 */
    private BigDecimal overtimeHours;
    /** 연장근무수당 */
    private BigDecimal overtimePay;
    /** 지각공제 */
    private BigDecimal lateDeduction;
    /** 조퇴공제 */
    private BigDecimal earlyLeaveDeduction;
    /** 결근공제 */
    private BigDecimal absentDeduction;
    /** 연차수당 */
    private BigDecimal annualLeavePay;
    /** 수당 총액 */
    private BigDecimal allowanceTotal;
    /** 공제 총액 */
    private BigDecimal deductionTotal;
    /** 실수령액 */
    private BigDecimal netPay;
    /** 생성일 */
    private LocalDateTime createdDate;
    /** 수정일 */
    private LocalDateTime updatedDate;
    
    // 조회용 필드
    /** 사원명 */
    private String empName;
    /** 부서명 */
    private String deptName;
    /** 직급명 */
    private String positionName;
} 