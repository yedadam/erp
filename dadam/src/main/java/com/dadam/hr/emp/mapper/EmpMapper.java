package com.dadam.hr.emp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dadam.hr.emp.service.EmpVO;

/**
 * 사원 매퍼 인터페이스
 */
@Mapper
public interface EmpMapper {

    /**
     * 사원 목록 조회
     * @param param - 검색 파라미터
     * @return 사원 리스트
     */
    public List<EmpVO> findEmpList(Map<String, Object> param);  // XML과 일치시킴

    /**
     * 사원 상세 조회
     * @param empId - 사원번호
     * @return 사원 정보
     */
    public EmpVO findEmpDetail(@Param("empId") String empId,@Param("comId") String comId);

    /**
     * 사원 등록
     * @param empVO - 사원 정보
     * @return 등록 결과
     */
    public int insertEmp(EmpVO empVO);

    /**
     * 사원 수정
     * @param empVO - 사원 정보
     * @return 수정 결과
     */
    public int updateEmp(EmpVO empVO);

    /**
     * 사원 삭제
     * @param empId - 사원번호
     * @return 삭제 결과
     */
    public int deleteEmp(String empId);

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
}
