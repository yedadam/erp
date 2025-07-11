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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

import com.dadam.hr.salary.service.SalaryCalculationVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * 급여 계산 REST 컨트롤러
 * - 근태 연동 급여 계산
 * - 급여 지급 처리
 * - 권한별 기능 제어
 */
@Slf4j
@RestController
@RequestMapping("/erp/hr")
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
    @PostMapping("/salary/calculate")
    public SalaryStatementVO calculateSalary(@RequestParam String empId, @RequestParam String payMonth) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자 또는 본인만 계산 가능
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return null; // 권한 없음
        }
        
        return salaryCalculationService.getSalaryStatement(empId, payMonth);
    }

    /**
     * 급여 지급 처리 (관리자만)
     * @param statementId - 급여명세서 ID
     * @return 지급 결과
     */
    @PostMapping("/salary/payment")
    public String processSalaryPayment(@RequestParam Long statementId) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return "unauthorized";
        }
        
        Map<String, String> userInfo = getCurrentUserInfo();
        return salaryCalculationService.processSalaryPayment(statementId);
    }

    /**
     * 급여 지급 승인/반려 (관리자만)
     * @param paymentId - 지급 ID
     * @param status - 상태 (APPROVED/REJECTED)
     * @param rejectReason - 반려사유 (반려시 필수)
     * @return 처리 결과
     */
    @PostMapping("/salary/approve")
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
        
        return salaryCalculationService.approveSalaryPayment(paymentId, status, rejectReason);
    }

    /**
     * 급여 통계 조회 (관리자만)
     * @param deptCode - 부서코드 (선택사항)
     * @param payMonth - 지급년월
     * @return 통계 정보
     */
    @GetMapping("/salary/statistics")
    public Map<String, Object> getSalaryStatistics(@RequestParam(required = false) String deptCode,
                                                   @RequestParam String payMonth) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return null;
        }
        
        Map<String, String> userInfo = getCurrentUserInfo();
        return salaryCalculationService.getSalaryStatistics(deptCode, payMonth);
    }

    /**
     * 기본급 계산
     * @param empId - 사원번호
     * @return 기본급
     */
    @GetMapping("/salary/baseSalary")
    public double getBaseSalary(@RequestParam String empId) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자 또는 본인만 조회 가능
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return 0.0; // 권한 없음
        }
        
        return salaryCalculationService.getBaseSalary(empId);
    }

    /**
     * 근무일수 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @return 근무일수 정보
     */
    @GetMapping("/salary/workDays")
    public Map<String, Object> getWorkDays(@RequestParam String empId, @RequestParam String payMonth) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자 또는 본인만 조회 가능
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return null; // 권한 없음
        }
        
        return salaryCalculationService.getWorkDays(empId, payMonth);
    }

    /**
     * 지각/조퇴 공제 계산
     * @param empId - 사원번호
     * @param payMonth - 지급년월
     * @return 공제 정보
     */
    @GetMapping("/salary/lateEarlyDeduction")
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
    @GetMapping("/salary/annualLeavePay")
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
    @GetMapping("/salary/overtimePay")
    public double getOvertimePay(@RequestParam String empId, @RequestParam String payMonth) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자 또는 본인만 조회 가능
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return 0.0; // 권한 없음
        }
        
        return salaryCalculationService.calculateOvertimePay(empId, payMonth, userInfo.get("comId"));
    }

    /**
     * 월별 급여 일괄 계산 실행
     * 
     * @param request 계산 요청 정보
     * @return 계산 결과
     */
    @PostMapping("/salary/monthly")
    public ResponseEntity<Map<String, Object>> calculateMonthlySalary(@RequestBody Map<String, Object> request) {
        log.info("월별 급여 일괄 계산 요청 - 회사ID: {}, 년월: {}", 
                request.get("companyId"), request.get("yearMonth"));
        
        try {
            Long companyId = Long.valueOf(request.get("companyId").toString());
            String yearMonth = request.get("yearMonth").toString();
            
            // 급여 계산 실행
            int calculatedCount = salaryCalculationService.calculateMonthlySalary(companyId, yearMonth);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "급여 계산이 완료되었습니다.");
            response.put("calculatedCount", calculatedCount);
            response.put("companyId", companyId);
            response.put("yearMonth", yearMonth);
            
            log.info("월별 급여 일괄 계산 완료 - 계산된 건수: {}", calculatedCount);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("월별 급여 일괄 계산 중 오류 발생", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "급여 계산 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 개별 사원 급여 계산
     * 
     * @param request 계산 요청 정보
     * @return 계산 결과
     */
    @PostMapping("/salary/employee")
    public ResponseEntity<Map<String, Object>> calculateEmployeeSalary(@RequestBody Map<String, Object> request) {
        log.info("개별 사원 급여 계산 요청 - 사원ID: {}, 년월: {}", 
                request.get("employeeId"), request.get("yearMonth"));
        
        try {
            Long companyId = Long.valueOf(request.get("companyId").toString());
            Long employeeId = Long.valueOf(request.get("employeeId").toString());
            String yearMonth = request.get("yearMonth").toString();
            
            // 개별 사원 급여 계산
            SalaryCalculationVO calculationResult = salaryCalculationService.calculateEmployeeSalary(
                companyId, employeeId, yearMonth);
            
            Map<String, Object> response = new HashMap<>();
            
            if (calculationResult != null) {
                response.put("success", true);
                response.put("message", "급여 계산이 완료되었습니다.");
                response.put("calculation", calculationResult);
            } else {
                response.put("success", false);
                response.put("message", "급여 계산 대상이 아닙니다.");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("개별 사원 급여 계산 중 오류 발생", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "급여 계산 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * 급여 지급 이력 조회 (권한별 필터링)
     * @param empId - 사원번호 (선택사항)
     * @param fromMonth - 시작년월
     * @param toMonth - 종료년월
     * @return 지급 이력
     */
    @GetMapping("/salary/history")
    public List<Map<String, Object>> getSalaryPaymentHistory(@RequestParam(required = false) String empId,
                                                             @RequestParam String fromMonth,
                                                             @RequestParam String toMonth) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자가 아닌 경우 본인 이력만 조회 가능
        if (!isAdmin()) {
            empId = userInfo.get("empId");
        }
        
        return salaryCalculationService.getSalaryPaymentHistory(empId, fromMonth, toMonth);
    }

    /**
     * 급여 목록 조회 (관리자용)
     * @param yearMonth - 지급년월
     * @param status - 상태
     * @param employeeName - 사원명
     * @return 급여 목록
     */
    @GetMapping("/salary/list")
    public ResponseEntity<Map<String, Object>> getSalaryList(@RequestParam(required = false) String yearMonth,
                                                             @RequestParam(required = false) String status,
                                                             @RequestParam(required = false) String employeeName) {
        Map<String, String> userInfo = getCurrentUserInfo();
        
        try {
            List<Map<String, Object>> salaryList = salaryCalculationService.getSalaryList(
                userInfo.get("comId"), yearMonth, status, employeeName);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", salaryList);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("급여 목록 조회 중 오류 발생", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "급여 목록 조회 중 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 급여 일괄 승인 (관리자만)
     * @param request - 요청 파라미터
     * @return 처리 결과
     */
    @PostMapping("/salary/approve/batch")
    public ResponseEntity<Map<String, Object>> approveBatchSalaries(@RequestBody Map<String, Object> request) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "권한이 없습니다.");
            return ResponseEntity.status(403).body(response);
        }
        
        try {
            Map<String, String> userInfo = getCurrentUserInfo();
            String yearMonth = (String) request.get("yearMonth");
            
            int result = salaryCalculationService.approveBatchSalaries(
                Long.valueOf(userInfo.get("comId")), yearMonth);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", result + "건의 급여가 승인되었습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("급여 일괄 승인 중 오류 발생", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "급여 일괄 승인 중 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 급여 일괄 지급 (관리자만)
     * @param request - 요청 파라미터
     * @return 처리 결과
     */
    @PostMapping("/salary/pay/batch")
    public ResponseEntity<Map<String, Object>> payBatchSalaries(@RequestBody Map<String, Object> request) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "권한이 없습니다.");
            return ResponseEntity.status(403).body(response);
        }
        
        try {
            Map<String, String> userInfo = getCurrentUserInfo();
            String yearMonth = (String) request.get("yearMonth");
            
            int result = salaryCalculationService.payBatchSalaries(
                Long.valueOf(userInfo.get("comId")), yearMonth);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", result + "건의 급여가 지급되었습니다.");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("급여 일괄 지급 중 오류 발생", e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "급여 일괄 지급 중 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 급여 엑셀 다운로드 (관리자만)
     * @param yearMonth - 지급년월
     * @param status - 상태
     * @return 엑셀 파일
     */
    @GetMapping("/salary/export")
    public ResponseEntity<byte[]> exportSalaryExcel(@RequestParam(required = false) String yearMonth,
                                                   @RequestParam(required = false) String status) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return ResponseEntity.status(403).build();
        }
        
        try {
            Map<String, String> userInfo = getCurrentUserInfo();
            byte[] excelData = salaryCalculationService.exportSalaryExcel(
                userInfo.get("comId"), yearMonth, status);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "급여대장_" + yearMonth + ".xlsx");
            
            return ResponseEntity.ok()
                .headers(headers)
                .body(excelData);
                
        } catch (Exception e) {
            log.error("급여 엑셀 다운로드 중 오류 발생", e);
            return ResponseEntity.status(500).build();
        }
    }
} 