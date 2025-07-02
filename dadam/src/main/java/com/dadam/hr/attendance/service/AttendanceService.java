package com.dadam.hr.attendance.service;

import java.util.List;

/**
 * 근태관리(Attendance) 서비스 인터페이스
 * 출근/퇴근 기록 저장 및 조회 비즈니스 로직
 */
public interface AttendanceService {
    /**
     * 출근/퇴근 기록 저장
     */
    int insertAttendance(AttendanceVO attendance);

    /**
     * 사원ID, 연월로 출퇴근 기록 조회
     * @param empId 사원ID
     * @param yearMonth 'YYYY-MM' 형식
     */
    List<AttendanceVO> selectByEmpIdAndMonth(String empId, String yearMonth);

    // 필요시 추가 메서드 정의
} 