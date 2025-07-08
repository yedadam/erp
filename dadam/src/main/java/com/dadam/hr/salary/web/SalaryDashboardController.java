package com.dadam.hr.salary.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 급여관리(대시보드/통계/일괄지급 등) 전용 컨트롤러
 */
@Controller
@RequestMapping("/erp/hr")
public class SalaryDashboardController {
    /**
     * 급여관리 대시보드(관리자) 화면
     */
    @GetMapping("/salary-dashboard")
    public String salaryDashboard() {
        return "hr/salary-dashboard-admin";
    }
} 