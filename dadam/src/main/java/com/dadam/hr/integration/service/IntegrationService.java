package com.dadam.hr.integration.service;

import java.util.List;
import java.util.Map;

/**
 * 통합 연동 서비스 인터페이스
 * - 사원/부서/권한 → 근태 → 급여 연동
 * - 데이터 일관성 검증
 * - 통합 통계 및 대시보드
 */
public interface IntegrationService {
    
    /**
     * 전체 시스템 상태 확인
     * @param comId - 회사ID
     * @return 시스템 상태 정보
     */
    Map<String, Object> getSystemStatus(String comId);
    
    /**
     * 사원 정보 연동 검증
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 연동 검증 결과
     */
    Map<String, Object> validateEmployeeIntegration(String empId, String comId);
    
    /**
     * 근태 데이터 연동 검증
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param month - 검증월
     * @return 연동 검증 결과
     */
    Map<String, Object> validateAttendanceIntegration(String empId, String comId, String month);
    
    /**
     * 급여 데이터 연동 검증
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param payMonth - 급여년월
     * @return 연동 검증 결과
     */
    Map<String, Object> validateSalaryIntegration(String empId, String comId, String payMonth);
    
    /**
     * 전체 연동 테스트 실행
     * @param comId - 회사ID
     * @return 테스트 결과
     */
    Map<String, Object> runIntegrationTest(String comId);
    
    /**
     * 권한별 기능 테스트
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param authority - 권한
     * @return 테스트 결과
     */
    Map<String, Object> testAuthorityFunctions(String comId, String empId, String authority);
    
    /**
     * 데이터 정합성 테스트
     * @param comId - 회사ID
     * @return 정합성 검증 결과
     */
    Map<String, Object> testDataConsistency(String comId);
    
    /**
     * 통합 대시보드 데이터 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드 (선택사항)
     * @return 대시보드 데이터
     */
    Map<String, Object> getDashboardData(String comId, String deptCode);
    
    /**
     * 실시간 현황 조회
     * @param comId - 회사ID
     * @return 실시간 현황
     */
    Map<String, Object> getRealtimeStatus(String comId);
    
    /**
     * 부서별 통계 조회
     * @param comId - 회사ID
     * @param month - 통계월
     * @return 부서별 통계
     */
    List<Map<String, Object>> getDepartmentStatistics(String comId, String month);
    
    /**
     * 전체 시스템 통계 조회
     * @param comId - 회사ID
     * @param month - 통계월
     * @return 전체 통계
     */
    Map<String, Object> getSystemStatistics(String comId, String month);
    
    /**
     * 데이터 백업 및 복원
     * @param comId - 회사ID
     * @param operation - 작업 (BACKUP/RESTORE)
     * @param backupPath - 백업 경로
     * @return 작업 결과
     */
    String backupRestoreData(String comId, String operation, String backupPath);
    
    /**
     * 시스템 로그 조회
     * @param comId - 회사ID
     * @param fromDate - 시작일
     * @param toDate - 종료일
     * @param logType - 로그 타입 (ERROR/WARNING/INFO)
     * @return 로그 목록
     */
    List<Map<String, Object>> getSystemLogs(String comId, String fromDate, String toDate, String logType);
} 