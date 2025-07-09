package com.dadam.hr.emp.service;

import java.util.List;

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
} 