package com.dadam.hr.salary.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 급여항목 관리 화면 컨트롤러
 * - /erp/hr/salaryitem 요청 시 hr/salaryitem.html 반환
 * - 실무 흐름/팀 규칙에 따라 별도 ViewController로 분리
 */
@Controller
public class SalaryItemViewController {
    @GetMapping("/erp/hr/salaryitem")
    public String salaryItemPage() {
        return "hr/salaryitem";
    }
} 