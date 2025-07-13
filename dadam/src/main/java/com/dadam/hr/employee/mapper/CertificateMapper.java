package com.dadam.hr.employee.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 증명서 발급 Mapper
 * - 재직증명서, 경력증명서, 급여증명서, 퇴직증명서
 * - 증명서 템플릿 관리
 * - 발급 이력 관리
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2025-07-11
 */
@Mapper
public interface CertificateMapper {

    /**
     * 사원 기본 정보 조회
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @return 사원 기본 정보
     */
    Map<String, Object> getEmployeeInfo(@Param("empId") String empId, 
                                       @Param("comId") String comId);

    /**
     * 사원 경력 이력 조회
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @return 경력 이력 목록
     */
    List<Map<String, Object>> getCareerHistory(@Param("empId") String empId, 
                                              @Param("comId") String comId);

    /**
     * 사원 급여 이력 조회
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @return 급여 이력 목록
     */
    List<Map<String, Object>> getSalaryHistory(@Param("empId") String empId, 
                                              @Param("comId") String comId);

    /**
     * 사원 퇴직 정보 조회
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @return 퇴직 정보
     */
    Map<String, Object> getResignationInfo(@Param("empId") String empId, 
                                          @Param("comId") String comId);

    /**
     * 증명서 발급 이력 저장
     * 
     * @param history 발급 이력 정보
     * @return 저장된 행 수
     */
    int insertCertificateHistory(Map<String, Object> history);

    /**
     * 증명서 발급 이력 조회
     * 
     * @param params 조회 조건
     * @return 발급 이력 목록
     */
    List<Map<String, Object>> selectCertificateHistory(Map<String, Object> params);

    /**
     * 증명서 템플릿 조회
     * 
     * @param certificateType 증명서 유형
     * @param comId 회사 ID
     * @return 증명서 템플릿
     */
    Map<String, Object> getCertificateTemplate(@Param("certificateType") String certificateType, 
                                             @Param("comId") String comId);
} 