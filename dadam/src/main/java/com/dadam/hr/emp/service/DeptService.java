package com.dadam.hr.emp.service;

import java.util.List;

/**
 * 부서 서비스 인터페이스
 */
public interface DeptService {
    /** 부서 전체 조회 */
    List<DeptVO> findAllDepartments();
    /** 부서 등록 */
    int insertDepartment(DeptVO dept);
    /** 부서 수정 */
    int updateDepartment(DeptVO dept);
    /** 부서 삭제 */
    int deleteDepartment(String deptCode);
    /** 부서 상세 조회 */
    DeptVO findDepartmentByCode(String deptCode);
    /** 조직도 트리 조회 */
    OrgNode getOrgTree();
} 