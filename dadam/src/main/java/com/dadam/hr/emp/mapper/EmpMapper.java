package com.dadam.hr.emp.mapper;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

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
     * @param param - 검색 파라미터
     * @return 사원 리스트
     */
    public List<EmpVO> findEmpList(Map<String, Object> param);

    /**
     * 사원 상세 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 사원 정보
     */
    public EmpVO getEmpDetail(@Param("empId") String empId, @Param("comId") String comId);

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
     * @param param - 사원번호, 회사ID
     * @return 삭제 결과
     */
    public int deleteEmp(Map<String, String> param);

    /**
     * 최대 사번 조회
     * @return 사번
     */
    public String getMaxEmpId();

    /**
     * 연차 총일수 업데이트
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param totalLeave - 연차 총일수
     */
    public void updateAnnualLeaveTotal(String empId, String comId, int totalLeave);
    
    /**
     * 연차 정보 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 연차 정보
     */
    public EmpVO getAnnualLeaveInfo(@Param("empId") String empId, @Param("comId") String comId);
    
    /**
     * 연차 정보 업데이트
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param totalLeave - 연차 총일수
     * @param usedLeave - 연차 사용일수
     * @return 업데이트 결과
     */
    public int updateAnnualLeaveInfo(@Param("empId") String empId, @Param("comId") String comId, 
                             @Param("totalLeave") int totalLeave, @Param("usedLeave") int usedLeave);
    
    /**
     * 연차 사용 처리
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param usedDays - 사용일수
     * @return 처리 결과
     */
    public int useAnnualLeave(@Param("empId") String empId, @Param("comId") String comId, @Param("usedDays") int usedDays);
    
    // === 스케줄러에서 사용하는 메서드들 ===
    
    /**
     * 신입 사원 조회 (특정 날짜 이후 입사)
     * @param comId - 회사ID
     * @param date - 기준일
     * @return 신입 사원 리스트
     */
    public List<EmpVO> selectNewEmployees(@Param("comId") String comId, @Param("date") LocalDate date);
    
    /**
     * 입사일 기준 사원 조회
     * @param comId - 회사ID
     * @param hireDate - 입사일
     * @return 해당 입사일의 사원 리스트
     */
    public List<EmpVO> selectEmployeesByHireDate(@Param("comId") String comId, @Param("hireDate") LocalDate hireDate);
    
    // === 사원별 급여항목(EMP_ALLOWANCE) 관련 메서드들 ===
    
    /**
     * 사원별 급여항목 조회
     * @param param - 사원번호, 회사ID
     * @return 급여항목 리스트
     */
    public List<Map<String, Object>> findEmpAllowances(Map<String, String> param);
    
    /**
     * 사원별 급여항목 등록
     * @param param - 사원번호, 회사ID, 급여항목코드, 금액, 비고
     * @return 등록 결과
     */
    public int insertEmpAllowance(Map<String, Object> param);
    
    /**
     * 사원별 급여항목 수정
     * @param param - 사원번호, 회사ID, 급여항목코드, 금액, 비고
     * @return 수정 결과
     */
    public int updateEmpAllowance(Map<String, Object> param);
    
    /**
     * 사원별 급여항목 삭제
     * @param param - 사원번호, 회사ID, 급여항목코드
     * @return 삭제 결과
     */
    public int deleteEmpAllowance(Map<String, String> param);
    
    /**
     * 사원별 급여항목 일괄 삭제
     * @param param - 사원번호, 회사ID
     * @return 삭제 결과
     */
    public int deleteAllEmpAllowances(Map<String, String> param);
}
