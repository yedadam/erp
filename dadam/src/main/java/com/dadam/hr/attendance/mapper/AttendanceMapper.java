package com.dadam.hr.attendance.mapper;

import com.dadam.hr.attendance.service.AttendanceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 근태 매퍼 인터페이스
 * - 보안 검증: IP, GPS, 지점 검증
 * - 자동 판정: 지각, 조퇴, 연장근무 자동 계산
 * - 실시간 현황: 실시간 출퇴근 현황 조회
 */
@Mapper
public interface AttendanceMapper {
    /**
     * 출근 등록
     * @param param - 출근 정보
     * @return 등록 결과
     */
    int insertCheckIn(Map<String, Object> param);
    /**
     * 퇴근 처리
     * @param param - 퇴근 정보
     * @return 처리 결과
     */
    int updateCheckOut(Map<String, Object> param);
    /**
     * 오늘 근태 조회
     * @param param - 조회 조건
     * @return 근태 정보
     */
    AttendanceVO selectTodayAttendance(Map<String, Object> param);
    /**
     * 출근기록 존재여부 확인
     * @param param - 확인 조건
     * @return 존재 여부
     */
    boolean existsCheckIn(Map<String, Object> param);
    /**
     * 근태 목록 조회 (권한별 필터링)
     * @param param - 조회 조건
     * @return 근태 리스트
     */
    List<AttendanceVO> selectAttendanceList(Map<String, Object> param);
    /**
     * IP 검증
     * @param requestIp - 요청 IP
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 검증 결과
     */
    boolean validateIp(@Param("requestIp") String requestIp, @Param("comId") String comId, @Param("empId") String empId);
    /**
     * GPS 검증
     * @param gpsInfo - GPS 정보
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 검증 결과
     */
    boolean validateGps(@Param("gpsInfo") String gpsInfo, @Param("comId") String comId, @Param("empId") String empId);
    /**
     * 사원별 출퇴근 시간 설정 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 시간 설정 정보
     */
    Map<String, Object> getEmployeeTimeSettings(@Param("empId") String empId, @Param("comId") String comId);
    /**
     * 실시간 출퇴근 현황 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @return 실시간 현황
     */
    List<AttendanceVO> getRealtimeStatus(@Param("comId") String comId, @Param("deptCode") String deptCode);
    /**
     * 부서별 출퇴근 통계 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @param workDate - 근무일자
     * @return 통계 정보
     */
    Map<String, Object> getDeptAttendanceStats(@Param("comId") String comId, @Param("deptCode") String deptCode, @Param("workDate") String workDate);
    /**
     * 지각/조퇴 통계 조회
     * @param comId - 회사ID
     * @param deptCode - 부서코드
     * @param fromDate - 시작일
     * @param toDate - 종료일
     * @return 통계 정보
     */
    Map<String, Object> getLateEarlyStats(@Param("comId") String comId, @Param("deptCode") String deptCode, 
                                         @Param("fromDate") String fromDate, @Param("toDate") String toDate);
} 