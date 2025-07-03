package com.dadam.hr.attendance.service;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 근태 정정 정보 VO
 */
@Data
public class AttendanceCorrectionVO {
    /** 정정코드 */
    private String corrCode;
    /** 근태코드 */
    private String attendanceCode;
    /** 회사ID */
    private String comId;
    /** 사원번호 */
    private String empId;
    /** 신청일 */
    private LocalDate reqDate;
    /** 정정사유 */
    private String reason;
    /** 정정 출근시간 */
    private LocalDateTime newInTime;
    /** 정정 퇴근시간 */
    private LocalDateTime newOutTime;
    /** 상태코드 */
    private String status; // 대기/승인/반려 등 (공통코드)
    /** 승인자ID */
    private String approverId;
    /** 승인일 */
    private LocalDate approveDate;
    /** 비고 */
    private String note;
    /** 생성일시 */
    private LocalDateTime createdAt;
    /** 수정일시 */
    private LocalDateTime updatedAt;
} 