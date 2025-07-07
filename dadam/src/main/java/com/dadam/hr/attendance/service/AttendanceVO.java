package com.dadam.hr.attendance.service;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 근태 정보 VO
 */
@Data
public class AttendanceVO {
    /** 근태코드 */
    private String attendanceCode;
    /** 회사ID */
    private String comId;
    /** 사원번호 */
    private String empId;
    /** 근무일자 */
    private LocalDate workDate;
    /** 기준 출근시간 */
    private LocalDateTime standardStartTime;
    /** 기준 퇴근시간 */
    private LocalDateTime standardEndTime;
    /** 실제 출근시간 */
    private LocalDateTime actualStartTime;
    /** 실제 퇴근시간 */
    private LocalDateTime actualEndTime;
    /** 연장근무시간 */
    private BigDecimal overtimeHrs;
    /** 상태코드 */
    private String status;         // 정상/지각/조퇴/결근 등 (공통코드)
    /** 휴일여부 (Y/N) */
    private String isHoliday;
    /** 근무유형코드 */
    private String workType;       // 정상/재택/외근 등 (공통코드)
    /** 출퇴근 시도 IP */
    private String checkIp;
    /** GPS 정보 */
    private String gpsInfo;
    /** 위치 정보 */
    private String locationInfo;
    /** 비고 */
    private String note;
    /** 생성일시 */
    private LocalDateTime createdAt;
    /** 수정일시 */
    private LocalDateTime updatedAt;
} 