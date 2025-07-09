package com.dadam.hr.salary.mapper;

import com.dadam.hr.salary.service.SalaryItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 급여항목 마스터 Mapper
 */
@Mapper
public interface SalaryItemMapper {
    /** 전체 급여항목 목록 조회 */
    public List<SalaryItemVO> selectSalaryItemList(@Param("comId") String comId);
    /** 단일 급여항목 조회 */
    public SalaryItemVO selectSalaryItem(@Param("comId") String comId, @Param("allowCode") String allowCode);
    /** 급여항목 등록 */
    public int insertSalaryItem(SalaryItemVO vo);
    /** 급여항목 수정 */
    public int updateSalaryItem(SalaryItemVO vo);
    /** 급여항목 삭제 */
    public int deleteSalaryItem(@Param("comId") String comId, @Param("allowCode") String allowCode);
} 