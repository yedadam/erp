package com.dadam.hr.integration.mapper;

import java.util.List;
import java.util.Map;

/**
 * 통합 연동 Mapper 인터페이스
 * - 시스템 상태 조회
 * - 데이터 연동 검증
 * - 통계 및 대시보드
 * - 백업/복원
 */
public interface IntegrationMapper {
    
    /**
     * 사원 수 조회
     */
    int getEmployeeCount(String comId);
    
    /**
     * 활성 사원 수 조회
     */
    int getActiveEmployeeCount(String comId);
    
    /**
     * 근태 데이터 수 조회
     */
    int getAttendanceCount(String comId);
    
    /**
     * 오늘 근태 데이터 수 조회
     */
    int getTodayAttendanceCount(String comId);
    
    /**
     * 급여 데이터 수 조회
     */
    int getSalaryCount(String comId);
    
    /**
     * 이번 달 급여 데이터 수 조회
     */
    int getThisMonthSalaryCount(String comId);
    
    /**
     * 사원 정보 조회
     */
    Map<String, Object> getEmployeeInfo(String empId, String comId);
    
    /**
     * 부서 정보 조회
     */
    Map<String, Object> getDepartmentInfo(String deptCode, String comId);
    
    /**
     * 월별 근태 데이터 조회
     */
    List<Map<String, Object>> getAttendanceByMonth(String empId, String comId, String month);
    
    /**
     * 월별 급여 데이터 조회
     */
    Map<String, Object> getSalaryByMonth(String empId, String comId, String payMonth);
    
    /**
     * 급여 상세 내역 조회
     */
    List<Map<String, Object>> getSalaryDetails(String empId, String comId, String payMonth);
    
    /**
     * 전체 현황 조회
     */
    Map<String, Object> getOverallStatus(String comId);
    
    /**
     * 부서별 현황 조회
     */
    List<Map<String, Object>> getDepartmentStatus(String comId);
    
    /**
     * 최근 활동 조회
     */
    List<Map<String, Object>> getRecentActivities(String comId);
    
    /**
     * 급여 현황 조회
     */
    Map<String, Object> getSalaryStatus(String comId);
    
    /**
     * 현재 출근자 수 조회
     */
    int getCurrentWorkers(String comId);
    
    /**
     * 오늘 출근률 조회
     */
    double getTodayAttendanceRate(String comId);
    
    /**
     * 부서별 출근 현황 조회
     */
    List<Map<String, Object>> getDepartmentAttendance(String comId);
    
    /**
     * 부서별 통계 조회
     */
    List<Map<String, Object>> getDepartmentStatistics(String comId, String month);
    
    /**
     * 시스템 통계 조회
     */
    Map<String, Object> getSystemStatistics(String comId, String month);
    
    /**
     * 데이터 백업
     */
    String backupData(String comId, String backupPath);
    
    /**
     * 데이터 복원
     */
    String restoreData(String comId, String backupPath);
    
    /**
     * 시스템 로그 조회
     */
    List<Map<String, Object>> getSystemLogs(String comId, String fromDate, String toDate, String logType);
} 