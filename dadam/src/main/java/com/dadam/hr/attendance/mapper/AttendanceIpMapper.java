package com.dadam.hr.attendance.mapper;

import com.dadam.hr.attendance.service.AttendanceIpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 출퇴근 IP 관리 Mapper
 */
@Mapper
public interface AttendanceIpMapper {
    
    /**
     * IP 목록 조회
     * @param comId - 회사ID
     * @param type - 검색타입
     * @param value - 검색값
     * @return IP 리스트
     */
    List<AttendanceIpVO> findIpList(@Param("comId") String comId, 
                                   @Param("type") String type, 
                                   @Param("value") String value);
    
    /**
     * IP 단건 조회
     * @param ipCode - IP 관리코드
     * @param comId - 회사ID
     * @return IP 정보
     */
    AttendanceIpVO findIpDetail(@Param("ipCode") String ipCode, 
                               @Param("comId") String comId);
    
    /**
     * IP 등록
     * @param vo - IP 정보
     * @return 등록 결과
     */
    int insertIp(AttendanceIpVO vo);
    
    /**
     * IP 수정
     * @param vo - IP 정보
     * @return 수정 결과
     */
    int updateIp(AttendanceIpVO vo);
    
    /**
     * IP 삭제
     * @param ipCode - IP 관리코드
     * @param comId - 회사ID
     * @return 삭제 결과
     */
    int deleteIp(@Param("ipCode") String ipCode, 
                 @Param("comId") String comId);
    
    /**
     * IP 허용 여부 확인
     * @param ipAddress - IP 주소
     * @param comId - 회사ID
     * @return 허용 여부
     */
    boolean isAllowedIp(@Param("ipAddress") String ipAddress, 
                       @Param("comId") String comId);
} 