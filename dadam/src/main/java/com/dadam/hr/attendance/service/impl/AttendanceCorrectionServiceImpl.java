package com.dadam.hr.attendance.service.impl;

import com.dadam.hr.attendance.mapper.AttendanceCorrectionMapper;
import com.dadam.hr.attendance.service.AttendanceCorrectionService;
import com.dadam.hr.attendance.service.AttendanceCorrectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 근태 정정 서비스 구현체
 */
@Service
public class AttendanceCorrectionServiceImpl implements AttendanceCorrectionService {

    /** 정정 Mapper */
    @Autowired
    private AttendanceCorrectionMapper correctionMapper;

    /**
     * 정정요청 처리
     * @param vo - 정정 정보
     */
    @Override
    public void requestCorrection(AttendanceCorrectionVO vo) throws Exception {
        vo.setStatus("대기");
        vo.setReqDate(LocalDate.now());
        vo.setCreatedAt(LocalDateTime.now());
        correctionMapper.insertCorrection(vo);
    }

    /**
     * 정정 승인/반려 처리
     * @param corrCode - 정정코드
     * @param status - 상태코드
     * @param approverId - 승인자ID
     */
    @Override
    public void approveCorrection(String corrCode, String status, String approverId) throws Exception {
        AttendanceCorrectionVO vo = correctionMapper.selectCorrection(corrCode, null);
        vo.setStatus(status);
        vo.setApproverId(approverId);
        vo.setApproveDate(LocalDate.now());
        vo.setUpdatedAt(LocalDateTime.now());
        correctionMapper.updateCorrectionStatus(vo);
    }

    @Override
    public void approveCorrection(String corrCode, String status, String approverId, String note) throws Exception {
        AttendanceCorrectionVO vo = correctionMapper.selectCorrection(corrCode, null);
        vo.setStatus(status);
        vo.setApproverId(approverId);
        vo.setNote(note);
        vo.setApproveDate(LocalDate.now());
        vo.setUpdatedAt(LocalDateTime.now());
        correctionMapper.updateCorrectionStatus(vo);
    }

    /**
     * 정정 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 정정 리스트
     */
    @Override
    public List<AttendanceCorrectionVO> getCorrectionList(String comId, String empId) {
        return correctionMapper.findCorrectionList(comId, empId);
    }

    @Override
    public List<AttendanceCorrectionVO> getCorrectionList(String comId, String empId, String status) {
        return correctionMapper.findCorrectionListWithStatus(comId, empId, status);
    }

    @Override
    public AttendanceCorrectionVO getCorrectionDetail(String corrCode) {
        return correctionMapper.selectCorrection(corrCode, null);
    }
} 