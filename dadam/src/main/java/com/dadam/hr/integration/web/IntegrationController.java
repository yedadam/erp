package com.dadam.hr.integration.web;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dadam.hr.integration.service.IntegrationService;

/**
 * 통합 관리 컨트롤러
 * - 인사/근태/급여 등 통합 기능 담당
 */
@Controller
@RequestMapping("/erp/hr")
public class IntegrationController {
    
    /** 통합 서비스 */
    @Autowired
    private IntegrationService integrationService;
    
    /**
     * 통합 관리 메인 페이지
     */
    @GetMapping("/integration")
    public String integrationMain(HttpSession session, Model model) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return "redirect:/error/403";
        }
        
        // 시스템 상태 조회
        Map<String, Object> systemStatus = integrationService.getSystemStatus(comId);
        model.addAttribute("systemStatus", systemStatus);
        
        return "hr/integration/main";
    }
    
    /**
     * 시스템 상태 확인 (Ajax)
     */
    @PostMapping("/status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSystemStatus(HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body(Map.of("error", "권한이 없습니다."));
        }
        
        Map<String, Object> result = integrationService.getSystemStatus(comId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 사원 연동 검증 (Ajax)
     */
    @PostMapping("/validate/employee")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> validateEmployee(
            @RequestParam String empId,
            HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body(Map.of("error", "권한이 없습니다."));
        }
        
        Map<String, Object> result = integrationService.validateEmployeeIntegration(empId, comId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 근태 연동 검증 (Ajax)
     */
    @PostMapping("/validate/attendance")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> validateAttendance(
            @RequestParam String empId,
            @RequestParam String month,
            HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body(Map.of("error", "권한이 없습니다."));
        }
        
        Map<String, Object> result = integrationService.validateAttendanceIntegration(empId, comId, month);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 급여 연동 검증 (Ajax)
     */
    @PostMapping("/validate/salary")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> validateSalary(
            @RequestParam String empId,
            @RequestParam String payMonth,
            HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body(Map.of("error", "권한이 없습니다."));
        }
        
        Map<String, Object> result = integrationService.validateSalaryIntegration(empId, comId, payMonth);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 전체 연동 테스트 실행 (Ajax)
     */
    @PostMapping("/test/integration")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> runIntegrationTest(HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body(Map.of("error", "권한이 없습니다."));
        }
        
        Map<String, Object> result = integrationService.runIntegrationTest(comId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 권한별 기능 테스트 (Ajax)
     */
    @PostMapping("/test/authority")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> testAuthorityFunctions(
            @RequestParam String empId,
            @RequestParam String authority,
            HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String currentAuthority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(currentAuthority)) {
            return ResponseEntity.badRequest().body(Map.of("error", "권한이 없습니다."));
        }
        
        Map<String, Object> result = integrationService.testAuthorityFunctions(comId, empId, authority);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 데이터 정합성 테스트 (Ajax)
     */
    @PostMapping("/test/consistency")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> testDataConsistency(HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body(Map.of("error", "권한이 없습니다."));
        }
        
        Map<String, Object> result = integrationService.testDataConsistency(comId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 대시보드 페이지
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return "redirect:/error/403";
        }
        
        // 대시보드 데이터 조회
        Map<String, Object> dashboardData = integrationService.getDashboardData(comId, null);
        model.addAttribute("dashboardData", dashboardData);
        
        return "hr/integration/dashboard";
    }
    
    /**
     * 대시보드 데이터 조회 (Ajax)
     */
    @PostMapping("/dashboard/data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getDashboardData(
            @RequestParam(required = false) String deptCode,
            HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body(Map.of("error", "권한이 없습니다."));
        }
        
        Map<String, Object> result = integrationService.getDashboardData(comId, deptCode);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 실시간 현황 조회 (Ajax)
     */
    @PostMapping("/realtime")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getRealtimeStatus(HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body(Map.of("error", "권한이 없습니다."));
        }
        
        Map<String, Object> result = integrationService.getRealtimeStatus(comId);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 부서별 통계 조회 (Ajax)
     */
    @PostMapping("/statistics/department")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getDepartmentStatistics(
            @RequestParam String month,
            HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body(null);
        }
        
        List<Map<String, Object>> result = integrationService.getDepartmentStatistics(comId, month);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 시스템 통계 조회 (Ajax)
     */
    @PostMapping("/statistics/system")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSystemStatistics(
            @RequestParam String month,
            HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body(Map.of("error", "권한이 없습니다."));
        }
        
        Map<String, Object> result = integrationService.getSystemStatistics(comId, month);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 데이터 백업/복원 (Ajax)
     */
    @PostMapping("/backup")
    @ResponseBody
    public ResponseEntity<String> backupRestoreData(
            @RequestParam String operation,
            @RequestParam String backupPath,
            HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body("권한이 없습니다.");
        }
        
        String result = integrationService.backupRestoreData(comId, operation, backupPath);
        return ResponseEntity.ok(result);
    }
    
    /**
     * 시스템 로그 조회 (Ajax)
     */
    @PostMapping("/logs")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getSystemLogs(
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String logType,
            HttpSession session) {
        String comId = (String) session.getAttribute("comId");
        String authority = (String) session.getAttribute("authority");
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(authority)) {
            return ResponseEntity.badRequest().body(null);
        }
        
        List<Map<String, Object>> result = integrationService.getSystemLogs(comId, fromDate, toDate, logType);
        return ResponseEntity.ok(result);
    }
} 