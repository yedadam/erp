package com.dadam.hr.emp.service;

import java.util.List;
import java.util.Map;

/**
 * 부서 서비스 인터페이스
 * - 부서 관련 비즈니스 로직 정의
 */
public interface DeptService {
    /**
     * 부서 목록 조회
     * @return 부서 리스트
     */
    List<DeptVO> getDeptList();
    /**
     * 검색 파라미터 기반 부서 목록 조회
     */
    List<DeptVO> getDeptList(Map<String, String> params);
    /**
     * 부서 등록
     * @param deptVO 부서 정보
     * @return 등록 성공 여부
     */
    boolean insertDept(DeptVO deptVO);
    /**
     * 부서 수정
     * @param deptVO 부서 정보
     * @return 수정 성공 여부
     */
    boolean updateDept(DeptVO deptVO);
    /**
     * 부서 삭제
     * @param deptCode 부서코드
     * @param comId 회사코드
     * @return 삭제 성공 여부
     */
    boolean deleteDept(String deptCode, String comId);
    /**
     * 부서 상세 조회
     * @param deptCode 부서코드
     * @param comId 회사코드
     * @return 부서 정보
     */
    DeptVO getDeptDetail(String deptCode, String comId);
    /**
     * 조직도 트리 조회
     * @return 조직 트리
     */
    OrgNode getOrgTree();
} 