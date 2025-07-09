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
        // 실제 데이터 바인딩은 필요시 추가
        return "hr/salary";
    }
    /**
     * 급여명세서 상세 화면
     * @param id - 급여명세서 PK (문자열 SAL_ID)
     * @param model - 뷰 모델
     * @return 급여명세서 상세 뷰
     */
    @GetMapping("/salary/detail/{id}")
    public String detail(@PathVariable String id, Model model) {
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("salId", id); // SAL_ID는 문자열
        param.put("comId", "com-101"); // 실제 로그인 정보에서 추출 필요
        SalaryStatementVO vo = salaryStatementService.getSalaryStatementById(param);
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