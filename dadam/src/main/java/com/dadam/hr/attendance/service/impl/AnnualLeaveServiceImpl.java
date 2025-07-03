package com.dadam.hr.attendance.service.impl;

import com.dadam.hr.attendance.mapper.AnnualLeaveMapper;
import com.dadam.hr.attendance.service.AnnualLeaveService;
import com.dadam.hr.attendance.service.AnnualLeaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 연차 서비스 구현체
 */
@Service
public class AnnualLeaveServiceImpl implements AnnualLeaveService {

    /** 연차 Mapper */
    @Autowired
    private AnnualLeaveMapper annualLeaveMapper;

    /**
     * 연차 신청 처리
     * @param vo - 연차 정보
     */
    @Override
    public void requestAnnualLeave(AnnualLeaveVO vo) throws Exception {
        vo.setStatus("대기");
        vo.setCreatedAt(LocalDateTime.now());
        annualLeaveMapper.insertAnnualLeave(vo);
    }

    /**
     * 연차 승인/반려 처리
     * @param leaveCode - 연차코드
     * @param status - 상태코드
     * @param approveId - 승인자ID
     */
    @Override
    public void approveAnnualLeave(String leaveCode, String status, String approveId) throws Exception {
        AnnualLeaveVO vo = annualLeaveMapper.selectAnnualLeave(leaveCode, null);
        vo.setStatus(status);
        vo.setApproveId(approveId);
        vo.setApproveDate(LocalDate.now());
        vo.setUpdatedAt(LocalDateTime.now());
        annualLeaveMapper.updateAnnualLeaveStatus(vo);
    }

    /**
     * 연차 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 연차 리스트
     */
    @Override
    public List<AnnualLeaveVO> getAnnualLeaveList(String comId, String empId) {
        return annualLeaveMapper.findAnnualLeaveList(comId, empId);
    }

    /**
     * 잔여 연차 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 잔여 연차
     */
    @Override
    public double getRemainAnnualLeave(String empId, String comId) {
        return annualLeaveMapper.getRemainAnnualLeave(empId, comId);
    }
} 