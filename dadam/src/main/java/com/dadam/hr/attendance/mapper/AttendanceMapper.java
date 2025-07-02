package com.dadam.hr.attendance.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.dadam.hr.attendance.service.AttendanceVO;

/**
 * 근태관리(Attendance) MyBatis Mapper
 * 출근/퇴근 기록 저장 및 조회
 */
public interface AttendanceMapper {
    /**
     * 출근/퇴근 기록 저장
     */
    int insertAttendance(AttendanceVO attendance);

    /**
     * 사원ID, 연월로 출퇴근 기록 조회
     * @param empId 사원ID
     * @param yearMonth 'YYYY-MM' 형식
     */
    List<AttendanceVO> selectByEmpIdAndMonth(@Param("empId") String empId, @Param("yearMonth") String yearMonth);

    // 필요시 추가 메서드 정의
} 