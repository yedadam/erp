package com.dadam.hr.attendance.service.impl;

import com.dadam.hr.attendance.mapper.AttendanceIpMapper;
import com.dadam.hr.attendance.service.AttendanceIpService;
import com.dadam.hr.attendance.service.AttendanceIpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 출퇴근 IP 관리 서비스 구현체
 */
@Service
public class AttendanceIpServiceImpl implements AttendanceIpService {

    /** IP 관리 Mapper */
    @Autowired
    private AttendanceIpMapper attendanceIpMapper;

    /**
     * IP 목록 조회
     * @param comId - 회사ID
     * @param type - 검색타입
     * @param value - 검색값
     * @return IP 리스트
     */
    @Override
    public List<AttendanceIpVO> findIpList(String comId, String type, String value) {
        return attendanceIpMapper.findIpList(comId, type, value);
    }

    /**
     * IP 단건 조회
     * @param ipCode - IP 관리코드
     * @param comId - 회사ID
     * @return IP 정보
     */
    @Override
    public AttendanceIpVO findIpDetail(String ipCode, String comId) {
        return attendanceIpMapper.findIpDetail(ipCode, comId);
    }

    /**
     * IP 등록
     * @param vo - IP 정보
     * @return 등록 결과
     */
    @Override
    public int insertIp(AttendanceIpVO vo) {
        vo.setComId("COM-101");
        vo.setRegistDate(LocalDateTime.now());
        return attendanceIpMapper.insertIp(vo);
    }

    /**
     * IP 수정
     * @param vo - IP 정보
     * @return 수정 결과
     */
    @Override
    public int updateIp(AttendanceIpVO vo) {
        vo.setUpdateDate(LocalDateTime.now());
        return attendanceIpMapper.updateIp(vo);
    }

    /**
     * IP 삭제
     * @param ipCode - IP 관리코드
     * @param comId - 회사ID
     * @return 삭제 결과
     */
    @Override
    public int deleteIp(String ipCode, String comId) {
        return attendanceIpMapper.deleteIp(ipCode, comId);
    }
} 