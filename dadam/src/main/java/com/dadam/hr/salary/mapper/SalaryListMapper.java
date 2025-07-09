package com.dadam.hr.salary.mapper;

import com.dadam.hr.salary.service.SalaryListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 급여명세 리스트 관리 Mapper 인터페이스
 * 
 * @author 팀장
 * @since 2024-01-01
 */
@Mapper
public interface SalaryListMapper {
    
    /**
     * 급여명세 목록 조회
     * 
     * @param searchVO 검색 조건
     * @return 급여명세 목록
     */
    public List<SalaryListVO> selectSalaryList(SalaryListVO searchVO);
    
    /**
     * 급여명세 상태 업데이트
     * 
     * @param updateVO 업데이트할 정보
     * @return 처리된 건수
     */
    public int updateSalaryStatus(SalaryListVO updateVO);
    
    /**
     * 급여명세 삭제
     * 
     * @param salaryId 삭제할 급여명세 ID
     * @return 처리된 건수
     */
    public int deleteSalary(String salaryId);
} 