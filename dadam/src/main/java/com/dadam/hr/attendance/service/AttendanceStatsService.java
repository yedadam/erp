package com.dadam.hr.attendance.service;

import java.util.List;
import java.util.Map;

/**
 * 근태 통계 서비스
 */
public interface AttendanceStatsService {
    
    /**
     * 월별 근태 통계 조회
     * @param comId - 회사ID
     * @param yearMonth - 년월 (YYYY-MM)
     * @param deptCode - 부서코드
     * @return 통계 리스트
     */
    List<AttendanceStatsVO> findMonthlyStats(String comId, String yearMonth, String deptCode);
    
    /**
     * 개인별 근태 통계 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param yearMonth - 년월 (YYYY-MM)
     * @return 통계 정보
     */
    AttendanceStatsVO findPersonalStats(String comId, String empId, String yearMonth);
    
    /**
     * 부서별 근태 현황 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @return 부서별 현황
     */
    List<AttendanceStatsVO> findDeptStats(String comId, String deptCode);
    
    /**
     * 실시간 출퇴근 현황 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @return 실시간 현황
     */
    List<AttendanceRealtimeVO> findRealtimeStatus(String comId, String deptCode);
    
    /**
     * 오늘 근태 현황 요약
     * @param comId - 회사ID
     * @return 오늘 현황 요약
     */
    Map<String, Object> getTodaySummary(String comId);
    
    /**
     * 월별 통계 생성
     * @param comId - 회사ID
     * @param yearMonth - 년월 (YYYY-MM)
     * @return 생성 결과
     */
    int generateMonthlyStats(String comId, String yearMonth);
} 