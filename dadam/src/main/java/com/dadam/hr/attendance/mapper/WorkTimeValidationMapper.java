package com.dadam.hr.attendance.mapper;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

/**
 * 근무시간 검증 Mapper 인터페이스
 */
public interface WorkTimeValidationMapper {
    
    /**
     * 근무시간 검증 데이터 조회
     * @param param - 검색 파라미터
     * @return 근무시간 검증 리스트
     */
    List<Map<String, Object>> selectWorkTimeValidationList(Map<String, Object> param);
    
    /**
     * 근무시간 검증 데이터 등록
     * @param param - 등록할 데이터
     * @return 등록된 행 수
     */
    int insertWorkTimeValidation(Map<String, Object> param);
    
    /**
     * 근무시간 검증 데이터 수정
     * @param param - 수정할 데이터
     * @return 수정된 행 수
     */
    int updateWorkTimeValidation(Map<String, Object> param);
    
    /**
     * 근무시간 검증 데이터 삭제
     * @param param - 삭제할 데이터
     * @return 삭제된 행 수
     */
    int deleteWorkTimeValidation(Map<String, Object> param);
    
    // === WorkTimeValidationService에서 사용하는 메서드들 ===
    
    /**
     * 일일 근무시간 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param date - 날짜
     * @return 일일 근무시간
     */
    double getDailyWorkHours(String empId, String comId, LocalDate date);
    
    /**
     * 주간 근무시간 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param date - 날짜
     * @return 주간 근무시간
     */
    double getWeeklyWorkHours(String empId, String comId, LocalDate date);
    
    /**
     * 월간 근무시간 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param date - 날짜
     * @return 월간 근무시간
     */
    double getMonthlyWorkHours(String empId, String comId, LocalDate date);
    
    /**
     * 월간 초과근무시간 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param date - 날짜
     * @return 월간 초과근무시간
     */
    double getMonthlyOvertimeHours(String empId, String comId, LocalDate date);
    
    /**
     * 일일 휴식시간 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param date - 날짜
     * @return 일일 휴식시간
     */
    double getDailyRestHours(String empId, String comId, LocalDate date);
    
    /**
     * 위반 통계 조회
     * @param param - 검색 파라미터
     * @return 위반 통계
     */
    Map<String, Object> getViolationStats(Map<String, Object> param);
    
    /**
     * 부서별 위반 통계 조회
     * @param param - 검색 파라미터
     * @return 부서별 위반 통계
     */
    List<Map<String, Object>> getDeptViolationStats(Map<String, Object> param);
    
    /**
     * 사원별 위반 통계 조회
     * @param param - 검색 파라미터
     * @return 사원별 위반 통계
     */
    List<Map<String, Object>> getEmpViolationStats(Map<String, Object> param);
    
    /**
     * 근무시간 위반 알림 등록
     * @param param - 알림 데이터
     * @return 등록된 행 수
     */
    int insertWorkTimeViolationAlert(Map<String, Object> param);
} 