package com.dadam.hr.emp.service;

import lombok.Data;
import java.util.List;

/**
 * 조직 트리 노드 VO
 */
@Data
public class OrgNode {
    /** 부서코드 */
    private String deptCode;
    /** 부서명 */
    private String deptName;
    /** 부서장 정보 */
    private EmpVO manager;
    /** 부서 소속 직원(부서장 제외) */
    private List<EmpVO> employees;
    /** 하위 부서 */
    private List<OrgNode> children;
} 