package com.dadam.hr.emp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.dadam.hr.emp.service.EmpVO;

@Mapper
public interface EmpMapper {

    // 사원 전체 조회
    public List<EmpVO> findEmpList(Map<String, Object> param);  // XML과 일치시킴

    // 사원 상세 조회
    public EmpVO findEmpDetail(String empId);

    // 사원 등록
    public int insertEmp(EmpVO empVO);

    // 사원 수정
    public int updateEmp(EmpVO empVO);

    // 사원 삭제
    public int deleteEmp(String empId);

    // 사원번호 최대값 조회
    String getMaxEmpId();
}
