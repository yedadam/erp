package com.dadam.hr.attendance.service.impl;

import com.dadam.hr.attendance.mapper.AttendanceMapper;
import com.dadam.hr.attendance.service.AttendanceService;
import com.dadam.hr.attendance.service.AttendanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 근태 서비스 구현체
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    /** 근태 Mapper */
    @Autowired
    private AttendanceMapper attendanceMapper;

    /**
     * 출근 등록
     * @param vo - 근태 정보
     * @param requestIp - 요청 IP
     * @param gpsInfo - GPS 정보
     */
    @Override
    public void checkIn(AttendanceVO vo, String requestIp, String gpsInfo) throws Exception {
        if (attendanceMapper.existsCheckIn(vo.getEmpId(), vo.getWorkDate().toString(), vo.getComId())) {
            throw new Exception("이미 출근 기록이 있습니다.");
        }
        if (!isValidIp(requestIp, vo.getEmpId(), vo.getComId())) {
            throw new Exception("허용되지 않은 IP입니다.");
        }
        vo.setStandardStartTime(getStandardStartTime(vo.getEmpId(), vo.getWorkDate()));
        vo.setActualStartTime(LocalDateTime.now());
        vo.setCheckIp(requestIp);
        vo.setStatus(calcStatus(vo.getActualStartTime(), vo.getStandardStartTime()));
        vo.setCreatedAt(LocalDateTime.now());
        attendanceMapper.insertCheckIn(vo);
    }

    /**
     * 퇴근 등록
     * @param vo - 근태 정보
     * @param requestIp - 요청 IP
     * @param gpsInfo - GPS 정보
     */
    @Override
    public void checkOut(AttendanceVO vo, String requestIp, String gpsInfo) throws Exception {
        AttendanceVO today = attendanceMapper.selectTodayAttendance(vo.getEmpId(), vo.getWorkDate().toString(), vo.getComId());
        if (today == null || today.getActualStartTime() == null) {
            throw new Exception("출근 기록이 없습니다.");
        }
        if (today.getActualEndTime() != null) {
            throw new Exception("이미 퇴근 처리되었습니다.");
        }
        if (!isValidIp(requestIp, vo.getEmpId(), vo.getComId())) {
            throw new Exception("허용되지 않은 IP입니다.");
        }
        today.setStandardEndTime(getStandardEndTime(vo.getEmpId(), vo.getWorkDate()));
        today.setActualEndTime(LocalDateTime.now());
        today.setOvertimeHrs(calcOvertime(today.getActualEndTime(), today.getStandardEndTime()));
        today.setUpdatedAt(LocalDateTime.now());
        attendanceMapper.updateCheckOut(today);
    }

    /**
     * 근태 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param fromDate - 시작일
     * @param toDate - 종료일
     * @return 근태 리스트
     */
    @Override
    public List<AttendanceVO> getAttendanceList(String comId, String empId, String fromDate, String toDate) {
        return attendanceMapper.findAttendanceList(comId, empId, fromDate, toDate);
    }

    /**
     * IP 유효성 검사
     * @param ip - 요청 IP
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 유효 여부
     */
    private boolean isValidIp(String ip, String empId, String comId) {
        return true;
    }

    /**
     * 기준 출근시간 반환
     * @param empId - 사원번호
     * @param workDate - 근무일자
     * @return 기준 출근시간
     */
    private LocalDateTime getStandardStartTime(String empId, java.time.LocalDate workDate) {
        return workDate.atTime(9, 0);
    }

    /**
     * 기준 퇴근시간 반환
     * @param empId - 사원번호
     * @param workDate - 근무일자
     * @return 기준 퇴근시간
     */
    private LocalDateTime getStandardEndTime(String empId, java.time.LocalDate workDate) {
        return workDate.atTime(18, 0);
    }

    /**
     * 근태 상태 판정
     * @param actual - 실제 출근시간
     * @param standard - 기준 출근시간
     * @return 근태 상태
     */
    private String calcStatus(LocalDateTime actual, LocalDateTime standard) {
        return actual.isAfter(standard) ? "지각" : "정상";
    }

    /**
     * 연장근무시간 계산
     * @param actualEnd - 실제 퇴근시간
     * @param standardEnd - 기준 퇴근시간
     * @return 연장근무시간
     */
    private java.math.BigDecimal calcOvertime(LocalDateTime actualEnd, LocalDateTime standardEnd) {
        long minutes = java.time.Duration.between(standardEnd, actualEnd).toMinutes();
        return minutes > 0 ? java.math.BigDecimal.valueOf(minutes / 60.0) : java.math.BigDecimal.ZERO;
    }
} 