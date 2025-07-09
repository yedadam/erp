package com.dadam.hr.emp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dadam.hr.emp.service.DeptService;
import com.dadam.common.service.CodeService;

/**
 * 사원 뷰 컨트롤러
 * - 사원 관련 화면 반환 담당
 */
@Controller
@RequestMapping("/erp/hr")
public class EmpViewController {
    @Autowired
    private DeptService deptService;
    @Autowired
    private CodeService codeService;

    /**
     * 사원 전체 목록 화면 반환
     * @return 사원 목록 뷰
     */
    @GetMapping("/empList")
    public String empPage(Model model) {
        model.addAttribute("departments", deptService.getDeptList());
        model.addAttribute("positions", codeService.getCodeMap("pos"));
        model.addAttribute("workTypes", codeService.getCodeMap("emp"));
        model.addAttribute("empStatuses", codeService.getCodeMap("stt"));
        return "hr/emplist";
    }

    @GetMapping("/emp-all")
    public String empAllPage(Model model) {
        model.addAttribute("empStatuses", codeService.getCodeMap("stt"));
        return "hr/emp-all";
    }
} 