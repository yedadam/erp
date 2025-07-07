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
     * 정정 승인/반려 처리 (3개 인자)
     */
    void approveCorrection(String corrCode, String status, String approverId) throws Exception;
    /**
     * 정정 승인/반려 처리 (4개 인자)
     */
    void approveCorrection(String corrCode, String status, String approverId, String note) throws Exception;
    /**
     * 정정 목록 조회 (2개 인자)
     */
    List<AttendanceCorrectionVO> getCorrectionList(String comId, String empId);
    /**
     * 정정 목록 조회 (3개 인자)
     */
    List<AttendanceCorrectionVO> getCorrectionList(String comId, String empId, String status);
    /**
     * 정정 단건 조회
     */
    AttendanceCorrectionVO getCorrectionDetail(String corrCode);
} 