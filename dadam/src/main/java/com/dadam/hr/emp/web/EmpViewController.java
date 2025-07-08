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
    @GetMapping("/emp")
    public String empPage(Model model) {
        // 화면 진입 시 필요한 데이터만 전달
        model.addAttribute("departments", deptService.getDeptList());
        model.addAttribute("positions", codeService.getCodeMap("pos"));
        model.addAttribute("workTypes", codeService.getCodeMap("emp"));
        model.addAttribute("empStatuses", codeService.getCodeMap("stt"));
        // 권한/부서 정보 등은 JS에서 Ajax로 별도 조회 권장
        return "hr/emplist";
    }
    

    
    @GetMapping({"/deptList", "/deptlist"})
    public String deptListPage() {
        return "hr/deptlist";
    }
} 