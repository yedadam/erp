package com.dadam.hr.salary.web;

import com.dadam.hr.salary.service.SalaryListService;
import com.dadam.hr.salary.service.SalaryListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 급여명세 리스트 관리 Controller
 * 
 * @author 팀장
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/erp/hr")
public class SalaryListController {

    @Autowired
    private SalaryListService salaryListService;

    /**
     * 현재 로그인 사용자의 회사ID(comId) 추출 (실무 기준)
     */
    private String getCurrentUserComId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null && auth.getPrincipal() instanceof com.dadam.security.service.LoginUserAuthority) {
            return ((com.dadam.security.service.LoginUserAuthority) auth.getPrincipal()).getComId();
        }
        // 기본값(테스트용)
        return "com-101";
    }

    /**
     * 급여명세 목록 조회
     * 
     * @param searchVO 검색 조건
     * @return 급여명세 목록
     */
    @PostMapping("/api/salary/list")
    public ResponseEntity<Map<String, Object>> getSalaryList(@RequestBody SalaryListVO searchVO) {
        Map<String, Object> response = new HashMap<>();
        // comId 우선순위: 프론트 → 세션/로그인 → 기본값
        String comId = searchVO.getComId();
        if (comId == null || comId.isEmpty()) {
            comId = getCurrentUserComId();
        }
        if (comId == null || comId.isEmpty()) {
            comId = "com-101";
        }
        searchVO.setComId(comId);
        
        try {
            List<SalaryListVO> salaryList = salaryListService.getSalaryList(searchVO);
            
            response.put("success", true);
            response.put("data", salaryList);
            response.put("message", "급여명세 목록을 조회했습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "급여명세 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 급여명세 일괄승인
     * 
     * @param request 승인할 급여명세 ID 목록
     * @return 처리 결과
     */
    @PostMapping("/api/salary/approve/batch")
    public ResponseEntity<Map<String, Object>> approveSalaryBatch(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            @SuppressWarnings("unchecked")
            List<String> salaryIds = (List<String>) request.get("salaryIds");
            
            int result = salaryListService.approveSalaryBatch(salaryIds);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", result + "건의 급여명세가 승인되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "일괄승인 처리 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 급여명세 일괄지급
     * 
     * @param request 지급할 급여명세 ID 목록
     * @return 처리 결과
     */
    @PostMapping("/api/salary/pay/batch")
    public ResponseEntity<Map<String, Object>> paySalaryBatch(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            @SuppressWarnings("unchecked")
            List<String> salaryIds = (List<String>) request.get("salaryIds");
            
            int result = salaryListService.paySalaryBatch(salaryIds);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", result + "건의 급여명세가 지급되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "일괄지급 처리 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 급여명세 개별승인
     * 
     * @param request 승인할 급여명세 ID
     * @return 처리 결과
     */
    @PostMapping("/api/salary/approve")
    public ResponseEntity<Map<String, Object>> approveSalary(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String salaryId = (String) request.get("salaryId");
            
            int result = salaryListService.approveSalary(salaryId);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", "급여명세가 승인되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "승인 처리 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 급여명세 개별지급
     * 
     * @param request 지급할 급여명세 ID
     * @return 처리 결과
     */
    @PostMapping("/api/salary/pay")
    public ResponseEntity<Map<String, Object>> paySalary(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String salaryId = (String) request.get("salaryId");
            
            int result = salaryListService.paySalary(salaryId);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", "급여명세가 지급되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "지급 처리 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    /**
     * 급여명세 삭제
     * 
     * @param request 삭제할 급여명세 ID
     * @return 처리 결과
     */
    @PostMapping("/api/salary/delete")
    public ResponseEntity<Map<String, Object>> deleteSalary(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String salaryId = (String) request.get("salaryId");
            
            int result = salaryListService.deleteSalary(salaryId);
            
            response.put("success", true);
            response.put("data", result);
            response.put("message", "급여명세가 삭제되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "삭제 처리 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
} 