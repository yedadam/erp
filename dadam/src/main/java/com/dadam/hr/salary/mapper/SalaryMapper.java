package com.dadam.hr.salary.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dadam.hr.salary.service.SalaryCalculationVO;
import com.dadam.hr.salary.service.SalaryDetailVO;

/**
 * 급여 매퍼 인터페이스
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2024-01-01
 */
@Mapper
public interface SalaryMapper {

    /**
     * 급여 명세서 목록 조회
     * 
     * @param params 조회 조건
     * @return 급여 명세서 목록
     */
    List<Map<String, Object>> selectSalaryList(Map<String, Object> params);

    /**
     * 급여 명세서 상세 조회
     * 
     * @param salaryId 급여 ID
     * @return 급여 명세서 상세 정보
     */
    Map<String, Object> selectSalaryDetail(@Param("salaryId") Long salaryId);

    /**
     * 급여 상세 항목 조회
     * 
     * @param salaryId 급여 ID
     * @return 급여 상세 항목 목록
     */
    List<Map<String, Object>> selectSalaryDetailItems(@Param("salaryId") Long salaryId);

    /**
     * 급여 상태 업데이트(단건)
     * status 값에 따라 approved_at, paid_at 자동 처리
     */
    int updateSalaryStatus(Map<String, Object> params);

    /**
     * 급여 항목 설정 조회
     * 
     * @param companyId 회사 ID
     * @return 급여 항목 설정
     */
    Map<String, Object> selectSalaryItemsByCompany(@Param("companyId") Long companyId);

    /**
     * 급여 항목 설정 저장
     * 
     * @param params 급여 항목 설정
     * @return 저장된 행 수
     */
    int insertSalaryItems(Map<String, Object> params);

    /**
     * 급여 마스터 저장
     * 
     * @param calculation 급여 계산 결과
     * @return 저장된 행 수
     */
    int insertSalaryMaster(SalaryCalculationVO calculation);

    /**
     * 급여 상세 저장
     * 
     * @param detail 급여 상세 정보
     * @return 저장된 행 수
     */
    int insertSalaryDetail(SalaryDetailVO detail);

    /**
     * 사원별 년월 급여 삭제
     * 
     * @param params 삭제 조건
     * @return 삭제된 행 수
     */
    int deleteSalaryByEmployeeAndMonth(Map<String, Object> params);

    /**
     * 급여 계산 통계 조회
     * 
     * @param params 조회 조건
     * @return 급여 계산 통계
     */
    Map<String, Object> selectSalaryCalculationStats(Map<String, Object> params);

    /**
     * 급여 항목별 통계 조회
     * 
     * @param params 조회 조건
     * @return 급여 항목별 통계
     */
    List<Map<String, Object>> selectSalaryItemStats(Map<String, Object> params);

    /**
     * 급여 계산 이력 조회
     * 
     * @param params 조회 조건
     * @return 급여 계산 이력
     */
    List<Map<String, Object>> selectSalaryCalculationHistory(Map<String, Object> params);

    /**
     * 급여 승인 대기 목록 조회
     * 
     * @param companyId 회사 ID
     * @return 급여 승인 대기 목록
     */
    List<Map<String, Object>> selectPendingApprovalList(@Param("companyId") Long companyId);

    /**
     * 급여 지급 완료 목록 조회
     * 
     * @param params 조회 조건
     * @return 급여 지급 완료 목록
     */
    List<Map<String, Object>> selectPaidSalaryList(Map<String, Object> params);

    /**
     * 급여 일괄 승인/지급
     * status 값에 따라 approved_at, paid_at 자동 처리
     */
    int approveBatchSalaries(Map<String, Object> params);

    /**
     * 급여 엑셀 내보내기 데이터 조회
     * 
     * @param params 조회 조건
     * @return 엑셀 내보내기 데이터
     */
    List<Map<String, Object>> selectSalaryExportData(Map<String, Object> params);
} 