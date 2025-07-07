package com.dadam.hr.salary.service;

import java.util.List;

/**
 * 급여항목 마스터 Service
 */
public interface SalaryItemService {
    /** 전체 급여항목 목록 조회 */
    List<SalaryItemVO> getSalaryItemList(String comId);
    /** 단일 급여항목 조회 */
    SalaryItemVO getSalaryItem(String comId, String allowCode);
    /** 급여항목 등록 */
    int addSalaryItem(SalaryItemVO vo);
    /** 급여항목 수정 */
    int modifySalaryItem(SalaryItemVO vo);
    /** 급여항목 삭제 */
    int removeSalaryItem(String comId, String allowCode);
} 