package com.dadam.hr.employee.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 사원 이력 관리 Mapper
 * - 입사/퇴사/이동/승진 이력 관리
 * - 이력 통계 및 엑셀 내보내기
 * - 이력 알림 관리
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2025-07-11
 */
@Mapper
public interface EmployeeHistoryMapper {

    /**
     * 사원 이력 저장
     * 
     * @param history 이력 정보
     * @return 저장된 행 수
     */
    int insertEmployeeHistory(Map<String, Object> history);

    /**
     * 사원 이력 조회
     * 
     * @param params 조회 조건
     * @return 이력 목록
     */
    List<Map<String, Object>> selectEmployeeHistory(Map<String, Object> params);

    /**
     * 변경 유형별 통계 조회
     * 
     * @param params 조회 조건
     * @return 변경 유형별 통계
     */
    List<Map<String, Object>> getChangeTypeStats(Map<String, Object> params);

    /**
     * 부서별 이력 통계 조회
     * 
     * @param params 조회 조건
     * @return 부서별 이력 통계
     */
    List<Map<String, Object>> getDeptHistoryStats(Map<String, Object> params);

    /**
     * 월별 이력 통계 조회
     * 
     * @param params 조회 조건
     * @return 월별 이력 통계
     */
    List<Map<String, Object>> getMonthlyHistoryStats(Map<String, Object> params);

    /**
     * 이력 알림 저장
     * 
     * @param notification 알림 정보
     * @return 저장된 행 수
     */
    int insertHistoryNotification(Map<String, Object> notification);

    /**
     * 엑셀 내보내기용 이력 데이터 조회
     * 
     * @param params 조회 조건
     * @return 엑셀 내보내기용 이력 데이터
     */
    List<Map<String, Object>> getEmployeeHistoryForExport(Map<String, Object> params);

    /**
     * 사원 이력 삭제
     * 
     * @param params 삭제 조건
     * @return 삭제된 행 수
     */
    int deleteEmployeeHistory(Map<String, Object> params);

    /**
     * 사원 이력 수정
     * 
     * @param params 수정 정보
     * @return 수정된 행 수
     */
    int updateEmployeeHistory(Map<String, Object> params);
} 