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

    /** 근로계약 매퍼 */
    @Autowired
    private com.dadam.hr.emp.mapper.ContractMapper contractMapper;

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
     * 사원 전체 목록 조회 (사원관리/현황/대시보드 AJAX)
     * @param comId - 회사ID(없으면 기본값)
     * @param keyword - 검색어
     * @param status - 재직상태
     * @param dept - 부서코드
     * @return 사원 리스트
     */
    @GetMapping("/empListAjax")
    @ResponseBody
    public List<EmpVO> empListAjax(
        @RequestParam(required = false) String comId,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String dept
    ) {
        if (comId == null || comId.isEmpty()) comId = "com-101";
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("comId", comId);
        param.put("keyword", keyword);
        param.put("status", status);
        param.put("dept", dept);
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
        // 기본값 설정
        if (empVO.getAnnualLeaveTotal() == null) empVO.setAnnualLeaveTotal(0.0);
        if (empVO.getAnnualLeaveUsed() == null) empVO.setAnnualLeaveUsed(0.0);
        if (empVO.getAnnualLeaveRemain() == null) empVO.setAnnualLeaveRemain(0.0);
        
        // 급여 기본값 설정
        if (empVO.getMealAllowance() == null) empVO.setMealAllowance(0);
        if (empVO.getTransportAllowance() == null) empVO.setTransportAllowance(0);
        
        // 사원 정보 등록
        boolean empResult = empService.insertEmp(empVO);
        
        // 근로계약 정보 등록 (기본값 포함)
        if (empResult) {
            try {
                com.dadam.hr.emp.service.ContractVO contractVO = new com.dadam.hr.emp.service.ContractVO();
                contractVO.setEmpId(empVO.getEmpId());
                contractVO.setComId(empVO.getComId());
                contractVO.setContCode(generateNextContractCode());
                contractVO.setContType(empVO.getWorkType() != null ? empVO.getWorkType() : "정규직"); // 기본값: 정규직
                contractVO.setContStatus("ACTIVE");
                contractVO.setCreatedDate(java.time.LocalDate.now().toString());
                
                // 급여 정보 설정
                if (empVO.getSal() != null) {
                    contractVO.setMonSal(empVO.getSal());
                    contractVO.setAnnSal(empVO.getSal() * 12);
                }
                
                contractMapper.insertContract(contractVO);
            } catch (Exception e) {
                System.err.println("근로계약 정보 등록 실패: " + e.getMessage());
            }
            
            // 사원별 급여항목(EMP_ALLOWANCE) 자동 등록 (기본값 포함)
            try {
                // 식대 지원 등록 (기본값: 0원)
                empService.insertEmpAllowance(empVO.getEmpId(), empVO.getComId(), "MEAL", 
                    empVO.getMealAllowance().doubleValue(), "식대 지원");
                
                // 교통비 지원 등록 (기본값: 0원)
                empService.insertEmpAllowance(empVO.getEmpId(), empVO.getComId(), "TRANSPORT", 
                    empVO.getTransportAllowance().doubleValue(), "교통비 지원");
                
                // 기본급 등록
                if (empVO.getSal() != null) {
                    empService.insertEmpAllowance(empVO.getEmpId(), empVO.getComId(), "BASIC_SALARY", 
                        empVO.getSal().doubleValue(), "기본급");
                }
            } catch (Exception e) {
                System.err.println("사원별 급여항목 등록 실패: " + e.getMessage());
            }
        }
        
        return empResult ? "ok" : "fail";
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
        if (profileImg != null && !profileImg.isEmpty()) {
            empVO.setProfileImgPath(saveProfileImage(profileImg));
        }
        boolean result = empService.updateEmp(empVO);
        try {
            // 식대 지원
            if (empService.getEmpAllowances(empVO.getEmpId(), empVO.getComId()).stream().anyMatch(a -> "MEAL".equals(a.get("ALLOW_CODE")))) {
                empService.updateEmpAllowance(empVO.getEmpId(), empVO.getComId(), "MEAL", empVO.getMealAllowance() != null ? empVO.getMealAllowance().doubleValue() : 0.0, "식대 지원");
            } else {
                empService.insertEmpAllowance(empVO.getEmpId(), empVO.getComId(), "MEAL", empVO.getMealAllowance() != null ? empVO.getMealAllowance().doubleValue() : 0.0, "식대 지원");
            }
            // 교통비 지원
            if (empService.getEmpAllowances(empVO.getEmpId(), empVO.getComId()).stream().anyMatch(a -> "TRANSPORT".equals(a.get("ALLOW_CODE")))) {
                empService.updateEmpAllowance(empVO.getEmpId(), empVO.getComId(), "TRANSPORT", empVO.getTransportAllowance() != null ? empVO.getTransportAllowance().doubleValue() : 0.0, "교통비 지원");
            } else {
                empService.insertEmpAllowance(empVO.getEmpId(), empVO.getComId(), "TRANSPORT", empVO.getTransportAllowance() != null ? empVO.getTransportAllowance().doubleValue() : 0.0, "교통비 지원");
            }
            // 기본급(필요시)
            if (empVO.getSal() != null) {
                if (empService.getEmpAllowances(empVO.getEmpId(), empVO.getComId()).stream().anyMatch(a -> "BASIC_SALARY".equals(a.get("ALLOW_CODE")))) {
                    empService.updateEmpAllowance(empVO.getEmpId(), empVO.getComId(), "BASIC_SALARY", empVO.getSal().doubleValue(), "기본급");
                } else {
                    empService.insertEmpAllowance(empVO.getEmpId(), empVO.getComId(), "BASIC_SALARY", empVO.getSal().doubleValue(), "기본급");
                }
            }
        } catch (Exception e) {
            System.err.println("[경고] 급여항목(EMPALLOWANCE) 저장/수정 실패: " + e.getMessage());
        }
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
     * 다음 계약코드 생성
     * @return 계약코드
     */
    private String generateNextContractCode() {
        try {
            String maxCode = contractMapper.getMaxContractCode();
            if (maxCode == null || maxCode.isEmpty() || "CONT0000".equals(maxCode)) {
                return "CONT0001";
            }
            int nextNum = Integer.parseInt(maxCode.substring(4)) + 1;
            return String.format("CONT%04d", nextNum);
        } catch (Exception e) {
            return "CONT0001";
        }
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
            return "uploads/profile/" + fileName;
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
    
    // === 사원별 급여항목(EMP_ALLOWANCE) 관련 API ===
    
    /**
     * 사원별 급여항목 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 급여항목 리스트
     */
    @GetMapping("/empAllowances")
    @ResponseBody
    public List<java.util.Map<String, Object>> getEmpAllowances(
        @RequestParam String empId,
        @RequestParam(required = false) String comId
    ) {
        if (comId == null || comId.isEmpty()) {
            java.util.Map<String, String> userInfo = getCurrentUserInfo();
            comId = userInfo.get("comId");
        }
        List<java.util.Map<String, Object>> rawList = empService.getEmpAllowances(empId, comId);
        // key를 소문자/카멜케이스로 변환
        List<java.util.Map<String, Object>> result = new java.util.ArrayList<>();
        for (java.util.Map<String, Object> row : rawList) {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            for (String key : row.keySet()) {
                String camelKey = key.toLowerCase();
                if (camelKey.contains("_")) {
                    String[] parts = camelKey.split("_");
                    StringBuilder sb = new StringBuilder(parts[0]);
                    for (int i = 1; i < parts.length; i++) {
                        sb.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1));
                    }
                    camelKey = sb.toString();
                }
                map.put(camelKey, row.get(key));
            }
            result.add(map);
        }
        return result;
    }
    
    /**
     * 사원별 급여항목 등록
     * @param param - 급여항목 정보
     * @return 등록 결과
     */
    @PostMapping("/insertEmpAllowance")
    @ResponseBody
    public String insertEmpAllowance(@RequestBody java.util.Map<String, Object> param) {
        if (!isAdmin()) {
            return "unauthorized";
        }
        
        String empId = (String) param.get("empId");
        String comId = (String) param.get("comId");
        String allowCode = (String) param.get("allowCode");
        Double amount = (Double) param.get("amount");
        String note = (String) param.get("note");
        
        if (empId == null || comId == null || allowCode == null) {
            return "fail";
        }
        
        boolean result = empService.insertEmpAllowance(empId, comId, allowCode, amount, note);
        return result ? "ok" : "fail";
    }
    
    /**
     * 사원별 급여항목 수정
     * @param param - 급여항목 정보
     * @return 수정 결과
     */
    @PostMapping("/updateEmpAllowance")
    @ResponseBody
    public String updateEmpAllowance(@RequestBody java.util.Map<String, Object> param) {
        if (!isAdmin()) {
            return "unauthorized";
        }
        
        String empId = (String) param.get("empId");
        String comId = (String) param.get("comId");
        String allowCode = (String) param.get("allowCode");
        Double amount = (Double) param.get("amount");
        String note = (String) param.get("note");
        
        if (empId == null || comId == null || allowCode == null) {
            return "fail";
        }
        
        boolean result = empService.updateEmpAllowance(empId, comId, allowCode, amount, note);
        return result ? "ok" : "fail";
    }
    
    /**
     * 사원별 급여항목 삭제
     * @param param - 급여항목 정보
     * @return 삭제 결과
     */
    @PostMapping("/deleteEmpAllowance")
    @ResponseBody
    public String deleteEmpAllowance(@RequestBody java.util.Map<String, String> param) {
        if (!isAdmin()) {
            return "unauthorized";
        }
        
        String empId = param.get("empId");
        String comId = param.get("comId");
        String allowCode = param.get("allowCode");
        
        if (empId == null || comId == null || allowCode == null) {
            return "fail";
        }
        
        boolean result = empService.deleteEmpAllowance(empId, comId, allowCode);
        return result ? "ok" : "fail";
    }
} 