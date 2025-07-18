package com.dadam.hr.attendance.service;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 연차 정보 VO
 */
@Data
public class AnnualLeaveVO {
    /** 연차코드 */
    private String leaveCode;
    /** 회사ID */
    private String comId;
    /** 사원번호 */
    private String empId;
    /** 연차유형코드 */
    private String leaveType; // 공통코드: 연차/반차/병가 등
    /** 시작일 */
    private LocalDate startDate;
    /** 종료일 */
    private LocalDate endDate;
    /** 사용일수 */
    private double days; // 사용일수(0.5, 1 등)
    /** 상태코드 */
    private String status; // 대기/승인/반려 등
    /** 사유 */
    private String reason;
    /** 승인자ID */
    private String approveId;
    /** 승인일 */
    private LocalDate approveDate;
    /** 비고 */
    private String note;
    /** 생성일시 */
    private LocalDateTime createdAt;
    /** 수정일시 */
    private LocalDateTime updatedAt;
    
    // === 스케줄러에서 사용하는 필드들 ===
    
    /** 연도 */
    private int year;
    /** 총 연차일수 */
    private int totalDays;
    /** 사용한 연차일수 */
    private int usedDays;
    /** 잔여 연차일수 */
    private int remainingDays;
    /** 만료일 */
    private LocalDate expiryDate;
    /** 입사일 */
    private Date hireDate;
} 