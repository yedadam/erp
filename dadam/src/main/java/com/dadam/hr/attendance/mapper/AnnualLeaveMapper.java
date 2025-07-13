package com.dadam.hr.attendance.mapper;

import com.dadam.hr.attendance.service.AnnualLeaveVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

/**
 * 연차 매퍼 인터페이스
 */
@Mapper
public interface AnnualLeaveMapper {
    /**
     * 연차 등록
     * @param vo - 연차 정보
     * @return 등록 결과
     */
    int insertAnnualLeave(AnnualLeaveVO vo);
    /**
     * 연차 상태/승인 처리
     * @param vo - 연차 정보
     * @return 처리 결과
     */
    int updateAnnualLeaveStatus(AnnualLeaveVO vo);
    /**
     * 연차 단건 조회
     * @param leaveCode - 연차코드
     * @param comId - 회사ID
     * @return 연차 정보
     */
    AnnualLeaveVO selectAnnualLeave(String leaveCode, String comId);
    /**
     * 연차 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 연차 리스트
     */
    List<AnnualLeaveVO> findAnnualLeaveList(String comId, String empId);
    /**
     * 잔여 연차 계산
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 잔여 연차
     */
    double getRemainAnnualLeave(String empId, String comId);
    
    // === 스케줄러에서 사용하는 메서드들 ===
    
    /**
     * 연차 수정
     * @param vo - 연차 정보
     * @return 수정 결과
     */
    int updateAnnualLeave(AnnualLeaveVO vo);
    
    /**
     * 사원별 연차 조회 (연도별)
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param year - 연도
     * @return 연차 정보
     */
    AnnualLeaveVO selectAnnualLeaveByEmpAndYear(String empId, String comId, int year);
    
    /**
     * 만료된 연차 조회
     * @param date - 기준일
     * @return 만료된 연차 리스트
     */
    List<AnnualLeaveVO> selectExpiredAnnualLeaves(Map<String, Object> param);
    
    /**
     * 미사용 연차 사원 조회
     * @param date - 기준일
     * @return 미사용 연차 사원 리스트
     */
    List<AnnualLeaveVO> selectUnusedLeaveEmployees(Map<String, Object> param);
    
    /**
     * 오늘 사용된 연차 조회
     * @param date - 기준일
     * @return 오늘 사용된 연차 리스트
     */
    List<AnnualLeaveVO> selectUsedLeavesToday(Map<String, Object> param);
    
    /**
     * 월별 연차 통계 생성
     * @param date - 기준일
     * @return 통계 데이터
     */
    Map<String, Object> generateMonthlyLeaveStatistics(Map<String, Object> param);
    
    /**
     * 월별 연차 통계 저장
     * @param statistics - 통계 데이터
     * @return 저장 결과
     */
    int insertMonthlyLeaveStatistics(Map<String, Object> statistics);
} 