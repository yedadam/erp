package com.dadam.hr.emp.mapper;

import com.dadam.hr.emp.service.EmpVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    List<EmpVO> findEmpList(java.util.Map<String, Object> param);
    EmpVO findEmpDetail(String empId);
    int insertEmp(EmpVO empVO);
    int updateEmp(EmpVO empVO);
    int deleteEmp(String empId);
} 