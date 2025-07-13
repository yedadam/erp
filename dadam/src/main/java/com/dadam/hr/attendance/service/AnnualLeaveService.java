package com.dadam.hr.attendance.service;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

/**
 * 연차 서비스 인터페이스
 */
public interface AnnualLeaveService {
    /**
     * 연차 신청
     * @param vo - 연차 정보
     */
    void requestAnnualLeave(AnnualLeaveVO vo) throws Exception;
    /**
     * 연차 승인/반려 처리
     * @param leaveCode - 연차코드
     * @param status - 상태코드
     * @param approveId - 승인자ID
     */
    void approveAnnualLeave(String leaveCode, String status, String approveId) throws Exception;
    /**
     * 연차 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 연차 리스트
     */
    List<AnnualLeaveVO> getAnnualLeaveList(String comId, String empId);
    /**
     * 잔여 연차 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 잔여 연차
     */
    double getRemainAnnualLeave(String empId, String comId);
    
    // === 스케줄러에서 사용하는 메서드들 ===
    
    /**
     * 연차 생성
     * @param vo - 연차 정보
     * @return 생성된 연차 정보
     */
    AnnualLeaveVO createAnnualLeave(AnnualLeaveVO vo) throws Exception;
    
    /**
     * 연차 수정
     * @param vo - 연차 정보
     * @return 수정된 연차 정보
     */
    AnnualLeaveVO updateAnnualLeave(AnnualLeaveVO vo) throws Exception;
    
    /**
     * 사원별 연차 조회 (연도별)
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param year - 연도
     * @return 연차 정보
     */
    AnnualLeaveVO getAnnualLeaveByEmpAndYear(String empId, String comId, int year);
    
    /**
     * 만료된 연차 조회
     * @param date - 기준일
     * @return 만료된 연차 리스트
     */
    List<AnnualLeaveVO> getExpiredAnnualLeaves(LocalDate date);
    
    /**
     * 미사용 연차 사원 조회
     * @param date - 기준일
     * @return 미사용 연차 사원 리스트
     */
    List<AnnualLeaveVO> getUnusedLeaveEmployees(LocalDate date);
    
    /**
     * 오늘 사용된 연차 조회
     * @param date - 기준일
     * @return 오늘 사용된 연차 리스트
     */
    List<AnnualLeaveVO> getUsedLeavesToday(LocalDate date);
    
    /**
     * 월별 연차 통계 생성
     * @param date - 기준일
     * @return 통계 데이터
     */
    Map<String, Object> generateMonthlyLeaveStatistics(LocalDate date);
    
    /**
     * 월별 연차 통계 저장
     * @param statistics - 통계 데이터
     * @return 저장 결과
     */
    boolean saveMonthlyLeaveStatistics(Map<String, Object> statistics);
} 