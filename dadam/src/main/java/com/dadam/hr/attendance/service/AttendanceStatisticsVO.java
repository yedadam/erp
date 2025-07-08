package com.dadam.hr.attendance.service;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 근태 통계 VO
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2024-01-01
 */
@Data
public class AttendanceStatisticsVO {
    
    /** 사원 ID */
    private Long employeeId;
    
    /** 년월 (YYYY-MM) */
    private String yearMonth;
    
    /** 총 근무일수 */
    private Integer totalWorkDays;
    
    /** 정상근무일수 */
    private Integer normalWorkDays;
    
    /** 연장근무일수 */
    private Integer overtimeWorkDays;
    
    /** 휴일근무일수 */
    private Integer holidayWorkDays;
    
    /** 야간근무일수 */
    private Integer nightWorkDays;
    
    /** 휴가일수 */
    private Integer vacationDays;
    
    /** 병가일수 */
    private Integer sickDays;
    
    /** 개인휴가일수 */
    private Integer personalDays;
    
    /** 총 근무시간 */
    private BigDecimal totalWorkHours;
    
    /** 연장근무시간 */
    private BigDecimal overtimeHours;
    
    /** 휴일근무시간 */
    private BigDecimal holidayHours;
    
    /** 야간근무시간 */
    private BigDecimal nightHours;
    
    /** 평균 근무시간 */
    private BigDecimal avgWorkHours;
    
    /** 최소 근무시간 */
    private BigDecimal minWorkHours;
    
    /** 최대 근무시간 */
    private BigDecimal maxWorkHours;
} 