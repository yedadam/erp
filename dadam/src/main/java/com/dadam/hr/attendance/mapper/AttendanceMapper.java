package com.dadam.hr.attendance.mapper;

import com.dadam.hr.attendance.service.AttendanceVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 근태 매퍼 인터페이스
 */
@Mapper
public interface AttendanceMapper {
    /**
     * 출근 등록
     * @param vo - 근태 정보
     * @return 등록 결과
     */
    int insertCheckIn(AttendanceVO vo);
    /**
     * 퇴근 처리
     * @param vo - 근태 정보
     * @return 처리 결과
     */
    int updateCheckOut(AttendanceVO vo);
    /**
     * 오늘 근태 조회
     * @param empId - 사원번호
     * @param workDate - 근무일자
     * @param comId - 회사ID
     * @return 근태 정보
     */
    AttendanceVO selectTodayAttendance(String empId, String workDate, String comId);
    /**
     * 출근기록 존재여부
     * @param empId - 사원번호
     * @param workDate - 근무일자
     * @param comId - 회사ID
     * @return 존재여부
     */
    boolean existsCheckIn(String empId, String workDate, String comId);
    /**
     * 근태 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param fromDate - 시작일
     * @param toDate - 종료일
     * @return 근태 리스트
     */
    List<AttendanceVO> findAttendanceList(String comId, String empId, String fromDate, String toDate);
} 