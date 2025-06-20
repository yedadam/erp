package com.dadam.hr.emp.mapper;

import java.util.List;

import com.dadam.hr.emp.service.EmpVO;

public interface EmpMapper {
    // 사원 전체 조회
    List<EmpVO> getEmpList();

    // 사원 상세 조회
    EmpVO getEmpDetail(String empId);

    // 사원 등록
    int insertEmp(EmpVO empVO);

    // 사원 수정
    int updateEmp(EmpVO empVO);

    // 사원 삭제
    int deleteEmp(String empId);
}