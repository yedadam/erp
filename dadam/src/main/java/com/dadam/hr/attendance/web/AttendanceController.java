package com.dadam.hr.attendance.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 근태관리 화면 컨트롤러
 * - 근태관리 메인 화면
 * - 근태 조회 화면
 * - 근태 수정 화면
 */
@Controller
@RequestMapping("/erp/hr")
public class AttendanceController {

    /**
     * 근태관리 메인 화면
     * @param model - 모델
     * @return 근태관리 메인 화면
     */
    @GetMapping("/attendance")
    public String attendanceMain(Model model) {
        model.addAttribute("activeMenu", "hr");
        model.addAttribute("pageTitle", "근태관리");
        return "hr/attendance";
    }

    /**
     * 근태 조회 화면 (관리자용)
     * @param model - 모델
     * @param empId - 사원번호 (선택적)
     * @param fromDate - 시작일
     * @param toDate - 종료일
     * @return 근태 조회 화면
     */
    @GetMapping("/attendance/view")
    public String attendanceView(Model model, 
                               @RequestParam(required = false) String empId,
                               @RequestParam(required = false) String fromDate,
                               @RequestParam(required = false) String toDate) {
        model.addAttribute("activeMenu", "hr");
        model.addAttribute("pageTitle", "근태 조회");
        model.addAttribute("empId", empId);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        return "hr/attendance";
    }

    /**
     * 근태 수정 화면 (관리자용)
     * @param model - 모델
     * @param attendanceCode - 근태코드
     * @return 근태 수정 화면
     */
    @GetMapping("/attendance/edit")
    public String attendanceEdit(Model model, 
                               @RequestParam String attendanceCode) {
        model.addAttribute("activeMenu", "hr");
        model.addAttribute("pageTitle", "근태 수정");
        model.addAttribute("attendanceCode", attendanceCode);
        return "hr/attendance";
    }

    /**
     * 근태 통계 화면 (관리자용)
     * @param model - 모델
     * @return 근태 통계 화면
     */
    @GetMapping("/attendance/stats")
    public String attendanceStats(Model model) {
        model.addAttribute("activeMenu", "hr");
        model.addAttribute("pageTitle", "근태 통계");
        return "hr/attendance";
    }

    /**
     * 급여명세서 메인 화면
     * /erp/hr/salary
     */
    @GetMapping("/salary")
    public String salaryMain() {
        return "hr/salary";
    }
} 