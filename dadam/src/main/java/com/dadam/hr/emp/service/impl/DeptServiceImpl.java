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
    public List<DeptVO> getDeptList() {
        return deptMapper.getDeptList();
    }

    /**
     * 부서 등록
     * @param deptVO 부서 정보
     * @return 등록 결과
     */
    @Override
    public boolean insertDept(DeptVO deptVO) {
        int result = deptMapper.insertDept(deptVO);
        return result > 0;
    }

    /**
     * 부서 수정
     * @param deptVO 부서 정보
     * @return 수정 결과
     */
    @Override
    public boolean updateDept(DeptVO deptVO) {
        int result = deptMapper.updateDept(deptVO);
        return result > 0;
    }

    /**
     * 부서 삭제
     * @param deptCode 부서코드
     * @param comId 회사코드
     * @return 삭제 결과
     */
    @Override
    public boolean deleteDept(String deptCode, String comId) {
        int result = deptMapper.deleteDept(deptCode, comId);
        return result > 0;
    }

    /**
     * 부서 상세 조회
     * @param deptCode 부서코드
     * @param comId 회사코드
     * @return 부서 정보
     */
    @Override
    public DeptVO getDeptDetail(String deptCode, String comId) {
        return deptMapper.getDeptDetail(deptCode, comId);
    }

    /**
     * 조직도 트리 조회
     * @return 조직 트리
     */
    @Override
    public OrgNode getOrgTree() {
        List<DeptVO> allDepts = deptMapper.getDeptList();
        List<EmpVO> allEmps = empMapper.selectEmpList(null, null, null); // 전체 사원
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

    @Override
    public List<DeptVO> getDeptList(Map<String, String> params) {
        return deptMapper.getDeptList(params);
    }
} 