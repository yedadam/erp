package com.dadam.hr.emp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/erp/standard")
public class DeptViewController {
    @GetMapping({"/deptList", "/deptlist"})
    public String deptListPage() {
        return "hr/deptlist";
    }
} 