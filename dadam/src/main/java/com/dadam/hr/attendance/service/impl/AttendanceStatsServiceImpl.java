package com.dadam.hr.attendance.service.impl;

import com.dadam.hr.attendance.mapper.AttendanceStatsMapper;
import com.dadam.hr.attendance.service.AttendanceStatsService;
import com.dadam.hr.attendance.service.AttendanceStatsVO;
import com.dadam.hr.attendance.service.AttendanceRealtimeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 근태 통계 서비스 구현체
 */
@Service
public class AttendanceStatsServiceImpl implements AttendanceStatsService {

    /** 통계 Mapper */
    @Autowired
    private AttendanceStatsMapper attendanceStatsMapper;

    /**
     * 월별 근태 통계 조회
     * @param comId - 회사ID
     * @param yearMonth - 년월 (YYYY-MM)
     * @param deptCode - 부서코드
     * @return 통계 리스트
     */
    @Override
    public List<AttendanceStatsVO> findMonthlyStats(String comId, String yearMonth, String deptCode) {
        return attendanceStatsMapper.findMonthlyStats(comId, yearMonth, deptCode);
    }

    /**
     * 개인별 근태 통계 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param yearMonth - 년월 (YYYY-MM)
     * @return 통계 정보
     */
    @Override
    public AttendanceStatsVO findPersonalStats(String comId, String empId, String yearMonth) {
        return attendanceStatsMapper.findPersonalStats(comId, empId, yearMonth);
    }

    /**
     * 부서별 근태 현황 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @return 부서별 현황
     */
    @Override
    public List<AttendanceStatsVO> findDeptStats(String comId, String deptCode) {
        return attendanceStatsMapper.findDeptStats(comId, deptCode);
    }

    /**
     * 실시간 출퇴근 현황 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @return 실시간 현황
     */
    @Override
    public List<AttendanceRealtimeVO> findRealtimeStatus(String comId, String deptCode) {
        return attendanceStatsMapper.findRealtimeStatus(comId, deptCode);
    }

    /**
     * 오늘 근태 현황 요약
     * @param comId - 회사ID
     * @return 오늘 현황 요약
     */
    @Override
    public Map<String, Object> getTodaySummary(String comId) {
        Map<String, Object> summary = new HashMap<>();
        
        int checkInCount = attendanceStatsMapper.countTodayCheckIn(comId);
        int lateCount = attendanceStatsMapper.countTodayLate(comId);
        int absentCount = attendanceStatsMapper.countTodayAbsent(comId);
        
        summary.put("checkInCount", checkInCount);
        summary.put("lateCount", lateCount);
        summary.put("absentCount", absentCount);
        summary.put("totalEmployees", checkInCount + lateCount + absentCount);
        
        return summary;
    }

    /**
     * 월별 통계 생성
     * @param comId - 회사ID
     * @param yearMonth - 년월 (YYYY-MM)
     * @return 생성 결과
     */
    @Override
    public int generateMonthlyStats(String comId, String yearMonth) {
        List<AttendanceStatsVO> statsList = findMonthlyStats(comId, yearMonth, null);
        int result = 0;
        
        for (AttendanceStatsVO stats : statsList) {
            result += attendanceStatsMapper.insertMonthlyStats(stats);
        }
        
        return result;
    }
} 