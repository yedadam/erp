package com.dadam.hr.salary.web;

import com.dadam.hr.salary.service.SalaryStatementService;
import com.dadam.hr.salary.service.SalaryStatementVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 급여명세서 Rest 컨트롤러
 * - 급여명세서 등록/수정/삭제, 목록/상세 등 담당
 */
@RestController
@RequestMapping("/erp/hr")
public class SalaryStatementRestController {
    /** 급여명세서 서비스 */
    @Autowired
    private SalaryStatementService salaryStatementService;

    /**
     * 급여명세서 목록 조회 (DB 구조 일치)
     * @param keyword 검색어
     * @param month 지급월(YYYYMM)
     * @param empId 사원ID
     * @param comId 회사ID
     * @return 급여명세서 리스트
     */
    @GetMapping("/salary/statement/list")
    public List<SalaryStatementVO> getSalaryList(@RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) String month,
                                                 @RequestParam(required = false) String empId,
                                                 @RequestParam(required = false) String comId) {
        // comId 우선순위: 프론트 파라미터 → 기본값
        if (comId == null || comId.isEmpty()) {
            comId = "com-101";
        }
        Map<String, Object> param = new HashMap<>();
        param.put("empId", empId);
        param.put("comId", comId);
        param.put("calcMonth", month);
        param.put("keyword", keyword);
        return salaryStatementService.getSalaryStatementList(param);
    }

    /**
     * 급여명세서 등록
     * @param vo - 등록 정보
     * @return 성공/실패 메시지
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody SalaryStatementVO vo) {
        Map<String, Object> result = new HashMap<>();
        int insertResult = salaryStatementService.addSalaryStatement(vo);
        boolean success = insertResult > 0;
        result.put("success", success);
        result.put("message", success ? "등록 성공" : "등록 실패");
        return result;
    }

    /**
     * 급여명세서 수정
     * @param vo - 수정 정보
     * @return 성공/실패 메시지
     */
    @PostMapping("/salary/edit-data")
    public Map<String, Object> edit(@RequestBody SalaryStatementVO vo) {
        Map<String, Object> result = new HashMap<>();
        int updateResult = salaryStatementService.modifySalaryStatement(vo);
        boolean success = updateResult > 0;
        result.put("success", success);
        result.put("message", success ? "수정 성공" : "수정 실패");
        return result;
    }

    /**
     * 급여명세서 삭제 (comId 인증정보 연동 필요)
     * @param id - SAL_ID
     * @param comId - 회사ID
     * @return 성공/실패 메시지
     */
    @PostMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id,
                                      @RequestParam(required = false) String comId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("salId", id);
        param.put("comId", comId); // TODO: 실제 로그인 정보에서 comId 추출 필요
        int deleteResult = salaryStatementService.removeSalaryStatement(param);
        boolean success = deleteResult > 0;
        result.put("success", success);
        result.put("message", success ? "삭제 성공" : "삭제 실패");
        return result;
    }
} 