package com.dadam.hr.attendance.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 출퇴근 위치 관리 Mapper 인터페이스
 * - 위치 정보 저장/조회
 * - GPS/IP 기반 위치 검증
 * - 위치 통계 및 이력 관리
 */
@Mapper
public interface AttendanceLocationMapper {
    
    /**
     * 위치 정보 저장
     * @param param - 위치 정보
     * @return 저장 결과
     */
    int insertLocation(Map<String, Object> param);
    
    /**
     * 위치 정보 조회
     * @param param - 조회 조건
     * @return 위치 정보
     */
    Map<String, Object> selectLocation(Map<String, Object> param);
    
    /**
     * 위치 검증 (GPS/IP 기반)
     * @param comId - 회사ID
     * @param lat - 위도
     * @param lng - 경도
     * @param ipAddress - IP 주소
     * @return 검증 결과
     */
    boolean validateLocation(@Param("comId") String comId, 
                           @Param("lat") double lat, 
                           @Param("lng") double lng, 
                           @Param("ipAddress") String ipAddress);
    
    /**
     * 회사 위치 정보 조회
     * @param comId - 회사ID
     * @return 회사 위치 정보
     */
    Map<String, Object> getCompanyLocation(@Param("comId") String comId);
    
    /**
     * 위치 정보 업데이트
     * @param param - 업데이트 정보
     * @return 업데이트 결과
     */
    int updateLocation(Map<String, Object> param);
    
    /**
     * 위치 통계 조회
     * @param param - 조회 조건
     * @return 위치 통계
     */
    List<Map<String, Object>> getLocationStats(Map<String, Object> param);
    
    /**
     * 위치 이력 조회
     * @param param - 조회 조건
     * @return 위치 이력
     */
    List<Map<String, Object>> getLocationHistory(Map<String, Object> param);
    
    /**
     * 위치 검증 로그 저장
     * @param param - 로그 정보
     * @return 저장 결과
     */
    int insertLocationValidationLog(Map<String, Object> param);
    
    /**
     * 위치 검증 로그 조회
     * @param param - 조회 조건
     * @return 검증 로그
     */
    List<Map<String, Object>> getLocationValidationLog(Map<String, Object> param);
} 