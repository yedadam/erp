package com.dadam.hr.attendance.service.impl;

import com.dadam.hr.attendance.mapper.AnnualLeaveMapper;
import com.dadam.hr.attendance.service.AnnualLeaveService;
import com.dadam.hr.attendance.service.AnnualLeaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    
    // === 스케줄러에서 사용하는 메서드들 ===
    
    /**
     * 연차 생성
     * @param vo - 연차 정보
     * @return 생성된 연차 정보
     */
    @Override
    public AnnualLeaveVO createAnnualLeave(AnnualLeaveVO vo) throws Exception {
        vo.setCreatedAt(LocalDateTime.now());
        vo.setUpdatedAt(LocalDateTime.now());
        annualLeaveMapper.insertAnnualLeave(vo);
        return vo;
    }
    
    /**
     * 연차 수정
     * @param vo - 연차 정보
     * @return 수정된 연차 정보
     */
    @Override
    public AnnualLeaveVO updateAnnualLeave(AnnualLeaveVO vo) throws Exception {
        vo.setUpdatedAt(LocalDateTime.now());
        annualLeaveMapper.updateAnnualLeave(vo);
        return vo;
    }
    
    /**
     * 사원별 연차 조회 (연도별)
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param year - 연도
     * @return 연차 정보
     */
    @Override
    public AnnualLeaveVO getAnnualLeaveByEmpAndYear(String empId, String comId, int year) {
        return annualLeaveMapper.selectAnnualLeaveByEmpAndYear(empId, comId, year);
    }
    
    /**
     * 만료된 연차 조회
     * @param date - 기준일
     * @return 만료된 연차 리스트
     */
    @Override
    public List<AnnualLeaveVO> getExpiredAnnualLeaves(LocalDate date) {
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("date", date.toString());
        return annualLeaveMapper.selectExpiredAnnualLeaves(param);
    }
    
    /**
     * 미사용 연차 사원 조회
     * @param date - 기준일
     * @return 미사용 연차 사원 리스트
     */
    @Override
    public List<AnnualLeaveVO> getUnusedLeaveEmployees(LocalDate date) {
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("date", date.toString());
        return annualLeaveMapper.selectUnusedLeaveEmployees(param);
    }
    
    /**
     * 오늘 사용된 연차 조회
     * @param date - 기준일
     * @return 오늘 사용된 연차 리스트
     */
    @Override
    public List<AnnualLeaveVO> getUsedLeavesToday(LocalDate date) {
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("date", date.toString());
        return annualLeaveMapper.selectUsedLeavesToday(param);
    }
    
    /**
     * 월별 연차 통계 생성
     * @param date - 기준일
     * @return 통계 데이터
     */
    @Override
    public java.util.Map<String, Object> generateMonthlyLeaveStatistics(LocalDate date) {
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("date", date.toString());
        return annualLeaveMapper.generateMonthlyLeaveStatistics(param);
    }
    
    /**
     * 월별 연차 통계 저장
     * @param statistics - 통계 데이터
     * @return 저장 결과
     */
    @Override
    public boolean saveMonthlyLeaveStatistics(Map<String, Object> statistics) {
        return annualLeaveMapper.insertMonthlyLeaveStatistics(statistics) > 0;
    }
} 