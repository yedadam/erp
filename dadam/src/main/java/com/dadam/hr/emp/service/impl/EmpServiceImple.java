package com.dadam.hr.emp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dadam.hr.emp.mapper.EmpMapper;
import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;

//service bean등록
@Service
public class EmpServiceImple implements EmpService{
	
	//객체 주입
	@Autowired
	EmpMapper empMapper;
	
	//전체 조회
	@Override
	public List<EmpVO> findAll() {
		List<EmpVO> result = empMapper.findAll();
		return result;
	}
}
