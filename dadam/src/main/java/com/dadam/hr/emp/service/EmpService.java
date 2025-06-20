package com.dadam.hr.emp.service;

import java.util.List;

public interface EmpService {

	// 사원 전체 조회
	List<EmpVO> getEmpList();

	// 사원 상세 조회
	EmpVO getEmpDetail(String empId);

	// 사원 등록/수정 (신규/수정 통합)
	int saveEmp(EmpVO empVO);

	// 사원 삭제
	int deleteEmp(String empId);
}
