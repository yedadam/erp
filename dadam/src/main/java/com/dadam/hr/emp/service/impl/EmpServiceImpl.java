package com.dadam.hr.emp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.hr.emp.mapper.EmpMapper;
import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;

@Service
public class EmpServiceImpl implements EmpService {

	@Autowired
	EmpMapper empMapper;

	// 사원 전체 조회
	@Override
	public List<EmpVO> getEmpList() {
		return empMapper.getEmpList();
	}

	// 사원 상세 조회
	@Override
	public EmpVO getEmpDetail(String empId) {
		return empMapper.getEmpDetail(empId);
	}

	// 사원 등록/수정 (신규/수정 통합)
	@Override
	public int saveEmp(EmpVO empVO) {
		if(empVO.getEmpId() == null || empVO.getEmpId().isEmpty()) {
			return empMapper.insertEmp(empVO);
		} else {
			return empMapper.updateEmp(empVO);
		}
	}

	// 사원 삭제
	@Override
	public int deleteEmp(String empId) {
		return empMapper.deleteEmp(empId);
	}
}
