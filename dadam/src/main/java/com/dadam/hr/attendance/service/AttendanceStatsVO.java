package com.dadam.hr.attendance.service;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 근태 통계 VO
 */
@Data
public class AttendanceStatsVO {
    /** 통계코드 */
    private String statsCode;
    /** 회사ID */
    private String comId;
    /** 부서코드 */
    private String deptCode;
    /** 사원번호 */
    private String empId;
    /** 통계년월 */
    private String statsYearMonth;
    /** 총 근무일수 */
    private Integer totalWorkDays;
    /** 실제 근무일수 */
    private Integer actualWorkDays;
    /** 정상 출근일수 */
    private Integer normalDays;
    /** 지각일수 */
    private Integer lateDays;
    /** 조퇴일수 */
    private Integer earlyLeaveDays;
    /** 결근일수 */
    private Integer absentDays;
    /** 연장근무시간 */
    private BigDecimal overtimeHours;
    /** 연차 사용일수 */
    private BigDecimal annualLeaveDays;
    /** 반차 사용일수 */
    private BigDecimal halfLeaveDays;
    /** 출근률 (%) */
    private BigDecimal attendanceRate;
    /** 지각률 (%) */
    private BigDecimal lateRate;
    /** 생성일시 */
    private String createdAt;
    /** 수정일시 */
    private String updatedAt;
    
    // 조회용 필드
    /** 부서명 */
    private String deptName;
    /** 사원명 */
    private String empName;
    /** 직급명 */
    private String positionName;
} 