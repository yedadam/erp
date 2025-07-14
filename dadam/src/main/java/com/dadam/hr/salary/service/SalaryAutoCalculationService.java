package com.dadam.hr.salary.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.hr.salary.mapper.SalaryCalculationMapper;
import com.dadam.hr.salary.service.SalaryStatementVO;
import com.dadam.hr.emp.mapper.EmpMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 급여 자동계산 서비스
 * 기본급 + 수당 - 비과세 - 4대보험 자동 계산
 */
@Slf4j
@Service
@Transactional
public class SalaryAutoCalculationService {

    @Autowired
    private SalaryCalculationMapper salaryCalculationMapper;
    
    @Autowired
    private EmpMapper empMapper;

    /**
     * 급여 자동계산 실행
     * @param empId 사원번호
     * @param calcMonth 계산월(YYYYMM)
     * @param comId 회사ID
     * @return 계산된 급여 정보
     */
    public SalaryStatementVO calculateSalary(String empId, String calcMonth, String comId) {
        log.info("급여 자동계산 시작 - 사원ID: {}, 계산월: {}, 회사ID: {}", empId, calcMonth, comId);
        
        try {
            // 1. 기본급 조회
            BigDecimal baseSalary = getBaseSalary(empId, comId);
            
            // 2. 수당 계산 (A/B/C 타입별)
            BigDecimal allowance = calculateAllowance(empId, calcMonth, comId);
            
            // 3. 공제 계산
            BigDecimal deduction = calculateDeduction(empId, calcMonth, comId);
            
            // 4. 4대보험 계산
            BigDecimal insurance = calculateInsurance(baseSalary, allowance, comId);
            
            // 5. 실수령액 계산
            BigDecimal netSalary = baseSalary.add(allowance).subtract(deduction).subtract(insurance);
            
            // 6. SalaryStatementVO 생성
            SalaryStatementVO result = new SalaryStatementVO();
            result.setEmpId(empId);
            result.setComId(comId);
            result.setCalcMonth(calcMonth);
            result.setBaseSal(baseSalary.toString());
            result.setNetSalary(netSalary.toString());
            result.setStatus("CALC");
            result.setCreatedDate(LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            
            // 7. 수당/공제 합계 설정
            result.setAllowanceTotal(allowance);
            result.setDeductionTotal(deduction.add(insurance));
            
            log.info("급여 자동계산 완료 - 사원ID: {}, 실수령액: {}", empId, netSalary);
            return result;
            
        } catch (Exception e) {
            log.error("급여 자동계산 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("급여 계산 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 기본급 조회
     * @param empId 사원번호
     * @param comId 회사ID
     * @return 기본급
     */
    private BigDecimal getBaseSalary(String empId, String comId) {
        try {
            double baseSalary = salaryCalculationMapper.getBaseSalary(empId, comId);
            return BigDecimal.valueOf(baseSalary);
        } catch (Exception e) {
            log.warn("기본급 조회 실패 - 사원ID: {}, 오류: {}", empId, e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    /**
     * 수당 계산 (A/B/C 타입별)
     * @param empId 사원번호
     * @param calcMonth 계산월
     * @param comId 회사ID
     * @return 수당 합계
     */
    private BigDecimal calculateAllowance(String empId, String calcMonth, String comId) {
        BigDecimal totalAllowance = BigDecimal.ZERO;
        
        try {
            // 1. OT수당 계산
            double otAllow = salaryCalculationMapper.getOvertimePay(empId, calcMonth, comId);
            totalAllowance = totalAllowance.add(BigDecimal.valueOf(otAllow));
            
            // 2. 연차수당 계산
            double annualLeavePay = salaryCalculationMapper.getAnnualLeavePay(empId, calcMonth, comId);
            totalAllowance = totalAllowance.add(BigDecimal.valueOf(annualLeavePay));
            
            // 3. 기타 수당 (sal1~sal10)
            Map<String, Object> params = new HashMap<>();
            params.put("empId", empId);
            params.put("calcMonth", calcMonth);
            params.put("comId", comId);
            
            // sal1~sal10 수당 계산
            for (int i = 1; i <= 10; i++) {
                String salKey = "sal" + i;
                BigDecimal salAmount = calculateSalAmount(params, salKey);
                totalAllowance = totalAllowance.add(salAmount);
            }
            
        } catch (Exception e) {
            log.warn("수당 계산 실패 - 사원ID: {}, 오류: {}", empId, e.getMessage());
        }
        
        return totalAllowance;
    }

    /**
     * 공제 계산
     * @param empId 사원번호
     * @param calcMonth 계산월
     * @param comId 회사ID
     * @return 공제 합계
     */
    private BigDecimal calculateDeduction(String empId, String calcMonth, String comId) {
        BigDecimal totalDeduction = BigDecimal.ZERO;
        
        try {
            // 1. 지각/조퇴 공제
            Map<String, Object> lateEarlyDeduction = salaryCalculationMapper.getLateEarlyDeduction(empId, calcMonth, comId);
            if (lateEarlyDeduction != null) {
                BigDecimal lateDeduction = new BigDecimal(lateEarlyDeduction.get("late_deduction").toString());
                BigDecimal earlyLeaveDeduction = new BigDecimal(lateEarlyDeduction.get("early_leave_deduction").toString());
                totalDeduction = totalDeduction.add(lateDeduction).add(earlyLeaveDeduction);
            }
            
            // 2. 결근 공제
            Map<String, Object> workDays = salaryCalculationMapper.getWorkDays(empId, calcMonth, comId);
            if (workDays != null) {
                int absentDays = Integer.parseInt(workDays.get("absent_days").toString());
                BigDecimal dailyWage = getBaseSalary(empId, comId).divide(BigDecimal.valueOf(22), 2, RoundingMode.HALF_UP);
                BigDecimal absentDeduction = dailyWage.multiply(BigDecimal.valueOf(absentDays));
                totalDeduction = totalDeduction.add(absentDeduction);
            }
            
        } catch (Exception e) {
            log.warn("공제 계산 실패 - 사원ID: {}, 오류: {}", empId, e.getMessage());
        }
        
        return totalDeduction;
    }

    /**
     * 4대보험 계산
     * @param baseSalary 기본급
     * @param allowance 수당
     * @param comId 회사ID
     * @return 4대보험 합계
     */
    private BigDecimal calculateInsurance(BigDecimal baseSalary, BigDecimal allowance, String comId) {
        BigDecimal totalInsurance = BigDecimal.ZERO;
        
        try {
            // 과세표준 (기본급 + 수당)
            BigDecimal taxableAmount = baseSalary.add(allowance);
            
            // 국민연금 (9%)
            BigDecimal nationalPension = taxableAmount.multiply(new BigDecimal("0.09"));
            
            // 건강보험 (3.545%)
            BigDecimal healthInsurance = taxableAmount.multiply(new BigDecimal("0.03545"));
            
            // 장기요양보험 (건강보험의 12.27%)
            BigDecimal longTermCare = healthInsurance.multiply(new BigDecimal("0.1227"));
            
            // 고용보험 (1.05%)
            BigDecimal employmentInsurance = taxableAmount.multiply(new BigDecimal("0.0105"));
            
            totalInsurance = nationalPension.add(healthInsurance).add(longTermCare).add(employmentInsurance);
            
        } catch (Exception e) {
            log.warn("4대보험 계산 실패 - 회사ID: {}, 오류: {}", comId, e.getMessage());
        }
        
        return totalInsurance;
    }

    /**
     * 개별 수당 계산 (sal1~sal10)
     * @param params 계산 파라미터
     * @param salKey 수당 키
     * @return 수당 금액
     */
    private BigDecimal calculateSalAmount(Map<String, Object> params, String salKey) {
        try {
            // 실제 구현에서는 DB에서 사원별 수당 설정을 조회
            // 현재는 기본값으로 처리
            return BigDecimal.ZERO;
        } catch (Exception e) {
            log.warn("수당 계산 실패 - 키: {}, 오류: {}", salKey, e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    /**
     * 급여 계산 결과 저장
     * @param salaryStatement 계산된 급여 정보
     * @return 저장된 행 수
     */
    public int saveSalaryCalculation(SalaryStatementVO salaryStatement) {
        try {
            // 급여명세서 저장
            return salaryCalculationMapper.insertSalaryStatement(salaryStatement);
        } catch (Exception e) {
            log.error("급여 계산 결과 저장 실패: {}", e.getMessage(), e);
            throw new RuntimeException("급여 저장 중 오류가 발생했습니다.", e);
        }
    }
} 