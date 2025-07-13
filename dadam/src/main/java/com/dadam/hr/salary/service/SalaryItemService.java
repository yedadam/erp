package com.dadam.hr.salary.service;

import java.util.List;
import java.util.Map;

/**
 * 급여항목 마스터 Service
 */
public interface SalaryItemService {
    /**
     * 전체 급여항목 목록 조회
     * @param map 검색 조건 (회사ID 등)
     * @return 급여항목 리스트
     * 적용대상(사원유형) 처리는 employees.work_type 기준으로 별도 쿼리/로직에서 처리
     */
    List<SalaryItemVO> getSalaryItemList(Map<String, Object> map);
    /** 단일 급여항목 조회 */
    SalaryItemVO getSalaryItem(String comId, String allowCode);
    /** 마지막 항목코드 조회 (자동생성용) */
    String getLastAllowCode(String comId);
    /** 급여항목 등록 */
    int addSalaryItem(SalaryItemVO vo);
    /** 급여항목 수정 */
    int modifySalaryItem(SalaryItemVO vo);
    /** 급여항목 삭제 */
    int removeSalaryItem(String comId, String allowCode);
} 