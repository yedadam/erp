package com.dadam.hr.emp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/erp/hr")
public class OrgViewController {
    @GetMapping("/org-tree")
    public String orgTreeView() {
        return "hr/org-tree";
    }
} 