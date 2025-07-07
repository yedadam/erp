package com.dadam.hr.attendance.service;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 실시간 출퇴근 현황 VO
 */
@Data
public class AttendanceRealtimeVO {
    /** 사원번호 */
    private String empId;
    /** 사원명 */
    private String empName;
    /** 부서코드 */
    private String deptCode;
    /** 부서명 */
    private String deptName;
    /** 직급명 */
    private String positionName;
    /** 오늘 출근시간 */
    private LocalDateTime todayCheckIn;
    /** 오늘 퇴근시간 */
    private LocalDateTime todayCheckOut;
    /** 출근 상태 */
    private String checkInStatus; // 출근/미출근/지각
    /** 퇴근 상태 */
    private String checkOutStatus; // 퇴근/미퇴근/조퇴
    /** 현재 근무시간 */
    private String currentWorkTime; // HH:MM 형식
    /** 연장근무시간 */
    private String overtimeHours; // HH:MM 형식
    /** 근무상태 */
    private String workStatus; // 근무중/퇴근/휴가/결근
    /** 마지막 업데이트 */
    private LocalDateTime lastUpdate;
} 