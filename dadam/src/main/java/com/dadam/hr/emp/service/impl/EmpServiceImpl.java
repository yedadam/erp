package com.dadam.hr.emp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dadam.hr.emp.mapper.EmpMapper;
import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사원 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {

    /** 사원 Mapper */
    private final EmpMapper empMapper;

    /**
     * 사원 목록 조회
     * @param keyword - 검색어
     * @param status - 재직상태
     * @param dept - 부서코드
     * @return 사원 리스트
     */
    @Override
    public List<EmpVO> findEmpList(String keyword, String status, String dept) {
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("keyword", keyword);
        param.put("status", status);
        param.put("dept", dept);
        return empMapper.findEmpList(param);
    }

    /**
     * 사원 상세 조회
     * @param empId - 사원번호
     * @return 사원 정보
     */
    @Override
    public EmpVO findEmpDetail(String empId) {
        return empMapper.findEmpDetail(empId);
    }

    /**
     * 사원 등록
     * @param empVO - 사원 정보
     * @return 등록 결과
     */
    @Override
    public int insertEmp(EmpVO empVO) {
        log.info("사원 등록 시작: {} ({})", empVO.getEmpName(), empVO.getEmpId());
        int result = empMapper.insertEmp(empVO);
        
        if (result > 0) {
            log.info("✅ 사원 등록 성공: {} ({})", empVO.getEmpName(), empVO.getEmpId());
        } else {
            log.error("❌ 사원 등록 실패: {} ({})", empVO.getEmpName(), empVO.getEmpId());
        }
        
        return result;
    }

    /**
     * 사원 수정
     * @param empVO - 사원 정보
     * @return 수정 결과
     */
    @Override
    public int updateEmp(EmpVO empVO) {
        log.info("사원 정보 수정: {} ({})", empVO.getEmpName(), empVO.getEmpId());
        int result = empMapper.updateEmp(empVO);
        
        if (result > 0) {
            log.info("✅ 사원 정보 수정 성공: {} ({})", empVO.getEmpName(), empVO.getEmpId());
        } else {
            log.error("❌ 사원 정보 수정 실패: {} ({})", empVO.getEmpName(), empVO.getEmpId());
        }
        
        return result;
    }

    /**
     * 사원 삭제(퇴사)
     * @param empId - 사원번호
     * @return 삭제 결과
     */
    @Override
    public int deleteEmp(String empId) {
        log.info("사원 삭제: {}", empId);
        int result = empMapper.deleteEmp(empId);
        
        if (result > 0) {
            log.info("✅ 사원 삭제 성공: {}", empId);
        } else {
            log.error("❌ 사원 삭제 실패: {}", empId);
        }
        
        return result;
    }

    /**
     * 최대 사번 조회
     * @return 사번
     */
    @Override
    public String getMaxEmpId() {
        return empMapper.getMaxEmpId();
    }
} 