package com.dadam.hr.emp.web;

import com.dadam.hr.emp.service.DeptService;
import com.dadam.hr.emp.service.DeptVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dadam.security.service.LoginUserAuthority;
import com.dadam.security.service.LoginMainAuthority;

import java.util.List;
import java.util.Map;

/**
 * 부서 관리 API 컨트롤러
 * - 권한별 기능 제어: 부서 등록/수정/삭제는 관리자만 가능
 * - 부서별 데이터 접근: 본인 부서만 조회 가능 (일반 사용자)
 */
@RestController
@RequestMapping("/erp/hr")
public class DeptController {

    private final DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * 현재 사용자의 권한 정보를 가져오는 메서드
     * @return 권한 정보 (comId, deptCode, authority)
     */
    private java.util.Map<String, String> getCurrentUserInfo() {
        java.util.Map<String, String> userInfo = new java.util.HashMap<>();
        
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
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        String authority = userInfo.get("authority");
        return "admin".equals(authority) || "master".equals(authority);
    }

    /**
     * 부서 목록 조회 (권한별 필터링)
     * @return 부서 리스트
     */
    @GetMapping("/dept")
    public List<DeptVO> getDeptList() {
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자가 아닌 경우 본인 부서만 조회 가능
        if (!isAdmin()) {
            DeptVO userDept = deptService.findDepartmentByCode(userInfo.get("deptCode"));
            List<DeptVO> result = new java.util.ArrayList<>();
            if (userDept != null) {
                result.add(userDept);
            }
            return result;
        }
        
        return deptService.findAllDepartments();
    }

    /**
     * 부서 등록 (관리자만 가능)
     * @param dept - 부서 정보
     * @return 등록 결과
     */
    @PostMapping("/deptInsert")
    public String insertDept(@RequestBody DeptVO dept) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return "unauthorized";
        }
        
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        dept.setComId(userInfo.get("comId"));
        
        int result = deptService.insertDepartment(dept);
        return result > 0 ? "ok" : "fail";
    }

    /**
     * 부서 수정 (관리자만 가능)
     * @param dept - 부서 정보
     * @return 수정 결과
     */
    @PostMapping("/deptUpdate")
    public String updateDept(@RequestBody DeptVO dept) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return "unauthorized";
        }
        
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        dept.setComId(userInfo.get("comId"));
        
        int result = deptService.updateDepartment(dept);
        return result > 0 ? "ok" : "fail";
    }

    /**
     * 부서 삭제 (관리자만 가능)
     * @param param - 부서코드
     * @return 삭제 결과
     */
    @PostMapping("/deptDelete")
    public String deleteDept(@RequestBody Map<String, String> param) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return "unauthorized";
        }
        
        int result = deptService.deleteDepartment(param.get("deptCode"));
        return result > 0 ? "ok" : "fail";
    }

    /**
     * 부서 상세 조회 (권한 확인)
     * @param deptCode - 부서코드
     * @return 부서 정보
     */
    @GetMapping("/deptDetail")
    public DeptVO getDeptDetail(@RequestParam String deptCode) {
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자가 아닌 경우 본인 부서만 조회 가능
        if (!isAdmin() && !deptCode.equals(userInfo.get("deptCode"))) {
            return null; // 권한 없음
        }
        
        return deptService.findDepartmentByCode(deptCode);
    }

    /**
     * 조직도 트리 구조 조회 (관리자만)
     * @return 조직도 트리 데이터
     */
    @GetMapping("/orgTree")
    public List<DeptVO> getOrgTree() {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return null;
        }
        
        return deptService.findAllDepartments(); // 트리 구조는 Service에서 처리
    }
} 