package com.dadam.hr.emp.service.impl;

import java.util.List;
import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dadam.hr.emp.mapper.EmpMapper;
import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;
import com.dadam.security.service.LoginUserAuthority;

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
    
    //comName 가져오기
    String comId = "com-101";
    public void initAuthInfo() {
        //로그인 객체값 연결
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //로그인 객체 가져오기
        Object principal = auth.getPrincipal();

        if (principal instanceof LoginUserAuthority) {
        	LoginUserAuthority user = (LoginUserAuthority) principal;
            comId = user.getComId();
            System.out.println("회사명: " + comId);
        }
    }

    /**
     * 사원 목록 조회
     * @param param - 검색 파라미터
     * @return 사원 리스트
     */
    @Override
    public List<EmpVO> findEmpList(java.util.Map<String, Object> param) {
        initAuthInfo();
        param.put("comId", comId);
        return empMapper.findEmpList(param);
    }

    /**
     * 사원 상세 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 사원 정보
     */
    @Override
    public EmpVO getEmpDetail(String empId, String comId) {
        return empMapper.getEmpDetail(empId, comId);
    }

    /**
     * 사원 등록
     * @param empVO - 사원 정보
     * @return 등록 결과
     */
    @Override
    public boolean insertEmp(EmpVO empVO) {
    	initAuthInfo();
    	empVO.setComId(comId);
        log.info("사원 등록 시작: {} ({})", empVO.getEmpName(), empVO.getEmpId());
        int result = empMapper.insertEmp(empVO);
        
        if (result > 0) {
            log.info("✅ 사원 등록 성공: {} ({})", empVO.getEmpName(), empVO.getEmpId());
            return true;
        } else {
            log.error("❌ 사원 등록 실패: {} ({})", empVO.getEmpName(), empVO.getEmpId());
            return false;
        }
    }

    /**
     * 사원 수정
     * @param empVO - 사원 정보
     * @return 수정 결과
     */
    @Override
    public boolean updateEmp(EmpVO empVO) {
    	initAuthInfo();
    	empVO.setComId(comId);
        log.info("사원 정보 수정: {} ({})", empVO.getEmpName(), empVO.getEmpId());
        int result = empMapper.updateEmp(empVO);
        
        if (result > 0) {
            log.info("✅ 사원 정보 수정 성공: {} ({})", empVO.getEmpName(), empVO.getEmpId());
            return true;
        } else {
            log.error("❌ 사원 정보 수정 실패: {} ({})", empVO.getEmpName(), empVO.getEmpId());
            return false;
        }
    }

    /**
     * 사원 삭제(퇴사)
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 삭제 결과
     */
    @Override
    public boolean deleteEmp(String empId, String comId) {
        // comId가 null 또는 빈 값이면 로그인 정보에서 보완
        if (comId == null || comId.isEmpty()) {
            initAuthInfo();
            comId = this.comId;
        }
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("empId", empId);
        param.put("comId", comId);
        int result = empMapper.deleteEmp(param);
        if (result > 0) {
            log.info("✅ 사원 삭제 성공: {} (회사: {})", empId, comId);
            return true;
        } else {
            log.error("❌ 사원 삭제 실패: {} (회사: {})", empId, comId);
            return false;
        }
    }

    /**
     * 최대 사번 조회
     * @return 사번
     */
    @Override
    public String getMaxEmpId() {
        return empMapper.getMaxEmpId();
    }

    /**
     * 연차 정보 조회
     * @param empId - 사원번호
     * @return 연차 정보
     */
    @Override
    public EmpVO getAnnualLeaveInfo(String empId) {
        initAuthInfo();
        log.info("연차 정보 조회: {} (회사: {})", empId, comId);
        return empMapper.getAnnualLeaveInfo(empId, comId);
    }

    /**
     * 연차 정보 업데이트
     * @param empId - 사원번호
     * @param totalLeave - 연차 총일수
     * @param usedLeave - 연차 사용일수
     * @return 업데이트 결과
     */
    @Override
    public int updateAnnualLeaveInfo(String empId, int totalLeave, int usedLeave) {
        initAuthInfo();
        log.info("연차 정보 업데이트: {} (총일수: {}, 사용일수: {})", empId, totalLeave, usedLeave);
        
        int result = empMapper.updateAnnualLeaveInfo(empId, comId, totalLeave, usedLeave);
        
        if (result > 0) {
            log.info("✅ 연차 정보 업데이트 성공: {} (총일수: {}, 사용일수: {})", empId, totalLeave, usedLeave);
        } else {
            log.error("❌ 연차 정보 업데이트 실패: {} (총일수: {}, 사용일수: {})", empId, totalLeave, usedLeave);
        }
        
        return result;
    }

    /**
     * 연차 사용 처리
     * @param empId - 사원번호
     * @param usedDays - 사용일수
     * @return 처리 결과
     */
    @Override
    public int useAnnualLeave(String empId, int usedDays) {
        initAuthInfo();
        log.info("연차 사용 처리: {} (사용일수: {})", empId, usedDays);
        
        int result = empMapper.useAnnualLeave(empId, comId, usedDays);
        
        if (result > 0) {
            log.info("✅ 연차 사용 처리 성공: {} (사용일수: {})", empId, usedDays);
        } else {
            log.error("❌ 연차 사용 처리 실패: {} (사용일수: {}) - 잔여 연차 부족 또는 권한 없음", empId, usedDays);
        }
        
        return result;
    }
    
    // === 스케줄러에서 사용하는 메서드들 ===
    
    /**
     * 신입 사원 조회 (특정 날짜 이후 입사)
     * @param date - 기준일
     * @return 신입 사원 리스트
     */
    @Override
    public List<EmpVO> getNewEmployees(LocalDate date) {
        initAuthInfo();
        return empMapper.selectNewEmployees(comId, date);
    }
    
    /**
     * 입사일 기준 사원 조회
     * @param hireDate - 입사일
     * @return 해당 입사일의 사원 리스트
     */
    @Override
    public List<EmpVO> getEmployeesByHireDate(LocalDate hireDate) {
        initAuthInfo();
        return empMapper.selectEmployeesByHireDate(comId, hireDate);
    }
    
    // === 사원별 급여항목(EMP_ALLOWANCE) 관련 메서드들 ===
    
    /**
     * 사원별 급여항목 조회
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @return 급여항목 리스트
     */
    @Override
    public List<java.util.Map<String, Object>> getEmpAllowances(String empId, String comId) {
        if (comId == null || comId.isEmpty()) {
            initAuthInfo();
            comId = this.comId;
        }
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("empId", empId);
        param.put("comId", comId);
        return empMapper.findEmpAllowances(param);
    }
    
    /**
     * 사원별 급여항목 등록
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param allowCode - 급여항목코드
     * @param amount - 금액
     * @param note - 비고
     * @return 등록 결과
     */
    @Override
    public boolean insertEmpAllowance(String empId, String comId, String allowCode, Double amount, String note) {
        if (comId == null || comId.isEmpty()) {
            initAuthInfo();
            comId = this.comId;
        }
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("empId", empId);
        param.put("comId", comId);
        param.put("allowCode", allowCode);
        param.put("amount", amount != null ? amount : 0.0);
        param.put("note", note);
        
        int result = empMapper.insertEmpAllowance(param);
        return result > 0;
    }
    
    /**
     * 사원별 급여항목 수정
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param allowCode - 급여항목코드
     * @param amount - 금액
     * @param note - 비고
     * @return 수정 결과
     */
    @Override
    public boolean updateEmpAllowance(String empId, String comId, String allowCode, Double amount, String note) {
        if (comId == null || comId.isEmpty()) {
            initAuthInfo();
            comId = this.comId;
        }
        java.util.Map<String, Object> param = new java.util.HashMap<>();
        param.put("empId", empId);
        param.put("comId", comId);
        param.put("allowCode", allowCode);
        param.put("amount", amount != null ? amount : 0.0);
        param.put("note", note);
        
        int result = empMapper.updateEmpAllowance(param);
        return result > 0;
    }
    
    /**
     * 사원별 급여항목 삭제
     * @param empId - 사원번호
     * @param comId - 회사ID
     * @param allowCode - 급여항목코드
     * @return 삭제 결과
     */
    @Override
    public boolean deleteEmpAllowance(String empId, String comId, String allowCode) {
        if (comId == null || comId.isEmpty()) {
            initAuthInfo();
            comId = this.comId;
        }
        java.util.Map<String, String> param = new java.util.HashMap<>();
        param.put("empId", empId);
        param.put("comId", comId);
        param.put("allowCode", allowCode);
        
        int result = empMapper.deleteEmpAllowance(param);
        return result > 0;
    }
} 