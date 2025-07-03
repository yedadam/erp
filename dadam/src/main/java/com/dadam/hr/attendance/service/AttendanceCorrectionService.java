package com.dadam.hr.attendance.service;

import java.util.List;
 
/**
 * 근태 정정 서비스 인터페이스
 */
public interface AttendanceCorrectionService {
    /**
     * 정정요청 처리
     * @param vo - 정정 정보
     */
    void requestCorrection(AttendanceCorrectionVO vo) throws Exception;
    /**
     * 정정 승인/반려 처리
     * @param corrCode - 정정코드
     * @param status - 상태코드
     * @param approverId - 승인자ID
     */
    void approveCorrection(String corrCode, String status, String approverId) throws Exception;
    /**
     * 정정 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 정정 리스트
     */
    List<AttendanceCorrectionVO> getCorrectionList(String comId, String empId);
} 