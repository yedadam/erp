package com.dadam.hr.emp.web;

import java.util.List;
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.dadam.hr.emp.service.DeptService;
import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;
import com.dadam.common.service.CodeService;

/**
 * 사원 관리 컨트롤러
 */
// - 사원 전체조회, 상세조회, 등록, 수정, 삭제, 사번생성 등
// - /erp/hr/emp-all, /erp/hr/empList 등 매핑
// - 부서 목록 조회(등록/수정 폼용)
@Controller
@RequestMapping("/erp/hr")
public class EmpController {

    @Autowired
    private EmpService empService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private CodeService codeService;

    /**
     * 사원 목록 페이지 이동
     * @param model - 뷰 모델
     * @return 사원 목록 뷰
     */
    @GetMapping("/emp")
    public String empPage(Model model) {
        model.addAttribute("departments", deptService.findAllDepartments());
        model.addAttribute("positions", codeService.getCodeMap("pos"));
        model.addAttribute("workTypes", codeService.getCodeMap("emp"));
        model.addAttribute("empStatuses", codeService.getCodeMap("stt"));
        return "hr/emplist";
    }

    /**
     * 사원 목록 조회
     * @param keyword - 검색어
     * @param status - 재직상태
     * @param dept - 부서코드
     * @return 사원 리스트
     */
    @GetMapping("/empList")
    @ResponseBody
    public List<EmpVO> empList(@RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String status,
                               @RequestParam(required = false) String dept) {
        return empService.findEmpList(keyword, status, dept);
    }

    /**
     * 사원 상세 조회
     * @param empId - 사원번호
     * @return 사원 정보
     */
    @GetMapping("/empDetail")
    @ResponseBody
    public EmpVO empDetail(@RequestParam String empId) {
        return empService.findEmpDetail(empId);
    }

    /**
     * 사원 등록
     * @param empVO - 사원 정보
     * @param profileImg - 프로필 이미지
     * @return 등록 결과
     */
    @PostMapping("/insertEmp")
    @ResponseBody
    public String insertEmp(@ModelAttribute EmpVO empVO, @RequestParam(value = "profileImg", required = false) MultipartFile profileImg) {
        empVO.setComId("COM-101");
        empVO.setPwd("init");
        if (profileImg != null && !profileImg.isEmpty()) {
            empVO.setProfileImgPath(saveProfileImage(profileImg));
        }
        if (empVO.getEmpId() == null || empVO.getEmpId().isEmpty()) {
            empVO.setEmpId(generateNextEmpId());
        }
        int result = empService.insertEmp(empVO);
        return result > 0 ? "ok" : "fail";
    }

    /**
     * 사원 수정
     * @param empVO - 사원 정보
     * @param profileImg - 프로필 이미지
     * @return 수정 결과
     */
    @PostMapping("/updateEmp")
    @ResponseBody
    public String updateEmp(@ModelAttribute EmpVO empVO, @RequestParam(value = "profileImg", required = false) MultipartFile profileImg) {
        if (profileImg != null && !profileImg.isEmpty()) {
            empVO.setProfileImgPath(saveProfileImage(profileImg));
        }
        int result = empService.updateEmp(empVO);
        return result > 0 ? "ok" : "fail";
    }

    /**
     * 사원 삭제
     * @param param - 사원번호
     * @return 삭제 결과
     */
    @PostMapping("/deleteEmp")
    @ResponseBody
    public String deleteEmp(@RequestBody java.util.Map<String, String> param) {
        String empId = param.get("empId");
        if (empId == null || empId.isEmpty()) return "fail";
        int result = empService.deleteEmp(empId);
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
     * 전체 사원 목록 페이지 이동
     * @param model - 뷰 모델
     * @return 전체 사원 목록 뷰
     */
    @GetMapping("/emp-all")
    public String empAllPage(Model model) {
        model.addAttribute("empStatuses", codeService.getCodeMap("stt"));
        return "hr/emp-all";
    }

    /**
     * 부서 목록 페이지 이동
     * @return 부서 목록 뷰
     */
    @GetMapping("/dept-list")
    public String deptListPage() {
        return "hr/dept";
    }

    /**
     * 조직도 페이지 이동
     * @return 조직도 뷰
     */
    @GetMapping("/org-tree")
    public String orgTreeFinalPage() {
        return "hr/org-tree-final";
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