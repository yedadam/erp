package com.dadam.hr.emp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dadam.hr.emp.mapper.EmpMapper;
import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;
import com.dadam.hr.emp.service.MailService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {

    private final EmpMapper empMapper;
    private final MailService mailService;

    @Override
    public List<EmpVO> findEmpList(String keyword, String status, String dept) {
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("keyword", keyword);
        param.put("status", status);
        param.put("dept", dept);
        return empMapper.findEmpList(param);
    }

    @Override
    public EmpVO findEmpDetail(String empId) {
        return empMapper.findEmpDetail(empId);
    }

    @Override
    public int insertEmp(EmpVO empVO) {
        int result = empMapper.insertEmp(empVO);
        
        // 사원 등록 성공 시, 이메일 주소가 있으면 메일 발송
        if (result > 0 && empVO.getEmail() != null && !empVO.getEmail().isEmpty()) {
            mailService.sendEmpRegisterMail(empVO.getEmail(), empVO.getEmpName());
        }
        
        return result;
    }

    @Override
    public int updateEmp(EmpVO empVO) {
        return empMapper.updateEmp(empVO);
    }

    @Override
    public int deleteEmp(String empId) {
        return empMapper.deleteEmp(empId);
    }

    @Override
    public String getMaxEmpId() {
        return empMapper.getMaxEmpId();
    }
} 