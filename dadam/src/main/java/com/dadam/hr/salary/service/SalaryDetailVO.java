package com.dadam.hr.salary.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 급여 상세 VO
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2024-01-01
 */
@Data
public class SalaryDetailVO {
    /** 상세 ID */
    private Long detailId;
    /** 급여 ID */
    private Long salaryId;
    /** 항목유형 (ALLOWANCE, DEDUCTION) */
    private String itemType;
    /** 항목명 */
    private String itemName;
    /** 금액 */
    private BigDecimal amount;
    /** 비고 */
    private String remarks;
    /** 생성일시 */
    private LocalDateTime createdAt;
} 