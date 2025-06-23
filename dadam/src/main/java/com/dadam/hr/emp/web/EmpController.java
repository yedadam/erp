package com.dadam.hr.emp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;

@Controller
@RequestMapping("/erp/hr")
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 사원 목록 페이지로 이동
     * @return 사원 목록 페이지 view name
     * 
     */
    @GetMapping("/emp")
    public String empPage() {
        return "hr/emplist";
    }

    @GetMapping("/empList")
    @ResponseBody
    public List<EmpVO> empList(@RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String status) {
        return empService.findEmpList(keyword, status);
    }

    @GetMapping("/empDetail")
    @ResponseBody
    public EmpVO empDetail(@RequestParam String empId) {
        return empService.findEmpDetail(empId);
    }

    @PostMapping("/insertEmp")
    @ResponseBody
    public String insertEmp(EmpVO empVO) {
        // 임시: 로그인 세션에서 회사ID 가져오기(예시)
        empVO.setComId("COM-101");
        empVO.setPwd("init"); // 또는 랜덤 비번 생성
        int result = empService.insertEmp(empVO);
        return result > 0 ? "ok" : "fail";
    }

    @PostMapping("/updateEmp")
    @ResponseBody
    public String updateEmp(EmpVO empVO) {
        if (empVO.getEmpId() == null || empVO.getEmpId().isEmpty()) return "fail";
        int result = empService.updateEmp(empVO);
        return result > 0 ? "ok" : "fail";
    }

    @PostMapping("/deleteEmp")
    @ResponseBody
    public String deleteEmp(@RequestParam String empId) {
        if (empId == null || empId.isEmpty()) return "fail";
        int result = empService.deleteEmp(empId);
        return result > 0 ? "ok" : "fail";
    }
} 