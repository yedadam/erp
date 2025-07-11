package com.dadam.hr.attendance.mapper;

import com.dadam.hr.attendance.service.AttendanceStatsVO;
import com.dadam.hr.attendance.service.AttendanceRealtimeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 근태 통계 Mapper
 */
@Mapper
public interface AttendanceStatsMapper {
    
    /**
     * 월별 근태 통계 조회
     * @param comId - 회사ID
     * @param yearMonth - 년월 (YYYY-MM)
     * @param deptCode - 부서코드
     * @return 통계 리스트
     */
    List<AttendanceStatsVO> findMonthlyStats(@Param("comId") String comId,
                                           @Param("yearMonth") String yearMonth,
                                           @Param("deptCode") String deptCode);
    
    /**
     * 개인별 근태 통계 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @param yearMonth - 년월 (YYYY-MM)
     * @return 통계 정보
     */
    AttendanceStatsVO findPersonalStats(@Param("comId") String comId,
                                      @Param("empId") String empId,
                                      @Param("yearMonth") String yearMonth);
    
    /**
     * 부서별 근태 현황 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @return 부서별 현황
     */
    List<AttendanceStatsVO> findDeptStats(@Param("comId") String comId,
                                         @Param("deptCode") String deptCode);
    
    /**
     * 실시간 출퇴근 현황 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @return 실시간 현황
     */
    List<AttendanceRealtimeVO> findRealtimeStatus(@Param("comId") String comId,
                                                 @Param("deptCode") String deptCode);
    
    /**
     * 오늘 출근자 수 조회
     * @param comId - 회사ID
     * @return 출근자 수
     */
    int countTodayCheckIn(@Param("comId") String comId);
    
    /**
     * 오늘 지각자 수 조회
     * @param comId - 회사ID
     * @return 지각자 수
     */
    int countTodayLate(@Param("comId") String comId);
    
    /**
     * 오늘 결근자 수 조회
     * @param comId - 회사ID
     * @return 결근자 수
     */
    int countTodayAbsent(@Param("comId") String comId);
    
    /**
     * 월별 통계 생성
     * @param vo - 통계 정보
     * @return 생성 결과
     */
    int insertMonthlyStats(AttendanceStatsVO vo);
    
    /**
     * 월별 통계 업데이트
     * @param vo - 통계 정보
     * @return 업데이트 결과
     */
    int updateMonthlyStats(AttendanceStatsVO vo);

    /**
     * 급여계산용 근태 통계 조회
     * @param params - 사원ID, 년월, 회사ID
     * @return 근태 통계 정보
     */
    com.dadam.hr.attendance.service.AttendanceStatisticsVO getAttendanceStatistics(java.util.Map<String, Object> params);
} 