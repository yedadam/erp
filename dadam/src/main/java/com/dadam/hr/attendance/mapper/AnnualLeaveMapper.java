package com.dadam.hr.attendance.mapper;

import com.dadam.hr.attendance.service.AnnualLeaveVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

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
} 