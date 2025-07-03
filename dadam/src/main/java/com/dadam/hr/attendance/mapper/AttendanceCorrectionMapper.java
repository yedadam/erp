package com.dadam.hr.attendance.mapper;

import com.dadam.hr.attendance.service.AttendanceCorrectionVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 근태 정정 매퍼 인터페이스
 */
@Mapper
public interface AttendanceCorrectionMapper {
    /**
     * 정정 등록
     * @param vo - 정정 정보
     * @return 등록 결과
     */
    int insertCorrection(AttendanceCorrectionVO vo);
    /**
     * 정정 상태/승인 처리
     * @param vo - 정정 정보
     * @return 처리 결과
     */
    int updateCorrectionStatus(AttendanceCorrectionVO vo);
    /**
     * 정정 단건 조회
     * @param corrCode - 정정코드
     * @param comId - 회사ID
     * @return 정정 정보
     */
    AttendanceCorrectionVO selectCorrection(String corrCode, String comId);
    /**
     * 정정 목록 조회
     * @param comId - 회사ID
     * @param empId - 사원번호
     * @return 정정 리스트
     */
    List<AttendanceCorrectionVO> findCorrectionList(String comId, String empId);
} 