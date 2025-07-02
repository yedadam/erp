package com.dadam.hr.attendance.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 근태관리(Attendance) 화면용 컨트롤러
 */
@Controller
@RequestMapping("/attendance")
public class AttendanceFormController {

    /**
     * 출퇴근 기록/조회 화면 진입
     */
    @GetMapping("/list")
    public String attendanceListPage(Model model) {
        // 필요시 model에 데이터 추가
        return "attendance/attendanceList"; // templates/attendance/attendanceList.html
    }

    // 필요시 추가 화면 진입 메서드 구현
} 