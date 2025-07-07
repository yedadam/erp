package com.dadam.hr.attendance.service;

import java.util.List;

/**
 * 근태 서비스 인터페이스
 * - 보안 검증: IP, GPS, 지점 검증
 * - 자동 판정: 지각, 조퇴, 연장근무 자동 계산
 * - 권한 관리: 부서별/권한별 데이터 접근 제어
 */
public interface AttendanceService {
    /**
     * 출근 등록 (보안 검증 포함)
     * @param vo - 근태 정보
     * @param requestIp - 요청 IP
     * @param gpsInfo - GPS 정보
     * @param locationInfo - 지점 정보
     * @return 처리 결과
     */
    String checkIn(AttendanceVO vo, String requestIp, String gpsInfo, String locationInfo) throws Exception;
    
    /**
     * 퇴근 등록 (보안 검증 포함)
     * @param vo - 근태 정보
     * @param requestIp - 요청 IP
     * @param gpsInfo - GPS 정보
     * @param locationInfo - 지점 정보
     * @return 처리 결과
     */
    String checkOut(AttendanceVO vo, String requestIp, String gpsInfo, String locationInfo) throws Exception;
    
    /**
     * 근태 목록 조회 (권한별 필터링)
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param fromDate - 시작일
     * @param toDate - 종료일
     * @param deptCode - 부서코드 (권한 필터링용)
     * @return 근태 리스트
     */
    List<AttendanceVO> getAttendanceList(String comId, String empId, String fromDate, String toDate, String deptCode);
    
    /**
     * IP 검증
     * @param requestIp - 요청 IP
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 검증 결과
     */
    boolean validateIp(String requestIp, String comId, String empId);
    
    /**
     * GPS 검증
     * @param gpsInfo - GPS 정보
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 검증 결과
     */
    boolean validateGps(String gpsInfo, String comId, String empId);
    
    /**
     * 지각/조퇴 자동 판정
     * @param vo - 근태 정보
     * @return 판정 결과 (정상/지각/조퇴)
     */
    String judgeAttendanceStatus(AttendanceVO vo);
    
    /**
     * 연장근무 시간 계산
     * @param vo - 근태 정보
     * @return 연장근무 시간
     */
    double calculateOvertimeHours(AttendanceVO vo);
    
    /**
     * 실시간 출퇴근 현황 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @return 실시간 현황
     */
    List<AttendanceVO> getRealtimeStatus(String comId, String deptCode);
} 