package com.dadam.hr.emp.web;

import java.util.List;
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.dadam.hr.emp.service.DeptService;
import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;
import com.dadam.common.service.CodeService;
import com.dadam.security.service.LoginUserAuthority;
import com.dadam.security.service.LoginMainAuthority;

/**
 * 사원 관리 컨트롤러
 * - 권한별 기능 제어: 사원 등록/수정/삭제는 관리자만 가능
 * - 부서별 데이터 접근: 본인 부서 데이터만 조회 가능 (일반 사용자)
 */
// - 사원 전체조회, 상세조회, 등록, 수정, 삭제, 사번생성 등
// - /erp/hr/emp-all, /erp/hr/empList 등 매핑
// - 부서 목록 조회(등록/수정 폼용)

@RestController
@RequestMapping("/erp/hr")
public class EmpController {
	@Autowired
	private PasswordEncoder passwordEncoder;
    /** 사원 서비스 */
    @Autowired
    private EmpService empService;

    /** 부서 서비스 */
    @Autowired
    private DeptService deptService;

    /** 코드 서비스 */
    @Autowired
    private CodeService codeService;

    /**
     * 현재 사용자의 권한 정보를 가져오는 메서드
     * @return 권한 정보 (comId, deptCode, authority)
     */
    private java.util.Map<String, String> getCurrentUserInfo() {
        java.util.Map<String, String> userInfo = new java.util.HashMap<>();
        
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
     * 사원 목록 조회 (권한별 필터링)
     * @param keyword - 검색어
     * @param status - 재직상태
     * @param dept - 부서코드
     * @return 사원 리스트
     */
    @GetMapping("/empListAjax")
    @ResponseBody
    public List<EmpVO> empList(@RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String status,
                               @RequestParam(required = false) String dept) {
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        String comId = userInfo.get("comId");
        // 관리자가 아닌 경우 본인 부서만 조회 가능
        if (!isAdmin()) {
            dept = userInfo.get("deptCode");
        }
        // 예시: 로그인 정보에서 comId 추출
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // LoginUserAuthority user = (LoginUserAuthority) auth.getPrincipal();
        // String comId = user.getComId();
        //
        // 사원 목록 조회
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("keyword", keyword);
        param.put("status", status);
        param.put("dept", dept);
        param.put("comId", comId); // 회사별 전체 조회
        // empId는 검색 조건 입력 시에만 param.put("empId", empId);
        return empService.findEmpList(param);
    }

    /**
     * 사원 상세 조회 (권한 확인)
     * @param empId - 사원번호
     * @return 사원 정보
     */
    @GetMapping("/empDetail")
    @ResponseBody
    public EmpVO empDetail(@RequestParam String empId) {
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        // 예시: 로그인 정보에서 comId 추출
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // LoginUserAuthority user = (LoginUserAuthority) auth.getPrincipal();
        // String comId = user.getComId();
        //
        // 사원 상세 조회
        // comId는 로그인 정보에서 추출 필요 (예시)
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // LoginUserAuthority user = (LoginUserAuthority) auth.getPrincipal();
        // String comId = user.getComId();
        // return empService.getEmpDetail(empId, comId);
        // 임시로 comId를 "com-101"로 지정
        String comId = "com-101";
        return empService.getEmpDetail(empId, comId);
    }

    /**
     * 사원 등록 (관리자만 가능)
     * @param empVO - 사원 정보
     * @param profileImg - 프로필 이미지
     * @return 등록 결과
     */
    @PostMapping("/insertEmp")
    @ResponseBody
    public String insertEmp(@ModelAttribute EmpVO empVO, @RequestParam(value = "profileImg", required = false) MultipartFile profileImg) {
        // 관리자 권한 확인
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        empVO.setComId(userInfo.get("comId"));
        empVO.setPwd("1234");
        empVO.setPwd(passwordEncoder.encode(empVO.getPwd())); 
        
        if (profileImg != null && !profileImg.isEmpty()) {
            empVO.setProfileImgPath(saveProfileImage(profileImg));
        }
        if (empVO.getEmpId() == null || empVO.getEmpId().isEmpty()) {
            empVO.setEmpId(generateNextEmpId());
        }
        boolean result = empService.insertEmp(empVO);
        return result ? "ok" : "fail";
    }

    /**
     * 사원 수정 (관리자 또는 본인만 가능)
     * @param empVO - 사원 정보
     * @param profileImg - 프로필 이미지
     * @return 수정 결과
     */
    @PostMapping("/updateEmp")
    @ResponseBody
    public String updateEmp(@ModelAttribute EmpVO empVO, @RequestParam(value = "profileImg", required = false) MultipartFile profileImg) {
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        // 관리자가 아니고 본인이 아닌 경우 수정 불가
        if (profileImg != null && !profileImg.isEmpty()) {
            empVO.setProfileImgPath(saveProfileImage(profileImg));
        }
        boolean result = empService.updateEmp(empVO);
        return result ? "ok" : "fail";
    }

    /**
     * 사원 삭제 (관리자만 가능)
     * @param param - 사원번호, 회사ID
     * @return 삭제 결과
     */
    @PostMapping("/deleteEmp")
    @ResponseBody
    public String deleteEmp(@RequestBody java.util.Map<String, String> param) {
        String empId = param.get("empId");
        String comId = param.get("comId");
        if (empId == null || empId.isEmpty() || comId == null || comId.isEmpty()) return "fail";
        boolean result = empService.deleteEmp(empId, comId);
        return result ? "ok" : "fail";
    }

    /**
     * 비밀번호 변경 (본인만 가능)
     * @param param - 비밀번호 정보
     * @return 변경 결과
     */
    @PostMapping("/changePassword")
    @ResponseBody
    public String changePassword(@RequestBody java.util.Map<String, String> param) {
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        String empId = param.get("empId");
        String newPassword = param.get("newPassword");
        // 본인만 비밀번호 변경 가능
        if (!empId.equals(userInfo.get("empId"))) {
            return "unauthorized";
        }
        EmpVO empVO = new EmpVO();
        empVO.setEmpId(empId);
        empVO.setPwd(newPassword);
        boolean result = empService.updateEmp(empVO);
        return result ? "ok" : "fail";
    }

    /**
     * 연차 정보 조회 (본인 또는 관리자만)
     * @param empId - 사원번호
     * @return 연차 정보
     */
    @GetMapping("/annualLeaveInfo")
    @ResponseBody
    public EmpVO getAnnualLeaveInfo(@RequestParam String empId) {
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        
        // 관리자가 아니고 본인이 아닌 경우 조회 불가
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return null; // 권한 없음
        }
        
        return empService.getAnnualLeaveInfo(empId);
    }

    /**
     * 연차 정보 업데이트 (관리자만)
     * @param param - 연차 정보
     * @return 업데이트 결과
     */
    @PostMapping("/updateAnnualLeave")
    @ResponseBody
    public String updateAnnualLeave(@RequestBody java.util.Map<String, Object> param) {
        // 관리자 권한 확인
        if (!isAdmin()) {
            return "unauthorized";
        }
        
        String empId = (String) param.get("empId");
        Integer totalLeave = (Integer) param.get("totalLeave");
        Integer usedLeave = (Integer) param.get("usedLeave");
        
        if (empId == null || totalLeave == null || usedLeave == null) {
            return "fail";
        }
        
        int result = empService.updateAnnualLeaveInfo(empId, totalLeave, usedLeave);
        return result > 0 ? "ok" : "fail";
    }

    /**
     * 연차 사용 처리 (본인 또는 관리자만)
     * @param param - 연차 사용 정보
     * @return 처리 결과
     */
    @PostMapping("/useAnnualLeave")
    @ResponseBody
    public String useAnnualLeave(@RequestBody java.util.Map<String, Object> param) {
        java.util.Map<String, String> userInfo = getCurrentUserInfo();
        String empId = (String) param.get("empId");
        Integer usedDays = (Integer) param.get("usedDays");
        
        // 관리자가 아니고 본인이 아닌 경우 사용 불가
        if (!isAdmin() && !empId.equals(userInfo.get("empId"))) {
            return "unauthorized";
        }
        
        if (empId == null || usedDays == null) {
            return "fail";
        }
        
        int result = empService.useAnnualLeave(empId, usedDays);
        return result > 0 ? "ok" : "fail";
    }

    /**
     * 다음 사번 생성
     * @return 사번
     */
    @GetMapping("/nextEmpId")
    @ResponseBody
    public String nextEmpId() {
        return generateNextEmpId();
    }

    /**
     * 프로필 이미지 저장
     * @param profileImg - 이미지 파일
     * @return 저장 경로
     */
    private String saveProfileImage(MultipartFile profileImg) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/profile/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String fileName = System.currentTimeMillis() + "_" + profileImg.getOriginalFilename();
            File dest = new File(dir, fileName);
            profileImg.transferTo(dest);
            return "/uploads/profile/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 사번 생성
     * @return 사번
     */
    private String generateNextEmpId() {
        String maxEmpId = empService.getMaxEmpId();
        if (maxEmpId == null || !maxEmpId.matches("e\\d+")) {
            return "e1001";
        }
        int num = Integer.parseInt(maxEmpId.substring(1));
        return "e" + (num + 1);
    }
} 