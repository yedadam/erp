package com.dadam.hr.salary.web.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

/**
 * 급여 계산 결과 VO
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2024-01-01
 */
@Data
public class SalaryCalculationVO {
    
    /** 급여 ID */
    private Long salaryId;
    
    /** 회사 ID */
    private Long companyId;
    
    /** 사원 ID */
    private Long employeeId;
    
    /** 사원명 */
    private String employeeName;
    
    /** 계산 년월 (YYYY-MM) */
    private String yearMonth;
    
    /** 기본급 */
    private BigDecimal baseSalary;
    
    /** 수당 항목별 금액 */
    private Map<String, BigDecimal> allowances;
    
    /** 공제 항목별 금액 */
    private Map<String, BigDecimal> deductions;
    
    /** 총 급여 */
    private BigDecimal totalSalary;
    
    /** 실수령액 */
    private BigDecimal netSalary;
    
    /** 계산 상태 (CALCULATED, APPROVED, PAID) */
    private String status;
    
    /** 계산일시 */
    private LocalDateTime calculatedAt;
    
    /** 승인일시 */
    private LocalDateTime approvedAt;
    
    /** 지급일시 */
    private LocalDateTime paidAt;
    
    /** 비고 */
    private String remarks;
} 