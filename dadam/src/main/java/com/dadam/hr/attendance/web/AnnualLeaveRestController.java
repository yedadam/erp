package com.dadam.hr.attendance.web;

import com.dadam.hr.attendance.service.AnnualLeaveService;
import com.dadam.hr.attendance.service.AnnualLeaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 연차 관리 REST 컨트롤러
 */
@RestController
@RequestMapping("/erp/hr")
public class AnnualLeaveRestController {

    /** 연차 서비스 */
    @Autowired
    private AnnualLeaveService annualLeaveService;

    /**
     * 연차 신청
     * @param vo - 연차 정보
     * @param request - 요청 객체
     * @return 처리 결과
     */
    @PostMapping("/annualLeaveRequest")
    public String requestAnnualLeave(@RequestBody AnnualLeaveVO vo, HttpServletRequest request) {
        try {
            annualLeaveService.requestAnnualLeave(vo);
            return "연차신청 완료";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 연차 승인/반려
     * @param leaveCode - 연차코드
     * @param status - 상태코드
     * @param approveId - 승인자ID
     * @return 처리 결과
     */
    @PostMapping("/annualLeaveApprove")
    public String approveAnnualLeave(@RequestParam String leaveCode, @RequestParam String status, @RequestParam String approveId) {
        try {
            annualLeaveService.approveAnnualLeave(leaveCode, status, approveId);
            return "처리 완료";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 연차 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 연차 리스트
     */
    @GetMapping("/annualLeaveList")
    public List<AnnualLeaveVO> getAnnualLeaveList(@RequestParam String comId, @RequestParam String empId) {
        return annualLeaveService.getAnnualLeaveList(comId, empId);
    }

    /**
     * 잔여 연차 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 잔여 연차
     */
    @GetMapping("/annualLeaveRemain")
    public double getRemainAnnualLeave(@RequestParam String empId, @RequestParam String comId) {
        return annualLeaveService.getRemainAnnualLeave(empId, comId);
    }
} 