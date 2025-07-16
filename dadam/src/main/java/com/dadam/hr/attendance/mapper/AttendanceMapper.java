package com.dadam.hr.attendance.mapper;

import com.dadam.hr.attendance.service.AttendanceVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 근태 관리 Mapper
 */
public interface AttendanceMapper {
    // 출근 현황 리스트 조회
    List<AttendanceVO> findAttendanceList(@Param("comId") String comId, @Param("date") String date);
} 