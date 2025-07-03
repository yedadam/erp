package com.dadam.hr.attendance.service;

import java.util.List;

/**
 * 근태 서비스 인터페이스
 */
public interface AttendanceService {
    /**
     * 출근 등록
     * @param vo - 근태 정보
     * @param requestIp - 요청 IP
     * @param gpsInfo - GPS 정보
     */
    void checkIn(AttendanceVO vo, String requestIp, String gpsInfo) throws Exception;
    /**
     * 퇴근 등록
     * @param vo - 근태 정보
     * @param requestIp - 요청 IP
     * @param gpsInfo - GPS 정보
     */
    void checkOut(AttendanceVO vo, String requestIp, String gpsInfo) throws Exception;
    /**
     * 근태 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param fromDate - 시작일
     * @param toDate - 종료일
     * @return 근태 리스트
     */
    List<AttendanceVO> getAttendanceList(String comId, String empId, String fromDate, String toDate);
} 