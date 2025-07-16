package com.dadam.hr.attendance.service.impl;

import com.dadam.hr.attendance.mapper.AttendanceMapper;
import com.dadam.hr.attendance.service.AttendanceService;
import com.dadam.hr.attendance.service.AttendanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 근태 관리 서비스 구현체
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    public List<AttendanceVO> findAttendanceList(String comId, String date) {
        return attendanceMapper.findAttendanceList(comId, date);
    }
} 