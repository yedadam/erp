package com.dadam.hr.attendance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.hr.attendance.mapper.AttendanceMapper;
import com.dadam.hr.attendance.service.AttendanceService;
import com.dadam.hr.attendance.service.AttendanceVO;

/**
 * 근태관리(Attendance) 서비스 구현체
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceMapper attendanceMapper;

    @Autowired
    public AttendanceServiceImpl(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }

    /**
     * 출근/퇴근 기록 저장
     */
    @Override
    public int insertAttendance(AttendanceVO attendance) {
        return attendanceMapper.insertAttendance(attendance);
    }

    /**
     * 사원ID, 연월로 출퇴근 기록 조회
     */
    @Override
    public List<AttendanceVO> selectByEmpIdAndMonth(String empId, String yearMonth) {
        return attendanceMapper.selectByEmpIdAndMonth(empId, yearMonth);
    }

    // 필요시 추가 메서드 구현
} 