package com.dadam.hr.emp.service;

import java.util.List;
import java.time.LocalDate;

/**
 * 사원 서비스 인터페이스
 * - 사원 관련 비즈니스 로직 정의
 */
public interface EmpService {
    /**
     * 사원 목록 조회
     * @param param - 검색 파라미터
     * @return 사원 리스트
     */
    List<EmpVO> findEmpList(java.util.Map<String, Object> param);
    /**
     * 사원 상세 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 사원 정보
     */
    EmpVO getEmpDetail(String empId, String comId);
    /**
     * 사원 등록
     * @param empVO 사원 정보
     * @return 등록 성공 여부
     */
    boolean insertEmp(EmpVO empVO);
    /**
     * 사원 수정
     * @param empVO 사원 정보
     * @return 수정 성공 여부
     */
    boolean updateEmp(EmpVO empVO);
    /**
     * 사원 삭제
     * @param empId 사원번호
     * @param comId 회사ID
     * @return 삭제 성공 여부
     */
    boolean deleteEmp(String empId, String comId);
    /**
     * 최대 사번 조회
     * @return 사번
     */
    String getMaxEmpId();
    
    /**
     * 연차 정보 조회
     * @param empId - 사원번호
     * @return 연차 정보
     */
    EmpVO getAnnualLeaveInfo(String empId);
    
    /**
     * 연차 정보 업데이트
     * @param empId - 사원번호
     * @param totalLeave - 연차 총일수
     * @param usedLeave - 연차 사용일수
     * @return 업데이트 결과
     */
    int updateAnnualLeaveInfo(String empId, int totalLeave, int usedLeave);
    
    /**
     * 연차 사용 처리
     * @param empId - 사원번호
     * @param usedDays - 사용일수
     * @return 처리 결과
     */
    int useAnnualLeave(String empId, int usedDays);
    
    // === 스케줄러에서 사용하는 메서드들 ===
    
    /**
     * 신입 사원 조회 (특정 날짜 이후 입사)
     * @param date - 기준일
     * @return 신입 사원 리스트
     */
    List<EmpVO> getNewEmployees(LocalDate date);
    
    /**
     * 입사일 기준 사원 조회
     * @param hireDate - 입사일
     * @return 해당 입사일의 사원 리스트
     */
    List<EmpVO> getEmployeesByHireDate(LocalDate hireDate);
    
    // === 사원별 급여항목(EMP_ALLOWANCE) 관련 메서드들 ===
    
    /**
     * 사원별 급여항목 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 급여항목 리스트
     */
    List<java.util.Map<String, Object>> getEmpAllowances(String empId, String comId);
    
    /**
     * 사원별 급여항목 등록
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param allowCode - 급여항목코드
     * @param amount - 금액
     * @param note - 비고
     * @return 등록 결과
     */
    boolean insertEmpAllowance(String empId, String comId, String allowCode, Double amount, String note);
    
    /**
     * 사원별 급여항목 수정
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param allowCode - 급여항목코드
     * @param amount - 금액
     * @param note - 비고
     * @return 수정 결과
     */
    boolean updateEmpAllowance(String empId, String comId, String allowCode, Double amount, String note);
    
    /**
     * 사원별 급여항목 삭제
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param allowCode - 급여항목코드
     * @return 삭제 결과
     */
    boolean deleteEmpAllowance(String empId, String comId, String allowCode);
} 