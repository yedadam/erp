package com.dadam.hr.emp.service;

import java.util.List;

public interface EmpService {
    List<EmpVO> findEmpList(String keyword, String status);
    EmpVO findEmpDetail(String empId);
    int insertEmp(EmpVO empVO); // 사원 등록
    int updateEmp(EmpVO empVO); // 사원 수정
    int deleteEmp(String empId); // 사원 삭제(퇴사)
} 