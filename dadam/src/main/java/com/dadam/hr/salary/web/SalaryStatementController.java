package com.dadam.hr.salary.web;

import com.dadam.hr.salary.service.SalaryStatementService;
import com.dadam.hr.salary.service.SalaryStatementVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 급여명세서 Controller
 */
@Controller
@RequestMapping("/hr/salary")
public class SalaryStatementController {
    @Autowired
    private SalaryStatementService salaryStatementService;

    /** 급여명세서 목록 */
    @GetMapping("/list")
    public String list(Model model) {
        List<SalaryStatementVO> list = salaryStatementService.getSalaryStatementList();
        model.addAttribute("salaryList", list);
        return "hr/salary/list";
    }
    /** 급여명세서 상세 */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        SalaryStatementVO vo = salaryStatementService.getSalaryStatementById(id);
        model.addAttribute("salary", vo);
        return "hr/salary/detail";
    }
    /** 급여명세서 등록 */
    @PostMapping("/add")
    public String add(@ModelAttribute SalaryStatementVO vo) {
        salaryStatementService.addSalaryStatement(vo);
        return "redirect:/hr/salary/list";
    }
    /** 급여명세서 수정 */
    @PostMapping("/edit")
    public String edit(@ModelAttribute SalaryStatementVO vo) {
        salaryStatementService.modifySalaryStatement(vo);
        return "redirect:/hr/salary/list";
    }
    /** 급여명세서 삭제 */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        salaryStatementService.removeSalaryStatement(id);
        return "redirect:/hr/salary/list";
    }
} 