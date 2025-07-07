package com.dadam.hr.attendance.web;

import com.dadam.hr.attendance.service.AttendanceStatsService;
import com.dadam.hr.attendance.service.AttendanceStatsVO;
import com.dadam.hr.attendance.service.AttendanceRealtimeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 근태 통계 REST 컨트롤러
 */
@RestController
@RequestMapping("/erp/hr")
public class AttendanceStatsRestController {

    /** 통계 서비스 */
    @Autowired
    private AttendanceStatsService attendanceStatsService;

    /**
     * 월별 근태 통계 조회
     * @param comId - 회사ID
     * @param yearMonth - 년월 (YYYY-MM)
     * @param deptCode - 부서코드
     * @return 통계 리스트
     */
    @GetMapping("/attendanceMonthlyStats")
    public List<AttendanceStatsVO> getMonthlyStats(@RequestParam String comId,
                                                 @RequestParam String yearMonth,
                                                 @RequestParam(required = false) String deptCode) {
        return attendanceStatsService.findMonthlyStats(comId, yearMonth, deptCode);
    }

    /**
     * 개인별 근태 통계 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param yearMonth - 년월 (YYYY-MM)
     * @return 통계 정보
     */
    @GetMapping("/attendancePersonalStats")
    public AttendanceStatsVO getPersonalStats(@RequestParam String comId,
                                           @RequestParam String empId,
                                           @RequestParam String yearMonth) {
        return attendanceStatsService.findPersonalStats(comId, empId, yearMonth);
    }

    /**
     * 부서별 근태 현황 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @return 부서별 현황
     */
    @GetMapping("/attendanceDeptStats")
    public List<AttendanceStatsVO> getDeptStats(@RequestParam String comId,
                                              @RequestParam(required = false) String deptCode) {
        return attendanceStatsService.findDeptStats(comId, deptCode);
    }

    /**
     * 실시간 출퇴근 현황 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @return 실시간 현황
     */
    @GetMapping("/attendanceRealtimeStatus")
    public List<AttendanceRealtimeVO> getRealtimeStatus(@RequestParam String comId,
                                                       @RequestParam(required = false) String deptCode) {
        return attendanceStatsService.findRealtimeStatus(comId, deptCode);
    }

    /**
     * 오늘 근태 현황 요약
     * @param comId - 회사ID
     * @return 오늘 현황 요약
     */
    @GetMapping("/attendanceTodaySummary")
    public Map<String, Object> getTodaySummary(@RequestParam String comId) {
        return attendanceStatsService.getTodaySummary(comId);
    }

    /**
     * 월별 통계 생성
     * @param comId - 회사ID
     * @param yearMonth - 년월 (YYYY-MM)
     * @return 생성 결과
     */
    @PostMapping("/generateMonthlyStats")
    public String generateMonthlyStats(@RequestParam String comId, @RequestParam String yearMonth) {
        try {
            int result = attendanceStatsService.generateMonthlyStats(comId, yearMonth);
            return result > 0 ? "통계 생성 완료 (" + result + "건)" : "생성할 데이터가 없습니다.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
} 