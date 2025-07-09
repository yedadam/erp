package com.dadam.hr.salary.service.impl;

import com.dadam.hr.salary.mapper.SalaryListMapper;
import com.dadam.hr.salary.service.SalaryListService;
import com.dadam.hr.salary.service.SalaryListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 급여명세 리스트 관리 Service 구현체
 * 
 * @author 팀장
 * @since 2024-01-01
 */
@Service
@Transactional
public class SalaryListServiceImpl implements SalaryListService {

    @Autowired
    private SalaryListMapper salaryListMapper;

    /**
     * 급여명세 목록 조회
     * 
     * @param searchVO 검색 조건
     * @return 급여명세 목록
     */
    @Override
    public List<SalaryListVO> getSalaryList(SalaryListVO searchVO) {
        return salaryListMapper.selectSalaryList(searchVO);
    }

    /**
     * 급여명세 일괄승인
     * 
     * @param salaryIds 승인할 급여명세 ID 목록
     * @return 처리된 건수
     */
    @Override
    public int approveSalaryBatch(List<String> salaryIds) {
        String approveDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String approveUser = "SYSTEM"; // TODO: 실제 로그인 사용자 정보로 변경
        
        int result = 0;
        for (String salaryId : salaryIds) {
            SalaryListVO updateVO = new SalaryListVO();
            updateVO.setSalaryId(salaryId);
            updateVO.setStatus("APPROVED");
            updateVO.setApproveDate(approveDate);
            updateVO.setApproveUser(approveUser);
            
            result += salaryListMapper.updateSalaryStatus(updateVO);
        }
        
        return result;
    }

    /**
     * 급여명세 일괄지급
     * 
     * @param salaryIds 지급할 급여명세 ID 목록
     * @return 처리된 건수
     */
    @Override
    public int paySalaryBatch(List<String> salaryIds) {
        String payDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String payUser = "SYSTEM"; // TODO: 실제 로그인 사용자 정보로 변경
        
        int result = 0;
        for (String salaryId : salaryIds) {
            SalaryListVO updateVO = new SalaryListVO();
            updateVO.setSalaryId(salaryId);
            updateVO.setStatus("PAID");
            updateVO.setPayDate(payDate);
            updateVO.setPayUser(payUser);
            
            result += salaryListMapper.updateSalaryStatus(updateVO);
        }
        
        return result;
    }

    /**
     * 급여명세 개별승인
     * 
     * @param salaryId 승인할 급여명세 ID
     * @return 처리된 건수
     */
    @Override
    public int approveSalary(String salaryId) {
        String approveDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String approveUser = "SYSTEM"; // TODO: 실제 로그인 사용자 정보로 변경
        
        SalaryListVO updateVO = new SalaryListVO();
        updateVO.setSalaryId(salaryId);
        updateVO.setStatus("APPROVED");
        updateVO.setApproveDate(approveDate);
        updateVO.setApproveUser(approveUser);
        
        return salaryListMapper.updateSalaryStatus(updateVO);
    }

    /**
     * 급여명세 개별지급
     * 
     * @param salaryId 지급할 급여명세 ID
     * @return 처리된 건수
     */
    @Override
    public int paySalary(String salaryId) {
        String payDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String payUser = "SYSTEM"; // TODO: 실제 로그인 사용자 정보로 변경
        
        SalaryListVO updateVO = new SalaryListVO();
        updateVO.setSalaryId(salaryId);
        updateVO.setStatus("PAID");
        updateVO.setPayDate(payDate);
        updateVO.setPayUser(payUser);
        
        return salaryListMapper.updateSalaryStatus(updateVO);
    }

    /**
     * 급여명세 삭제
     * 
     * @param salaryId 삭제할 급여명세 ID
     * @return 처리된 건수
     */
    @Override
    public int deleteSalary(String salaryId) {
        return salaryListMapper.deleteSalary(salaryId);
    }
} 