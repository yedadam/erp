package com.dadam.hr.attendance.web;

import com.dadam.hr.attendance.service.AttendanceCorrectionService;
import com.dadam.hr.attendance.service.AttendanceCorrectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 근태 정정 REST 컨트롤러
 */
@RestController
@RequestMapping("/erp/hr")
public class AttendanceCorrectionRestController {

    /** 정정 서비스 */
    @Autowired
    private AttendanceCorrectionService correctionService;

    /**
     * 정정요청
     * @param vo - 정정 정보
     * @param request - 요청 객체
     * @return 처리 결과
     */
    @PostMapping("/attendanceCorrectionRequest")
    public String requestCorrection(@RequestBody AttendanceCorrectionVO vo, HttpServletRequest request) {
        try {
            correctionService.requestCorrection(vo);
            return "정정요청 완료";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 정정 승인/반려
     * @param corrCode - 정정코드
     * @param status - 상태코드
     * @param approverId - 승인자ID
     * @return 처리 결과
     */
    @PostMapping("/attendanceCorrectionApprove")
    public String approveCorrection(@RequestParam String corrCode, @RequestParam String status, @RequestParam String approverId) {
        try {
            correctionService.approveCorrection(corrCode, status, approverId);
            return "처리 완료";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 정정 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 정정 리스트
     */
    @GetMapping("/attendanceCorrectionList")
    public List<AttendanceCorrectionVO> getCorrectionList(@RequestParam String comId, @RequestParam String empId) {
        return correctionService.getCorrectionList(comId, empId);
    }
} 