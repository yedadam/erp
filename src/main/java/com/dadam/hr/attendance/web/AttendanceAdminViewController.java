package com.dadam.hr.attendance.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 근태관리(관리자) 대시보드 View Controller
 */
@Controller
@RequestMapping("/erp/hr")
public class AttendanceAdminViewController {
    @GetMapping("/attendance-admin")
    public String attendanceAdmin() {
        return "hr/attendance-admin";
    }
} 