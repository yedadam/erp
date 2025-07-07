package com.dadam.hr.salary.web;

import com.dadam.hr.salary.service.SalaryStatementService;
import com.dadam.hr.salary.service.SalaryStatementVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 급여명세서 화면 컨트롤러
 * - 반드시 클래스 레벨 @RequestMapping("/erp/hr")로 통일
 */
@Controller
@RequestMapping("/erp/hr")
public class SalaryStatementController {
    /** 급여명세서 서비스 */
    @Autowired
    private SalaryStatementService salaryStatementService;

    /**
     * 급여명세서 목록 화면
     * @param model - 뷰 모델
     * @return 급여명세서 리스트 뷰
     */
    @GetMapping("/salary/list")
    public String list(Model model) {
        // TODO: 실제 로그인 정보에서 empId, comId 추출
        String empId = "e1002";
        String comId = "com-101";
        List<SalaryStatementVO> list = salaryStatementService.getSalaryStatementList(empId, comId);
        model.addAttribute("salaryList", list);
        return "hr/salarylist";
    }
    /**
     * 급여명세서 상세 화면
     * @param id - 급여명세서 PK
     * @param model - 뷰 모델
     * @return 급여명세서 상세 뷰
     */
    @GetMapping("/salary/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        SalaryStatementVO vo = salaryStatementService.getSalaryStatementById(id);
        model.addAttribute("salary", vo);
        return "hr/salarydetail";
    }
    /**
     * 급여명세서 수정
     * @param vo - 수정 정보
     * @return 리다이렉트 URL
     */
    // @PostMapping("/salary/edit")
    // public String edit(@ModelAttribute SalaryStatementVO vo) {
    //     // ... 화면 처리 ...
    // }
} 