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
import com.dadam.hr.emp.service.OrgNode;

import java.util.List;
import java.util.Map;

/**
 * 부서 관리 컨트롤러
 * - 부서 등록/수정/삭제, 목록 등 담당
 */
@RestController
@RequestMapping("/erp/hr")
public class DeptController {

    /** 부서 서비스 */
    private final DeptService deptService;

    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * 현재 로그인 사용자 권한 정보 조회
     * @return 권한 정보 Map
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
     * 관리자 권한 여부 확인
     * @return 관리자 여부(Y/N)
     */
    private boolean isAdmin() {
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        String authority = userInfo.get("authority");
        return "admin".equals(authority) || "master".equals(authority);
    }

    /**
     * 부서 목록 조회
     * @return 부서 리스트
     */
    @GetMapping("/dept")
    public List<DeptVO> getDeptList(@RequestParam Map<String, String> params) {
    	System.out.println(params);
        return deptService.getDeptList(params);
    }

    /**
     * 부서 등록
     * @param dept 부서 정보
     * @return 등록 결과
     */
    @PostMapping("/deptInsert")
    public Map<String, Object> insertDept(@RequestBody DeptVO dept) {
        Map<String, Object> result = new java.util.HashMap<>();
        if (!isAdmin()) {
            result.put("success", false);
            result.put("message", "권한 없음");
            return result;
        }
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        dept.setComId(userInfo.get("comId"));
        boolean insertResult = deptService.insertDept(dept);
        result.put("success", insertResult);
        result.put("message", insertResult ? "등록 성공" : "등록 실패");
        return result;
    }

    /**
     * 부서 수정
     * @param dept 부서 정보
     * @return 수정 결과
     */
    @PostMapping("/deptUpdate")
    public Map<String, Object> updateDept(@RequestBody DeptVO dept) {
        Map<String, Object> result = new java.util.HashMap<>();
        if (!isAdmin()) {
            result.put("success", false);
            result.put("message", "권한 없음");
            return result;
        }
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        dept.setComId(userInfo.get("comId"));
        boolean updateResult = deptService.updateDept(dept);
        result.put("success", updateResult);
        result.put("message", updateResult ? "수정 성공" : "수정 실패");
        return result;
    }

    /**
     * 부서 삭제
     * @param param 부서코드
     * @return 삭제 결과
     */
    @PostMapping("/deptDelete")
    public Map<String, Object> deleteDept(@RequestBody Map<String, String> param) {
        Map<String, Object> result = new java.util.HashMap<>();
        if (!isAdmin()) {
            result.put("success", false);
            result.put("message", "권한 없음");
            return result;
        }
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        boolean deleteResult = deptService.deleteDept(param.get("deptCode"), userInfo.get("comId"));
        result.put("success", deleteResult);
        result.put("message", deleteResult ? "삭제 성공" : "삭제 실패");
        return result;
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
        return deptService.getDeptDetail(deptCode, userInfo.get("comId"));
    }

    /**
     * 조직도 트리 구조 조회 (관리자만)
     * @return 조직도 트리 데이터
     */
    @GetMapping("/orgTree")
    public OrgNode getOrgTree() {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return null;
        }
        return deptService.getOrgTree();
    }
} 