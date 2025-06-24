package com.dadam.hr.emp.service.impl;

import com.dadam.hr.emp.mapper.DeptMapper;
import com.dadam.hr.emp.service.DeptService;
import com.dadam.hr.emp.service.DeptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<DeptVO> findAllDepartments() {
        return deptMapper.findAllDepartments();
    }
} 