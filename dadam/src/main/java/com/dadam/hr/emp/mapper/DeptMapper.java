package com.dadam.hr.emp.mapper;

import com.dadam.hr.emp.service.DeptVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 부서 매퍼 인터페이스
 * - 부서 관련 DB 연동 메서드 정의
 */
@Mapper
public interface DeptMapper {
    /**
     * 부서 목록 조회
     * @return 부서 리스트
     */
    List<DeptVO> getDeptList();
    /**
     * 부서 상세 조회
     * @param deptCode 부서코드
     * @param comId 회사코드
     * @return 부서 정보
     */
    DeptVO getDeptDetail(@Param("deptCode") String deptCode, @Param("comId") String comId);
    /**
     * 부서 등록
     * @param deptVO 부서 정보
     * @return 등록 결과
     */
    int insertDept(DeptVO deptVO);
    /**
     * 부서 수정
     * @param deptVO 부서 정보
     * @return 수정 결과
     */
    int updateDept(DeptVO deptVO);
    /**
     * 부서 삭제
     * @param deptCode 부서코드
     * @param comId 회사코드
     * @return 삭제 결과
     */
    int deleteDept(@Param("deptCode") String deptCode, @Param("comId") String comId);
} 