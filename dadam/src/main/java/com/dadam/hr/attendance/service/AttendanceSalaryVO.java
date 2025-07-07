package com.dadam.hr.attendance.service;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 근태 기반 급여 계산 VO
 */
@Data
public class AttendanceSalaryVO {
    /** 급여계산코드 */
    private String salaryCode;
    /** 회사ID */
    private String comId;
    /** 사원번호 */
    private String empId;
    /** 급여년월 */
    private String salaryYearMonth;
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
    /** 실수령액 */
    private BigDecimal netSalary;
    /** 생성일시 */
    private String createdAt;
    /** 수정일시 */
    private String updatedAt;
    
    // 조회용 필드
    /** 사원명 */
    private String empName;
    /** 부서명 */
    private String deptName;
    /** 직급명 */
    private String positionName;
} 