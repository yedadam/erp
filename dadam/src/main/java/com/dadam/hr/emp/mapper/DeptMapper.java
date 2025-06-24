package com.dadam.hr.emp.mapper;

import com.dadam.hr.emp.service.DeptVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DeptMapper {
    List<DeptVO> findAllDepartments();
} 