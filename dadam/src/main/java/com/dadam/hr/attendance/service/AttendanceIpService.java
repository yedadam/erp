package com.dadam.hr.attendance.service;

import java.util.List;

/**
 * 출퇴근 IP 관리 서비스 인터페이스
 */
public interface AttendanceIpService {

    /**
     * IP 목록 조회
     * @param comId - 회사ID
     * @param type - 검색타입
     * @param value - 검색값
     * @return IP 리스트
     */
    List<AttendanceIpVO> findIpList(String comId, String type, String value);

    /**
     * IP 단건 조회
     * @param ipCode - IP 관리코드
     * @param comId - 회사ID
     * @return IP 정보
     */
    AttendanceIpVO findIpDetail(String ipCode, String comId);

    /**
     * IP 등록
     * @param vo - IP 정보
     * @return 등록 결과
     */
    int insertIp(AttendanceIpVO vo);

    /**
     * IP 수정
     * @param vo - IP 정보
     * @return 수정 결과
     */
    int updateIp(AttendanceIpVO vo);

    /**
     * IP 삭제
     * @param ipCode - IP 관리코드
     * @param comId - 회사ID
     * @return 삭제 결과
     */
    int deleteIp(String ipCode, String comId);
} 