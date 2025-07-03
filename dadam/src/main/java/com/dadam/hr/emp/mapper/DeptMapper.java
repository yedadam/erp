package com.dadam.hr.emp.mapper;

import com.dadam.hr.emp.service.DeptVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 부서 매퍼 인터페이스
 */
@Mapper
public interface DeptMapper {
    /**
     * 부서 전체 조회
     * @return 부서 리스트
     */
    List<DeptVO> findAllDepartments();
    /**
     * 부서 등록
     * @param dept - 부서 정보
     * @return 등록 결과
     */
    int insertDepartment(DeptVO dept);
    /**
     * 부서 수정
     * @param dept - 부서 정보
     * @return 수정 결과
     */
    int updateDepartment(DeptVO dept);
    /**
     * 부서 삭제
     * @param deptCode - 부서코드
     * @return 삭제 결과
     */
    int deleteDepartment(String deptCode);
    /**
     * 부서 상세 조회
     * @param deptCode - 부서코드
     * @return 부서 정보
     */
    DeptVO findDepartmentByCode(String deptCode);
} 