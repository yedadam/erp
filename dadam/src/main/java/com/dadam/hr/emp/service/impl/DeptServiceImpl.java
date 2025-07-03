package com.dadam.hr.emp.service.impl;

import com.dadam.hr.emp.mapper.DeptMapper;
import com.dadam.hr.emp.service.DeptService;
import com.dadam.hr.emp.service.DeptVO;
import com.dadam.hr.emp.service.OrgNode;
import com.dadam.hr.emp.mapper.EmpMapper;
import com.dadam.hr.emp.service.EmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 부서 서비스 구현체
 */
@Service
public class DeptServiceImpl implements DeptService {
    /** 부서 Mapper */
    @Autowired
    private DeptMapper deptMapper;
    /** 사원 Mapper */
    @Autowired
    private EmpMapper empMapper;

    /**
     * 부서 전체 조회
     * @return 부서 리스트
     */
    @Override
    public List<DeptVO> findAllDepartments() {
        return deptMapper.findAllDepartments();
    }

    /**
     * 부서 등록
     * @param dept - 부서 정보
     * @return 등록 결과
     */
    @Override
    public int insertDepartment(DeptVO dept) {
        return deptMapper.insertDepartment(dept);
    }

    /**
     * 부서 수정
     * @param dept - 부서 정보
     * @return 수정 결과
     */
    @Override
    public int updateDepartment(DeptVO dept) {
        return deptMapper.updateDepartment(dept);
    }

    /**
     * 부서 삭제
     * @param deptCode - 부서코드
     * @return 삭제 결과
     */
    @Override
    public int deleteDepartment(String deptCode) {
        return deptMapper.deleteDepartment(deptCode);
    }

    /**
     * 부서 상세 조회
     * @param deptCode - 부서코드
     * @return 부서 정보
     */
    @Override
    public DeptVO findDepartmentByCode(String deptCode) {
        return deptMapper.findDepartmentByCode(deptCode);
    }

    /**
     * 조직도 트리 조회
     * @return 조직 트리
     */
    @Override
    public OrgNode getOrgTree() {
        List<DeptVO> allDepts = deptMapper.findAllDepartments();
        List<EmpVO> allEmps = empMapper.findEmpList(new HashMap<>()); // 전체 사원
        Map<String, DeptVO> deptMap = allDepts.stream().collect(Collectors.toMap(DeptVO::getDeptCode, d -> d));
        Map<String, List<EmpVO>> empMap = allEmps.stream()
            .filter(e -> e.getDeptCode() != null)
            .collect(Collectors.groupingBy(EmpVO::getDeptCode));
        Map<String, OrgNode> nodeMap = new HashMap<>();
        List<OrgNode> roots = new ArrayList<>();
        for (DeptVO dept : allDepts) {
            OrgNode node = new OrgNode();
            node.setDeptCode(dept.getDeptCode());
            node.setDeptName(dept.getDeptName());
            EmpVO manager = null;
            if (dept.getEmpId() != null) {
                manager = allEmps.stream()
                    .filter(e -> dept.getEmpId().equals(e.getEmpId()))
                    .findFirst()
                    .orElse(null);
            }
            node.setManager(manager);
            List<EmpVO> emps = empMap.getOrDefault(dept.getDeptCode(), new ArrayList<>());
            node.setEmployees(
                emps.stream()
                    .filter(e -> dept.getEmpId() == null || !dept.getEmpId().equals(e.getEmpId()))
                    .collect(Collectors.toList())
            );
            System.out.println("부서코드: " + dept.getDeptCode() + ", 부서명: " + dept.getDeptName()
                + ", 직원수: " + emps.size()
                + ", 부서장: " + (manager != null ? manager.getEmpName() : "없음"));
            node.setChildren(new ArrayList<>());
            nodeMap.put(dept.getDeptCode(), node);
        }
        for (DeptVO dept : allDepts) {
            if (dept.getParentDeptCode() != null && nodeMap.containsKey(dept.getParentDeptCode())) {
                nodeMap.get(dept.getParentDeptCode()).getChildren().add(nodeMap.get(dept.getDeptCode()));
            } else {
                roots.add(nodeMap.get(dept.getDeptCode()));
            }
        }
        return roots.isEmpty() ? null : roots.get(0);
    }
} 