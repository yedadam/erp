package com.dadam.hr.attendance.service;

import java.util.List;

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
} 