package com.dadam.hr.emp.service;

import java.util.List;

/**
 * 사원 서비스 인터페이스
 */
public interface EmpService {
    /**
     * 사원 목록 조회
     * @param keyword - 검색어
     * @param status - 재직상태
     * @param dept - 부서코드
     * @return 사원 리스트
     */
    List<EmpVO> findEmpList(String keyword, String status, String dept);
    /**
     * 사원 상세 조회
     * @param empId - 사원번호
     * @return 사원 정보
     */
    EmpVO findEmpDetail(String empId);
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
     * 사원 삭제(퇴사)
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
} 