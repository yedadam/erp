package com.dadam.hr.attendance.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dadam.hr.attendance.service.AttendanceService;
import com.dadam.hr.attendance.service.AttendanceVO;

/**
 * 근태관리(Attendance) REST 컨트롤러
 */
@RestController
@RequestMapping("/api/attendance")
public class AttendanceRestController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceRestController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * 출근/퇴근 기록 저장
     */
    @PostMapping("/save")
    public int saveAttendance(@RequestBody AttendanceVO attendance) {
        return attendanceService.insertAttendance(attendance);
    }

    /**
     * 사원ID, 연월로 출퇴근 기록 조회
     */
    @GetMapping("/list")
    public List<AttendanceVO> getAttendanceList(@RequestParam String empId, @RequestParam String yearMonth) {
        return attendanceService.selectByEmpIdAndMonth(empId, yearMonth);
    }

    // 필요시 추가 API 구현
} 