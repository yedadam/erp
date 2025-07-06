package com.dadam.hr.attendance.web;

import com.dadam.hr.attendance.service.AttendanceCorrectionService;
import com.dadam.hr.attendance.service.AttendanceCorrectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dadam.security.service.LoginUserAuthority;
import com.dadam.security.service.LoginMainAuthority;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 근태 정정 REST 컨트롤러
 * - 권한별 기능 제어: 정정 요청은 본인만, 승인은 관리자만
 * - 승인/반려 처리: 관리자 권한 확인
 * - 정정 이력 관리: 정정 요청/승인 이력 추적
 */
@RestController
@RequestMapping("/erp/hr")
public class AttendanceCorrectionRestController {

    /** 정정 서비스 */
    @Autowired
    private AttendanceCorrectionService correctionService;

    /**
     * 현재 사용자의 권한 정보를 가져오는 메서드
     * @return 권한 정보 (comId, deptCode, authority, empId)
     */
    private Map<String, String> getCurrentUserInfo() {
        Map<String, String> userInfo = new java.util.HashMap<>();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        if (principal instanceof LoginUserAuthority) {
            LoginUserAuthority user = (LoginUserAuthority) principal;
            userInfo.put("comId", user.getComId());
            userInfo.put("deptCode", user.getDeptCode());
            userInfo.put("authority", user.getOptionCode());
            userInfo.put("empId", user.getUserId());
        } else if (principal instanceof LoginMainAuthority) {
            LoginMainAuthority user = (LoginMainAuthority) principal;
            userInfo.put("comId", user.getComId());
            userInfo.put("deptCode", user.getDeptCode());
            userInfo.put("authority", user.getAuthority());
            userInfo.put("empId", "");
        }
        
        return userInfo;
    }

    /**
     * 관리자 권한 확인
     * @return 관리자 여부
     */
    private boolean isAdmin() {
        Map<String, String> userInfo = getCurrentUserInfo();
        String authority = userInfo.get("authority");
        return "admin".equals(authority) || "master".equals(authority);
    }

    /**
     * 정정요청 (본인만 가능)
     * @param vo - 정정 정보
     * @param request - 요청 객체
     * @return 처리 결과
     */
    @PostMapping("/attendanceCorrectionRequest")
    public String requestCorrection(@RequestBody AttendanceCorrectionVO vo, HttpServletRequest request) {
        try {
            Map<String, String> userInfo = getCurrentUserInfo();
            
            // 본인만 정정 요청 가능
            if (!vo.getEmpId().equals(userInfo.get("empId"))) {
                return "unauthorized";
            }
            
            // 정정 사유 필수
            if (vo.getCorrectionReason() == null || vo.getCorrectionReason().trim().isEmpty()) {
                return "correction_reason_required";
            }
            
            correctionService.requestCorrection(vo);
            return "정정요청 완료";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    /**
     * 정정 승인/반려 (관리자만 가능)
     * @param corrCode - 정정코드
     * @param status - 상태코드 (APPROVED/REJECTED)
     * @param approverId - 승인자ID
     * @param rejectReason - 반려사유 (반려시 필수)
     * @return 처리 결과
     */
    @PostMapping("/attendanceCorrectionApprove")
    public String approveCorrection(@RequestParam String corrCode, 
                                   @RequestParam String status, 
                                   @RequestParam String approverId,
                                   @RequestParam(required = false) String rejectReason) {
        try {
            // 관리자 권한 확인
            if (!isAdmin()) {
                return "unauthorized";
            }
            
            // 반려시 반려사유 필수
            if ("REJECTED".equals(status) && (rejectReason == null || rejectReason.trim().isEmpty())) {
                return "reject_reason_required";
            }
            
            correctionService.approveCorrection(corrCode, status, approverId, rejectReason);
            return "처리 완료";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    /**
     * 정정 목록 조회 (권한별 필터링)
     * @param comId - 회사ID
     * @param empId - 사원번호 (선택사항)
     * @param status - 상태코드 (선택사항)
     * @return 정정 리스트
     */
    @GetMapping("/attendanceCorrectionList")
    public List<AttendanceCorrectionVO> getCorrectionList(@RequestParam String comId, 
                                                          @RequestParam(required = false) String empId,
                                                          @RequestParam(required = false) String status) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자가 아닌 경우 본인 정정만 조회 가능
        if (!isAdmin()) {
            empId = userInfo.get("empId");
        }
        
        return correctionService.getCorrectionList(comId, empId, status);
    }

    /**
     * 정정 상세 조회 (본인 또는 관리자만)
     * @param corrCode - 정정코드
     * @return 정정 상세 정보
     */
    @GetMapping("/attendanceCorrectionDetail")
    public AttendanceCorrectionVO getCorrectionDetail(@RequestParam String corrCode) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        AttendanceCorrectionVO correction = correctionService.getCorrectionDetail(corrCode);
        
        // 본인 또는 관리자만 조회 가능
        if (correction != null && 
            (isAdmin() || correction.getEmpId().equals(userInfo.get("empId")))) {
            return correction;
        }
        
        return null; // 권한 없음
    }

    /**
     * 승인 대기 정정 목록 조회 (관리자만)
     * @param comId - 회사ID
     * @return 승인 대기 정정 리스트
     */
    @GetMapping("/pendingCorrectionList")
    public List<AttendanceCorrectionVO> getPendingCorrectionList(@RequestParam String comId) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return null;
        }
        
        return correctionService.getCorrectionList(comId, null, "PENDING");
    }
} 