package com.dadam.hr.emp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dadam.hr.emp.service.EmpVO;

/**
 * 사원 매퍼 인터페이스
 * - 사원 관련 DB 연동 메서드 정의
 */
@Mapper
public interface EmpMapper {

    /**
     * 사원 목록 조회
     * @param keyword 검색어
     * @param status 재직상태
     * @param dept 부서코드
     * @return 사원 리스트
     */
    public List<EmpVO> selectEmpList(@Param("keyword") String keyword, @Param("status") String status, @Param("dept") String dept);

    /**
     * 사원 상세 조회
     * @param empId 사원번호
     * @return 사원 정보
     */
    public EmpVO selectEmpDetail(@Param("empId") String empId);

    /**
     * 사원 등록
     * @param empVO - 사원 정보
     * @return 등록 결과
     */
    int insertEmp(EmpVO empVO);

    /**
     * 사원 수정
     * @param empVO - 사원 정보
     * @return 수정 결과
     */
    int updateEmp(EmpVO empVO);

    /**
     * 사원 삭제
     * @param empId - 사원번호
     * @return 삭제 결과
     */
    int deleteEmp(String empId);

    /**
     * 최대 사번 조회
     * @return 사번
     */
    String getMaxEmpId();

    /**
     * 연차 총일수 업데이트
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param totalLeave - 연차 총일수
     */
    void updateAnnualLeaveTotal(String empId, String comId, int totalLeave);
    
    /**
     * 연차 정보 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 연차 정보
     */
    EmpVO getAnnualLeaveInfo(@Param("empId") String empId, @Param("comId") String comId);
    
    /**
     * 연차 정보 업데이트
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param totalLeave - 연차 총일수
     * @param usedLeave - 연차 사용일수
     * @return 업데이트 결과
     */
    int updateAnnualLeaveInfo(@Param("empId") String empId, @Param("comId") String comId, 
                             @Param("totalLeave") int totalLeave, @Param("usedLeave") int usedLeave);
    
    /**
     * 연차 사용 처리
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param usedDays - 사용일수
     * @return 처리 결과
     */
    int useAnnualLeave(@Param("empId") String empId, @Param("comId") String comId, @Param("usedDays") int usedDays);
}
