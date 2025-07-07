package com.dadam.hr.salary.web;

import com.dadam.hr.salary.service.SalaryCalculationService;
import com.dadam.hr.salary.service.SalaryStatementVO;
import com.dadam.hr.salary.service.SalaryPaymentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dadam.security.service.LoginUserAuthority;
import com.dadam.security.service.LoginMainAuthority;

import java.util.List;
import java.util.Map;

/**
 * 급여 계산 REST 컨트롤러
 * - 근태 연동 급여 계산
 * - 급여 지급 처리
 * - 권한별 기능 제어
 */
@RestController
@RequestMapping("/erp/salary/calculation")
public class SalaryCalculationController {

    @Autowired
    private SalaryCalculationService salaryCalculationService;

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
     * 급여 계산 (근태 연동)
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @return 계산된 급여 정보
     */
    @PostMapping("/calculate")
    public SalaryStatementVO calculateSalary(@RequestParam String empId, @RequestParam String payMonth) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자 또는 본인만 계산 가능
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return null; // 권한 없음
        }
        
        return salaryCalculationService.calculateSalary(empId, payMonth, userInfo.get("comId"));
    }

    /**
     * 급여 지급 처리 (관리자만)
     * @param statementId - 급여명세서 ID
     * @return 지급 결과
     */
    @PostMapping("/payment")
    public String processSalaryPayment(@RequestParam Long statementId) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return "unauthorized";
        }
        
        Map<String, String> userInfo = getCurrentUserInfo();
        return salaryCalculationService.processSalaryPayment(statementId, userInfo.get("comId"));
    }

    /**
     * 급여 지급 승인/반려 (관리자만)
     * @param paymentId - 지급 ID
     * @param status - 상태 (APPROVED/REJECTED)
     * @param rejectReason - 반려사유 (반려시 필수)
     * @return 처리 결과
     */
    @PostMapping("/approve")
    public String approveSalaryPayment(@RequestParam Long paymentId, 
                                      @RequestParam String status,
                                      @RequestParam(required = false) String rejectReason) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return "unauthorized";
        }
        
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 반려시 반려사유 필수
        if ("REJECTED".equals(status) && (rejectReason == null || rejectReason.trim().isEmpty())) {
            return "reject_reason_required";
        }
        
        return salaryCalculationService.approveSalaryPayment(paymentId, status, userInfo.get("empId"), rejectReason);
    }

    /**
     * 급여 통계 조회 (관리자만)
     * @param deptCode - 부서코드 (선택사항)
     * @param payMonth - 지급년월
     * @return 통계 정보
     */
    @GetMapping("/statistics")
    public Map<String, Object> getSalaryStatistics(@RequestParam(required = false) String deptCode,
                                                   @RequestParam String payMonth) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return null;
        }
        
        Map<String, String> userInfo = getCurrentUserInfo();
        return salaryCalculationService.getSalaryStatistics(userInfo.get("comId"), deptCode, payMonth);
    }

    /**
     * 급여 지급 이력 조회 (권한별 필터링)
     * @param empId - 사원번호 (선택사항)
     * @param fromMonth - 시작년월
     * @param toMonth - 종료년월
     * @return 지급 이력
     */
    @GetMapping("/history")
    public List<Map<String, Object>> getSalaryPaymentHistory(@RequestParam(required = false) String empId,
                                                             @RequestParam String fromMonth,
                                                             @RequestParam String toMonth) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자가 아닌 경우 본인 이력만 조회 가능
        if (!isAdmin()) {
            empId = userInfo.get("empId");
        }
        
        return salaryCalculationService.getSalaryPaymentHistory(userInfo.get("comId"), empId, fromMonth, toMonth);
    }

    /**
     * 기본급 계산
     * @param empId - 사원번호
     * @return 기본급
     */
    @GetMapping("/baseSalary")
    public double getBaseSalary(@RequestParam String empId) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자 또는 본인만 조회 가능
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return 0.0; // 권한 없음
        }
        
        return salaryCalculationService.calculateBaseSalary(empId, userInfo.get("comId"));
    }

    /**
     * 근무일수 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @return 근무일수 정보
     */
    @GetMapping("/workDays")
    public Map<String, Object> getWorkDays(@RequestParam String empId, @RequestParam String payMonth) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자 또는 본인만 조회 가능
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return null; // 권한 없음
        }
        
        return salaryCalculationService.calculateWorkDays(empId, payMonth, userInfo.get("comId"));
    }

    /**
     * 지각/조퇴 공제 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @return 공제 정보
     */
    @GetMapping("/lateEarlyDeduction")
    public Map<String, Object> getLateEarlyDeduction(@RequestParam String empId, @RequestParam String payMonth) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자 또는 본인만 조회 가능
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return null; // 권한 없음
        }
        
        return salaryCalculationService.calculateLateEarlyDeduction(empId, payMonth, userInfo.get("comId"));
    }

    /**
     * 연차수당 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @return 연차수당
     */
    @GetMapping("/annualLeavePay")
    public double getAnnualLeavePay(@RequestParam String empId, @RequestParam String payMonth) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자 또는 본인만 조회 가능
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return 0.0; // 권한 없음
        }
        
        return salaryCalculationService.calculateAnnualLeavePay(empId, payMonth, userInfo.get("comId"));
    }

    /**
     * 연장근무수당 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @return 연장근무수당
     */
    @GetMapping("/overtimePay")
    public double getOvertimePay(@RequestParam String empId, @RequestParam String payMonth) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자 또는 본인만 조회 가능
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return 0.0; // 권한 없음
        }
        
        return salaryCalculationService.calculateOvertimePay(empId, payMonth, userInfo.get("comId"));
    }
} 