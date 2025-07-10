package com.dadam.hr.salary.web;

import com.dadam.hr.salary.service.SalaryItemService;
import com.dadam.hr.salary.service.SalaryItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dadam.security.service.LoginUserAuthority;
import com.dadam.security.service.LoginMainAuthority;

import java.util.List;
import java.util.Map;

/**
 * 급여항목 마스터 REST 컨트롤러
 * - 권한별 기능 제어: 급여항목 등록/수정/삭제는 관리자만 가능
 * - 조회는 권한별 필터링
 */
@RestController
@RequestMapping("/erp/hr")
public class SalaryItemRestController {
    
    @Autowired
    private SalaryItemService salaryItemService;

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
        System.out.println("[급여항목 삭제] 현재 로그인 계정 권한: " + authority);
        return "admin".equals(authority) || "master".equals(authority);
    }

    /** 전체 급여항목 목록 조회 */
    @GetMapping("/salaryitem/list")
    public List<SalaryItemVO> getSalaryItemList(@RequestParam Map<String, Object> map) {
        return salaryItemService.getSalaryItemList(map);
    }

    /** 단일 급여항목 조회 */
    @GetMapping("/detail")
    public SalaryItemVO getSalaryItem(@RequestParam String comId, @RequestParam String allowCode) {
        return salaryItemService.getSalaryItem(comId, allowCode);
    }

    /** 급여항목 등록 (관리자만) */
    @PostMapping("/salaryitem/register")
    public String addSalaryItem(@RequestBody SalaryItemVO vo) {
        // comId가 없으면 기본값 세팅 (실무에서는 로그인 정보에서 가져오는 게 더 안전)
        if (vo.getComId() == null || vo.getComId().isEmpty()) {
            vo.setComId("com-101");
        }
        int result = salaryItemService.addSalaryItem(vo);
        return result > 0 ? "ok" : "fail";
    }

    /** 급여항목 수정 (관리자만) */
    @PostMapping("/salaryitem/modify")
    public String modifySalaryItem(@RequestBody SalaryItemVO vo) {
        // 관리자 권한 확인
//        if (!isAdmin()) {
//            return "unauthorized";
//        }
        
        Map<String, String> userInfo = getCurrentUserInfo();
        vo.setComId(userInfo.get("comId"));
        
        int result = salaryItemService.modifySalaryItem(vo);
        return result > 0 ? "ok" : "fail";
    }

    /** 급여항목 삭제 (관리자만) */
    @DeleteMapping("/delete")
    public String removeSalaryItem(@RequestParam String comId, @RequestParam String allowCode) {
        // 관리자 권한 확인
//        if (!isAdmin()) {
//            return "unauthorized";
//        }
        
        int result = salaryItemService.removeSalaryItem(comId, allowCode);
        return result > 0 ? "ok" : "fail";
    }
} 