package com.dadam.hr.emp.service;

import java.util.List;

public interface DeptService {
    List<DeptVO> findAllDepartments();
    int insertDepartment(DeptVO dept);
    int updateDepartment(DeptVO dept);
    int deleteDepartment(String deptCode);
    DeptVO findDepartmentByCode(String deptCode);
} 