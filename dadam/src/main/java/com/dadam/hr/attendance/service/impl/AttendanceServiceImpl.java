package com.dadam.hr.attendance.service.impl;

import com.dadam.hr.attendance.mapper.AttendanceMapper;
import com.dadam.hr.attendance.mapper.AttendanceIpMapper;
import com.dadam.hr.attendance.service.AttendanceService;
import com.dadam.hr.attendance.service.AttendanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 근태 서비스 구현체
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    /** 근태 Mapper */
    @Autowired
    private AttendanceMapper attendanceMapper;
    
    /** IP 관리 Mapper */
    @Autowired
    private AttendanceIpMapper attendanceIpMapper;

    /**
     * 출근 등록
     * @param vo - 근태 정보
     * @param requestIp - 요청 IP
     * @param gpsInfo - GPS 정보
     * @param locationInfo - 지점 정보
     * @return 처리 결과
     */
    @Override
    public String checkIn(AttendanceVO vo, String requestIp, String gpsInfo, String locationInfo) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("empId", vo.getEmpId());
        param.put("workDate", vo.getWorkDate().toString());
        param.put("comId", vo.getComId());
        
        if (attendanceMapper.existsCheckIn(param)) {
            throw new Exception("이미 출근 기록이 있습니다.");
        }
        if (!validateIp(requestIp, vo.getComId(), vo.getEmpId())) {
            throw new Exception("허용되지 않은 IP입니다.");
        }
        
        vo.setStandardStartTime(getStandardStartTime(vo.getEmpId(), vo.getWorkDate()));
        vo.setActualStartTime(LocalDateTime.now());
        vo.setCheckIp(requestIp);
        vo.setGpsInfo(gpsInfo);
        vo.setLocationInfo(locationInfo);
        vo.setStatus(judgeAttendanceStatus(vo));
        vo.setCreatedAt(LocalDateTime.now());
        
        Map<String, Object> insertParam = new HashMap<>();
        insertParam.put("attendanceCode", vo.getAttendanceCode());
        insertParam.put("comId", vo.getComId());
        insertParam.put("empId", vo.getEmpId());
        insertParam.put("workDate", vo.getWorkDate().toString());
        insertParam.put("standardStartTime", vo.getStandardStartTime());
        insertParam.put("actualStartTime", vo.getActualStartTime());
        insertParam.put("checkIp", vo.getCheckIp());
        insertParam.put("gpsInfo", vo.getGpsInfo());
        insertParam.put("locationInfo", vo.getLocationInfo());
        insertParam.put("status", vo.getStatus());
        
        attendanceMapper.insertCheckIn(insertParam);
        return "출근이 정상적으로 등록되었습니다.";
    }

    /**
     * 퇴근 등록
     * @param vo - 근태 정보
     * @param requestIp - 요청 IP
     * @param gpsInfo - GPS 정보
     * @param locationInfo - 지점 정보
     * @return 처리 결과
     */
    @Override
    public String checkOut(AttendanceVO vo, String requestIp, String gpsInfo, String locationInfo) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("empId", vo.getEmpId());
        param.put("workDate", vo.getWorkDate().toString());
        param.put("comId", vo.getComId());
        
        AttendanceVO today = attendanceMapper.selectTodayAttendance(param);
        if (today == null || today.getActualStartTime() == null) {
            throw new Exception("출근 기록이 없습니다.");
        }
        if (today.getActualEndTime() != null) {
            throw new Exception("이미 퇴근 처리되었습니다.");
        }
        if (!validateIp(requestIp, vo.getComId(), vo.getEmpId())) {
            throw new Exception("허용되지 않은 IP입니다.");
        }
        
        today.setStandardEndTime(getStandardEndTime(vo.getEmpId(), vo.getWorkDate()));
        today.setActualEndTime(LocalDateTime.now());
        today.setOvertimeHrs(BigDecimal.valueOf(calculateOvertimeHours(today)));
        today.setUpdatedAt(LocalDateTime.now());
        
        Map<String, Object> updateParam = new HashMap<>();
        updateParam.put("attendanceCode", today.getAttendanceCode());
        updateParam.put("comId", today.getComId());
        updateParam.put("actualEndTime", today.getActualEndTime());
        updateParam.put("standardEndTime", today.getStandardEndTime());
        updateParam.put("overtimeHrs", today.getOvertimeHrs());
        
        attendanceMapper.updateCheckOut(updateParam);
        return "퇴근이 정상적으로 처리되었습니다.";
    }

    /**
     * 근태 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param fromDate - 시작일
     * @param toDate - 종료일
     * @param deptCode - 부서코드
     * @return 근태 리스트
     */
    @Override
    public List<AttendanceVO> getAttendanceList(String comId, String empId, String fromDate, String toDate, String deptCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("comId", comId);
        param.put("empId", empId);
        param.put("fromDate", fromDate);
        param.put("toDate", toDate);
        param.put("deptCode", deptCode);
        return attendanceMapper.selectAttendanceList(param);
    }

    /**
     * IP 검증
     * @param requestIp - 요청 IP
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 검증 결과
     */
    @Override
    public boolean validateIp(String requestIp, String comId, String empId) {
        return attendanceMapper.validateIp(requestIp, comId, empId);
    }

    /**
     * GPS 검증
     * @param gpsInfo - GPS 정보
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 검증 결과
     */
    @Override
    public boolean validateGps(String gpsInfo, String comId, String empId) {
        return attendanceMapper.validateGps(gpsInfo, comId, empId);
    }

    /**
     * 지각/조퇴 자동 판정
     * @param vo - 근태 정보
     * @return 판정 결과 (정상/지각/조퇴)
     */
    @Override
    public String judgeAttendanceStatus(AttendanceVO vo) {
        if (vo.getActualStartTime() == null) {
            return "결근";
        }
        if (vo.getActualStartTime().isAfter(vo.getStandardStartTime())) {
            return "지각";
        } else {
            return "정상";
        }
    }

    /**
     * 연장근무 시간 계산
     * @param vo - 근태 정보
     * @return 연장근무 시간
     */
    @Override
    public double calculateOvertimeHours(AttendanceVO vo) {
        if (vo.getActualEndTime() == null || vo.getStandardEndTime() == null) {
            return 0.0;
        }
        long minutes = java.time.Duration.between(vo.getStandardEndTime(), vo.getActualEndTime()).toMinutes();
        return minutes > 0 ? minutes / 60.0 : 0.0;
    }

    /**
     * 실시간 출퇴근 현황 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @return 실시간 현황
     */
    @Override
    public List<AttendanceVO> getRealtimeStatus(String comId, String deptCode) {
        return attendanceMapper.getRealtimeStatus(comId, deptCode);
    }

    /**
     * 기준 출근시간 반환
     * @param empId - 사원번호
     * @param workDate - 근무일자
     * @return 기준 출근시간
     */
    private LocalDateTime getStandardStartTime(String empId, java.time.LocalDate workDate) {
        return workDate.atTime(9, 0); // 기본값
    }

    /**
     * 기준 퇴근시간 반환
     * @param empId - 사원번호
     * @param workDate - 근무일자
     * @return 기준 퇴근시간
     */
    private LocalDateTime getStandardEndTime(String empId, java.time.LocalDate workDate) {
        return workDate.atTime(18, 0); // 기본값
    }
} 