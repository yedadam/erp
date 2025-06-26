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

// 사원관리 컨트롤러
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

    /**
     * 사원 목록 페이지로 이동
     * @return 사원 목록 페이지 view name
     * 
     */
    @GetMapping("/emp")
    public String empPage(Model model) {
        model.addAttribute("departments", deptService.findAllDepartments());
        return "hr/emplist";
    }

    @GetMapping("/empList")
    @ResponseBody
    public List<EmpVO> empList(@RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String status,
                               @RequestParam(required = false) String dept) {
        return empService.findEmpList(keyword, status, dept);
    }

    @GetMapping("/empDetail")
    @ResponseBody
    public EmpVO empDetail(@RequestParam String empId) {
        return empService.findEmpDetail(empId);
    }

    @PostMapping("/insertEmp")
    @ResponseBody
    public String insertEmp(@ModelAttribute EmpVO empVO, @RequestParam(value = "profileImg", required = false) MultipartFile profileImg) {
        empVO.setComId("COM-101");
        empVO.setPwd("init");
        // 파일 저장 처리 (절대경로 사용)
        if (profileImg != null && !profileImg.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/profile/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String fileName = System.currentTimeMillis() + "_" + profileImg.getOriginalFilename();
            File dest = new File(dir, fileName);
            try {
                profileImg.transferTo(dest);
                empVO.setProfileImgPath("/uploads/profile/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "fail";
            }
        }
        int result = empService.insertEmp(empVO);
        return result > 0 ? "ok" : "fail";
    }

    @PostMapping("/updateEmp")
    @ResponseBody
    public String updateEmp(@ModelAttribute EmpVO empVO, @RequestParam(value = "profileImg", required = false) MultipartFile profileImg) {
        // 파일 저장 처리 (절대경로 사용)
        if (profileImg != null && !profileImg.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/profile/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String fileName = System.currentTimeMillis() + "_" + profileImg.getOriginalFilename();
            File dest = new File(dir, fileName);
            try {
                profileImg.transferTo(dest);
                empVO.setProfileImgPath("/uploads/profile/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "fail";
            }
        }
        int result = empService.updateEmp(empVO);
        return result > 0 ? "ok" : "fail";
    }

    @PostMapping("/deleteEmp")
    @ResponseBody
    public String deleteEmp(@RequestBody java.util.Map<String, String> param) {
        String empId = param.get("empId");
        if (empId == null || empId.isEmpty()) return "fail";
        int result = empService.deleteEmp(empId);
        return result > 0 ? "ok" : "fail";
    }

    @GetMapping("/nextEmpId")
    @ResponseBody
    public String nextEmpId() {
        String maxEmpId = empService.getMaxEmpId();
        if (maxEmpId == null || !maxEmpId.matches("e\\d+")) {
            return "e1001";
        }
        int num = Integer.parseInt(maxEmpId.substring(1));
        return "e" + (num + 1);
    }

    @GetMapping("/emp-all")
    public String empAllPage() {
        return "hr/emp-all";
    }
} 