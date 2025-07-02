package com.dadam.hr.attendance.service;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceVO {
    private String attendanceCode; // 근태기록 고유코드
    private Date workDate; // 근무일자
    private Date standardStartTime; // 기준 출근시간
    private Date standardEndTime; // 기준 퇴근시간
    private Date actualStartTime; // 실제 출근시간
    private Date actualEndTime; // 실제 퇴근시간
    private String status; // 근태상태(정상/지각/조퇴/결근/휴가 등)
    private Double overtimeHrs; // 연장근무시간
    private String overtimeType; // 연장근무유형(평일/휴일 등)
    private String empId; // 사원ID
    private String comId; // 회사ID(테넌시)
    private String correctionStatus; // 정정요청 상태
    private String correctionReason; // 정정요청 사유
    private String leaveType; // 휴가/결근 유형
    private String checkIp; // 출퇴근 시 사용한 IP
    private String note; // 비고/메모
    private Date createdAt; // 레코드 생성 시각
    private Date updatedAt; // 레코드 최종 수정 시각
} 