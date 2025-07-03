package com.dadam.hr.attendance.web;

import com.dadam.hr.attendance.service.AttendanceService;
import com.dadam.hr.attendance.service.AttendanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 근태 관리 REST 컨트롤러
 */
@RestController
@RequestMapping("/erp/hr")
public class AttendanceRestController {

    /** 근태 서비스 */
    @Autowired
    private AttendanceService attendanceService;

    /**
     * 출근 등록
     * @param vo - 근태 정보
     * @param request - 요청 객체
     * @return 처리 결과
     */
    @PostMapping("/attendanceCheckIn")
    public String checkIn(@RequestBody AttendanceVO vo, HttpServletRequest request) {
        try {
            String ip = request.getRemoteAddr();
            attendanceService.checkIn(vo, ip, null);
            return "출근 완료";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 퇴근 등록
     * @param vo - 근태 정보
     * @param request - 요청 객체
     * @return 처리 결과
     */
    @PostMapping("/attendanceCheckOut")
    public String checkOut(@RequestBody AttendanceVO vo, HttpServletRequest request) {
        try {
            String ip = request.getRemoteAddr();
            attendanceService.checkOut(vo, ip, null);
            return "퇴근 완료";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 근태 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param fromDate - 시작일
     * @param toDate - 종료일
     * @return 근태 리스트
     */
    @GetMapping("/attendanceList")
    public List<AttendanceVO> getAttendanceList(@RequestParam String comId, @RequestParam String empId,
                                                @RequestParam String fromDate, @RequestParam String toDate) {
        return attendanceService.getAttendanceList(comId, empId, fromDate, toDate);
    }
} 