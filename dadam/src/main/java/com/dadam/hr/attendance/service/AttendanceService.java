package com.dadam.hr.attendance.service;

import java.util.List;

/**
 * 근태 관리 서비스
 */
public interface AttendanceService {
    // 출근 현황 리스트 조회
    List<AttendanceVO> findAttendanceList(String comId, String date);
} 