package com.dadam.hr.salary.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.hr.salary.mapper.SalaryMapper;
import com.dadam.hr.attendance.mapper.AttendanceManageMapper;
import com.dadam.hr.emp.mapper.EmpMapper;
import com.dadam.hr.salary.service.SalaryCalculationVO;
import com.dadam.hr.salary.service.SalaryDetailVO;
import com.dadam.hr.attendance.service.AttendanceStatisticsVO;
import com.dadam.hr.attendance.mapper.AttendanceStatsMapper;
import com.dadam.hr.salary.service.SalaryStatementVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 급여 자동계산 서비스
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2024-01-01
 */
@Slf4j
@Service
@Transactional
public class SalaryCalculationService {

    @Autowired
    private SalaryMapper salaryMapper;
    
    @Autowired
    private AttendanceStatsMapper attendanceMapper;
    
    @Autowired
    private EmpMapper employeeMapper;

    /**
     * 월별 급여 자동계산 실행
     * 
     * @param companyId 회사 ID
     * @param yearMonth 계산 대상 년월 (YYYY-MM)
     * @return 계산된 급여 건수
     */
    public int calculateMonthlySalary(Long companyId, String yearMonth) {
        log.info("급여 자동계산 시작 - 회사ID: {}, 년월: {}", companyId, yearMonth);
        
        try {
            // 1. 해당 회사의 모든 사원 조회
            List<Map<String, Object>> employees = salaryMapper.getEmployeesByCompany(companyId);
            int calculatedCount = 0;
            
            // 2. 각 사원별로 급여 계산
            for (Map<String, Object> employee : employees) {
                String empId = (String) employee.get("emp_id");
                
                // 3. 개별 사원 급여 계산
                SalaryCalculationVO calculation = calculateEmployeeSalary(companyId, Long.valueOf(empId), yearMonth);
                
                if (calculation != null) {
                    // 4. 계산 결과 저장
                    saveSalaryCalculation(calculation);
                    calculatedCount++;
                    log.debug("사원 {} 급여 계산 완료", empId);
                }
            }
            
            log.info("급여 자동계산 완료 - 총 {}건 계산됨", calculatedCount);
            return calculatedCount;
            
        } catch (Exception e) {
            log.error("급여 자동계산 중 오류 발생: {}", e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 개별 사원 급여 계산
     * 
     * @param companyId 회사 ID
     * @param employeeId 사원 ID
     * @param yearMonth 계산 대상 년월
     * @return 급여 계산 결과
     */
    public SalaryCalculationVO calculateEmployeeSalary(Long companyId, Long employeeId, String yearMonth) {
        log.debug("사원 급여 계산 시작 - 사원ID: {}, 년월: {}", employeeId, yearMonth);
        
        try {
            // 1. 사원 기본 정보 조회
            Map<String, Object> employeeParams = new HashMap<>();
            employeeParams.put("employeeId", employeeId);
            employeeParams.put("companyId", companyId);
            Map<String, Object> employeeInfo = salaryMapper.getEmployeeInfo(employeeParams);
            if (employeeInfo == null) {
                log.warn("사원 정보를 찾을 수 없습니다: {}", employeeId);
                return null;
            }
            
            // 2. 근태 통계 조회
            Map<String, Object> attendanceParams = new HashMap<>();
            attendanceParams.put("empId", employeeId.toString());
            attendanceParams.put("yearMonth", yearMonth);
            attendanceParams.put("comId", companyId);
            AttendanceStatisticsVO attendanceStats = attendanceMapper.getAttendanceStatistics(attendanceParams);
            if (attendanceStats == null) {
                log.warn("근태 통계를 찾을 수 없습니다: 사원ID={}, 년월={}", employeeId, yearMonth);
                return null;
            }
            
            // 3. 급여 항목 설정 조회
            Map<String, Object> salaryItems = salaryMapper.getSalaryItems(companyId);
            
            // 4. 급여 계산 실행
            SalaryCalculationVO calculation = performSalaryCalculation(employeeInfo, attendanceStats, salaryItems, yearMonth);
            
            log.debug("사원 {} 급여 계산 완료 - 총액: {}", employeeId, calculation.getTotalSalary());
            return calculation;
            
        } catch (Exception e) {
            log.error("사원 급여 계산 중 오류 발생: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 급여 계산 로직 실행
     * 
     * @param employeeInfo 사원 기본 정보
     * @param attendanceStats 근태 통계
     * @param salaryItems 급여 항목 설정
     * @param yearMonth 계산 년월
     * @return 급여 계산 결과
     */
    private SalaryCalculationVO performSalaryCalculation(
            Map<String, Object> employeeInfo,
            AttendanceStatisticsVO attendanceStats,
            Map<String, Object> salaryItems,
            String yearMonth) {
        
        SalaryCalculationVO calculation = new SalaryCalculationVO();
        
        // 기본 정보 설정
        calculation.setCompanyId((Long) employeeInfo.get("companyId"));
        calculation.setEmployeeId((Long) employeeInfo.get("employeeId"));
        calculation.setYearMonth(yearMonth);
        calculation.setEmployeeName((String) employeeInfo.get("employeeName"));
        
        // 1. 기본급 계산
        BigDecimal baseSalary = calculateBaseSalary(employeeInfo, attendanceStats);
        calculation.setBaseSalary(baseSalary);
        
        // 2. 수당 계산
        Map<String, BigDecimal> allowances = calculateAllowances(employeeInfo, attendanceStats, salaryItems);
        calculation.setAllowances(allowances);
        
        // 3. 공제 계산
        Map<String, BigDecimal> deductions = calculateDeductions(employeeInfo, attendanceStats, salaryItems);
        calculation.setDeductions(deductions);
        
        // 4. 총 급여 계산
        BigDecimal totalSalary = calculateTotalSalary(baseSalary, allowances, deductions);
        calculation.setTotalSalary(totalSalary);
        
        // 5. 실수령액 계산
        BigDecimal netSalary = calculateNetSalary(totalSalary, deductions);
        calculation.setNetSalary(netSalary);
        
        return calculation;
    }

    /**
     * 기본급 계산
     * 
     * @param employeeInfo 사원 정보
     * @param attendanceStats 근태 통계
     * @return 기본급
     */
    private BigDecimal calculateBaseSalary(Map<String, Object> employeeInfo, AttendanceStatisticsVO attendanceStats) {
        String empType = (String) employeeInfo.get("empType");
        if ("일용직".equals(empType)) {
            // 일용직: 근무일수 * 일급 또는 근무시간 * 시급
            BigDecimal dailyWage = employeeInfo.get("dailyWage") != null ? new BigDecimal(employeeInfo.get("dailyWage").toString()) : BigDecimal.ZERO;
            BigDecimal hourlyWage = employeeInfo.get("hourlyWage") != null ? new BigDecimal(employeeInfo.get("hourlyWage").toString()) : BigDecimal.ZERO;
            int totalWorkDays = attendanceStats.getTotalWorkDays() != null ? attendanceStats.getTotalWorkDays() : 0;
            int totalWorkHours = attendanceStats.getTotalWorkHours() != null ? attendanceStats.getTotalWorkHours().intValue() : 0;
            // 일급 우선, 없으면 시급
            if (dailyWage.compareTo(BigDecimal.ZERO) > 0) {
                return dailyWage.multiply(BigDecimal.valueOf(totalWorkDays)).setScale(0, RoundingMode.HALF_UP);
            } else if (hourlyWage.compareTo(BigDecimal.ZERO) > 0) {
                return hourlyWage.multiply(BigDecimal.valueOf(totalWorkHours)).setScale(0, RoundingMode.HALF_UP);
            } else {
                return BigDecimal.ZERO;
            }
        } else {
            // 기존 월급제 계산
            BigDecimal baseSalary = new BigDecimal(employeeInfo.get("baseSalary").toString());
            int totalWorkDays = attendanceStats.getTotalWorkDays() != null ? attendanceStats.getTotalWorkDays() : 0;
            int standardWorkDays = getStandardWorkDays(attendanceStats.getYearMonth());
            if (totalWorkDays < standardWorkDays) {
                BigDecimal ratio = BigDecimal.valueOf(totalWorkDays)
                    .divide(BigDecimal.valueOf(standardWorkDays), 4, RoundingMode.HALF_UP);
                baseSalary = baseSalary.multiply(ratio);
            }
            return baseSalary.setScale(0, RoundingMode.HALF_UP);
        }
    }

    /**
     * 수당 계산
     * 
     * @param employeeInfo 사원 정보
     * @param attendanceStats 근태 통계
     * @param salaryItems 급여 항목 설정
     * @return 수당 항목별 금액
     */
    private Map<String, BigDecimal> calculateAllowances(
            Map<String, Object> employeeInfo,
            AttendanceStatisticsVO attendanceStats,
            Map<String, Object> salaryItems) {
        
        Map<String, BigDecimal> allowances = new HashMap<>();
        
        // 1. 연장근무수당 계산
        BigDecimal overtimeAllowance = calculateOvertimeAllowance(employeeInfo, attendanceStats);
        allowances.put("overtime", overtimeAllowance);
        
        // 2. 야간수당 계산
        BigDecimal nightAllowance = calculateNightAllowance(employeeInfo, attendanceStats);
        allowances.put("night", nightAllowance);
        
        // 3. 휴일수당 계산
        BigDecimal holidayAllowance = calculateHolidayAllowance(employeeInfo, attendanceStats);
        allowances.put("holiday", holidayAllowance);
        
        // 4. 식대수당
        BigDecimal mealAllowance = calculateMealAllowance(employeeInfo, attendanceStats);
        allowances.put("meal", mealAllowance);
        
        // 5. 교통수당
        BigDecimal transportAllowance = calculateTransportAllowance(employeeInfo);
        allowances.put("transport", transportAllowance);
        
        // 6. 기타수당 (동적 항목)
        Map<String, BigDecimal> otherAllowances = calculateOtherAllowances(employeeInfo, salaryItems);
        allowances.putAll(otherAllowances);
        
        return allowances;
    }
    
    /**
     * 연장근무수당 계산
     * 
     * @param employeeInfo 사원 정보
     * @param attendanceStats 근태 통계
     * @return 연장근무수당
     */
    private BigDecimal calculateOvertimeAllowance(Map<String, Object> employeeInfo, AttendanceStatisticsVO attendanceStats) {
        BigDecimal hourlyWage = new BigDecimal(employeeInfo.get("hourlyWage").toString());
        int overtimeHours = attendanceStats.getOvertimeHours() != null ? attendanceStats.getOvertimeHours().intValue() : 0;
        
        // 연장근무수당 = 시간당 임금 × 연장근무시간 × 1.5
        return hourlyWage
            .multiply(BigDecimal.valueOf(overtimeHours))
            .multiply(new BigDecimal("1.5"))
            .setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 야간수당 계산
     * 
     * @param employeeInfo 사원 정보
     * @param attendanceStats 근태 통계
     * @return 야간수당
     */
    private BigDecimal calculateNightAllowance(Map<String, Object> employeeInfo, AttendanceStatisticsVO attendanceStats) {
        BigDecimal hourlyWage = new BigDecimal(employeeInfo.get("hourlyWage").toString());
        int nightHours = attendanceStats.getNightHours() != null ? attendanceStats.getNightHours().intValue() : 0;
        
        // 야간수당 = 시간당 임금 × 야간근무시간 × 0.5
        return hourlyWage
            .multiply(BigDecimal.valueOf(nightHours))
            .multiply(new BigDecimal("0.5"))
            .setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 휴일수당 계산
     * 
     * @param employeeInfo 사원 정보
     * @param attendanceStats 근태 통계
     * @return 휴일수당
     */
    private BigDecimal calculateHolidayAllowance(Map<String, Object> employeeInfo, AttendanceStatisticsVO attendanceStats) {
        BigDecimal hourlyWage = new BigDecimal(employeeInfo.get("hourlyWage").toString());
        int holidayHours = attendanceStats.getHolidayHours() != null ? attendanceStats.getHolidayHours().intValue() : 0;
        
        // 휴일수당 = 시간당 임금 × 휴일근무시간 × 1.5
        return hourlyWage
            .multiply(BigDecimal.valueOf(holidayHours))
            .multiply(new BigDecimal("1.5"))
            .setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 식대수당 계산
     * 
     * @param employeeInfo 사원 정보
     * @param attendanceStats 근태 통계
     * @return 식대수당
     */
    private BigDecimal calculateMealAllowance(Map<String, Object> employeeInfo, AttendanceStatisticsVO attendanceStats) {
        BigDecimal dailyMealAllowance = new BigDecimal("8000"); // 일일 식대수당
        int workDays = attendanceStats.getTotalWorkDays();
        
        return dailyMealAllowance
            .multiply(BigDecimal.valueOf(workDays))
            .setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 교통수당 계산
     * 
     * @param employeeInfo 사원 정보
     * @return 교통수당
     */
    private BigDecimal calculateTransportAllowance(Map<String, Object> employeeInfo) {
        // 월 고정 교통수당
        return new BigDecimal("50000");
    }

    /**
     * 기타수당 계산 (동적 항목)
     * 
     * @param employeeInfo 사원 정보
     * @param salaryItems 급여 항목 설정
     * @return 기타수당 항목별 금액
     */
    private Map<String, BigDecimal> calculateOtherAllowances(Map<String, Object> employeeInfo, Map<String, Object> salaryItems) {
        Map<String, BigDecimal> otherAllowances = new HashMap<>();
        
        // sal1~sal10 동적 수당 항목 처리
        for (int i = 1; i <= 10; i++) {
            String itemKey = "sal" + i;
            Object itemValue = salaryItems.get(itemKey);
            
            if (itemValue != null && !itemValue.toString().isEmpty()) {
                try {
                    BigDecimal amount = new BigDecimal(itemValue.toString());
                    otherAllowances.put(itemKey, amount);
                } catch (NumberFormatException e) {
                    log.warn("급여 항목 {} 값이 숫자가 아닙니다: {}", itemKey, itemValue);
                }
            }
        }
        
        return otherAllowances;
    }

    /**
     * 공제 계산
     * 
     * @param employeeInfo 사원 정보
     * @param attendanceStats 근태 통계
     * @param salaryItems 급여 항목 설정
     * @return 공제 항목별 금액
     */
    private Map<String, BigDecimal> calculateDeductions(
            Map<String, Object> employeeInfo,
            AttendanceStatisticsVO attendanceStats,
            Map<String, Object> salaryItems) {
        
        Map<String, BigDecimal> deductions = new HashMap<>();
        
        // 1. 국민연금 (9%)
        BigDecimal nationalPension = calculateNationalPension(employeeInfo);
        deductions.put("nationalPension", nationalPension);
        
        // 2. 건강보험 (3.545%)
        BigDecimal healthInsurance = calculateHealthInsurance(employeeInfo);
        deductions.put("healthInsurance", healthInsurance);
        
        // 3. 고용보험 (0.8%)
        BigDecimal employmentInsurance = calculateEmploymentInsurance(employeeInfo);
        deductions.put("employmentInsurance", employmentInsurance);
        
        // 4. 소득세
        BigDecimal incomeTax = calculateIncomeTax(employeeInfo);
        deductions.put("incomeTax", incomeTax);
        
        // 5. 지방소득세 (소득세의 10%)
        BigDecimal localIncomeTax = incomeTax.multiply(new BigDecimal("0.1"));
        deductions.put("localIncomeTax", localIncomeTax);
        
        return deductions;
    }

    /**
     * 국민연금 계산
     * 
     * @param employeeInfo 사원 정보
     * @return 국민연금
     */
    private BigDecimal calculateNationalPension(Map<String, Object> employeeInfo) {
        BigDecimal baseSalary = new BigDecimal(employeeInfo.get("baseSalary").toString());
        
        // 국민연금 = 기본급 × 9%
        return baseSalary
            .multiply(new BigDecimal("0.09"))
            .setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 건강보험 계산
     * 
     * @param employeeInfo 사원 정보
     * @return 건강보험
     */
    private BigDecimal calculateHealthInsurance(Map<String, Object> employeeInfo) {
        BigDecimal baseSalary = new BigDecimal(employeeInfo.get("baseSalary").toString());
        
        // 건강보험 = 기본급 × 3.545%
        return baseSalary
            .multiply(new BigDecimal("0.03545"))
            .setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 고용보험 계산
     * 
     * @param employeeInfo 사원 정보
     * @return 고용보험
     */
    private BigDecimal calculateEmploymentInsurance(Map<String, Object> employeeInfo) {
        BigDecimal baseSalary = new BigDecimal(employeeInfo.get("baseSalary").toString());
        
        // 고용보험 = 기본급 × 0.8%
        return baseSalary
            .multiply(new BigDecimal("0.008"))
            .setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 소득세 계산 (간이세율 적용)
     * 
     * @param employeeInfo 사원 정보
     * @return 소득세
     */
    private BigDecimal calculateIncomeTax(Map<String, Object> employeeInfo) {
        BigDecimal baseSalary = new BigDecimal(employeeInfo.get("baseSalary").toString());
        
        // 간이세율표 적용 (연간 기준)
        BigDecimal annualSalary = baseSalary.multiply(new BigDecimal("12"));
        
        BigDecimal taxRate;
        BigDecimal deduction;
        
        if (annualSalary.compareTo(new BigDecimal("12000000")) <= 0) {
            taxRate = new BigDecimal("0.06");
            deduction = new BigDecimal("0");
        } else if (annualSalary.compareTo(new BigDecimal("46000000")) <= 0) {
            taxRate = new BigDecimal("0.15");
            deduction = new BigDecimal("1080000");
        } else if (annualSalary.compareTo(new BigDecimal("88000000")) <= 0) {
            taxRate = new BigDecimal("0.24");
            deduction = new BigDecimal("5220000");
        } else if (annualSalary.compareTo(new BigDecimal("150000000")) <= 0) {
            taxRate = new BigDecimal("0.35");
            deduction = new BigDecimal("14900000");
        } else if (annualSalary.compareTo(new BigDecimal("300000000")) <= 0) {
            taxRate = new BigDecimal("0.38");
            deduction = new BigDecimal("19400000");
        } else if (annualSalary.compareTo(new BigDecimal("500000000")) <= 0) {
            taxRate = new BigDecimal("0.40");
            deduction = new BigDecimal("25400000");
        } else {
            taxRate = new BigDecimal("0.42");
            deduction = new BigDecimal("35400000");
        }
        
        // 월 소득세 = (연간 소득세 ÷ 12)
        BigDecimal annualTax = annualSalary.multiply(taxRate).subtract(deduction);
        if (annualTax.compareTo(BigDecimal.ZERO) < 0) {
            annualTax = BigDecimal.ZERO;
        }
        
        return annualTax
            .divide(new BigDecimal("12"), 0, RoundingMode.HALF_UP);
    }

    /**
     * 총 급여 계산
     * 
     * @param baseSalary 기본급
     * @param allowances 수당 항목들
     * @param deductions 공제 항목들
     * @return 총 급여
     */
    private BigDecimal calculateTotalSalary(BigDecimal baseSalary, Map<String, BigDecimal> allowances, Map<String, BigDecimal> deductions) {
        BigDecimal total = baseSalary;
        
        // 수당 합계
        for (BigDecimal allowance : allowances.values()) {
            total = total.add(allowance);
        }
        
        return total.setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 실수령액 계산
     * 
     * @param totalSalary 총 급여
     * @param deductions 공제 항목들
     * @return 실수령액
     */
    private BigDecimal calculateNetSalary(BigDecimal totalSalary, Map<String, BigDecimal> deductions) {
        BigDecimal totalDeduction = BigDecimal.ZERO;
        
        // 공제 합계
        for (BigDecimal deduction : deductions.values()) {
            totalDeduction = totalDeduction.add(deduction);
        }
        
        return totalSalary.subtract(totalDeduction).setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 표준 근무일수 조회
     * 
     * @param yearMonth 년월 (YYYY-MM)
     * @return 표준 근무일수
     */
    private int getStandardWorkDays(String yearMonth) {
        YearMonth ym = YearMonth.parse(yearMonth);
        LocalDate startOfMonth = ym.atDay(1);
        LocalDate endOfMonth = ym.atEndOfMonth();
        
        int workDays = 0;
        LocalDate current = startOfMonth;
        
        while (!current.isAfter(endOfMonth)) {
            // 주말 제외 (토요일=6, 일요일=7)
            if (current.getDayOfWeek().getValue() < 6) {
                workDays++;
            }
            current = current.plusDays(1);
        }
        
        return workDays;
    }

    /**
     * 급여 계산 결과 저장
     * 
     * @param calculation 급여 계산 결과
     */
    private void saveSalaryCalculation(SalaryCalculationVO calculation) {
        try {
            // 기존 급여 데이터가 있으면 삭제
            Map<String, Object> params = new HashMap<>();
            params.put("employeeId", calculation.getEmployeeId());
            params.put("yearMonth", calculation.getYearMonth());
            salaryMapper.deleteSalaryByEmployeeAndMonth(params);
            
            // 급여 마스터 저장
            salaryMapper.insertSalaryMaster(calculation);
            
            // 급여 상세 저장
            for (Map.Entry<String, BigDecimal> allowance : calculation.getAllowances().entrySet()) {
                SalaryDetailVO detail = new SalaryDetailVO();
                detail.setSalaryId(calculation.getSalaryId());
                detail.setItemType("ALLOWANCE");
                detail.setItemName(allowance.getKey());
                detail.setAmount(allowance.getValue());
                salaryMapper.insertSalaryDetail(detail);
            }
            
            for (Map.Entry<String, BigDecimal> deduction : calculation.getDeductions().entrySet()) {
                SalaryDetailVO detail = new SalaryDetailVO();
                detail.setSalaryId(calculation.getSalaryId());
                detail.setItemType("DEDUCTION");
                detail.setItemName(deduction.getKey());
                detail.setAmount(deduction.getValue());
                salaryMapper.insertSalaryDetail(detail);
            }
            
            log.info("급여 계산 결과 저장 완료 - 급여ID: {}", calculation.getSalaryId());
            
        } catch (Exception e) {
            log.error("급여 계산 결과 저장 중 오류 발생", e);
            throw new RuntimeException("급여 계산 결과 저장 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 급여 단건 승인
     */
    public int approveSalary(Long salaryId) {
        Map<String, Object> params = new HashMap<>();
        params.put("salaryId", salaryId);
        params.put("status", "APPROVED");
        return salaryMapper.updateSalaryStatus(params);
    }

    /**
     * 급여 단건 지급
     */
    public int paySalary(Long salaryId) {
        Map<String, Object> params = new HashMap<>();
        params.put("salaryId", salaryId);
        params.put("status", "PAID");
        return salaryMapper.updateSalaryStatus(params);
    }

    /**
     * 월별 일괄 승인
     */
    public int approveBatchSalaries(Long companyId, String yearMonth) {
        Map<String, Object> params = new HashMap<>();
        params.put("companyId", companyId);
        params.put("yearMonth", yearMonth);
        params.put("status", "APPROVED");
        return salaryMapper.approveBatchSalaries(params);
    }

    /**
     * 월별 일괄 지급
     */
    public int payBatchSalaries(Long companyId, String yearMonth) {
        Map<String, Object> params = new HashMap<>();
        params.put("companyId", companyId);
        params.put("yearMonth", yearMonth);
        params.put("status", "PAID");
        return salaryMapper.approveBatchSalaries(params);
    }

    /**
     * 급여 이력(계산/승인/지급) 조회
     * @param companyId 회사ID
     * @param yearMonth 년월(선택)
     * @param employeeId 사원ID(선택)
     * @param status 상태(선택: CALCULATED, APPROVED, PAID)
     * @return 급여 이력 목록
     */
    public List<Map<String, Object>> getSalaryHistory(Long companyId, String yearMonth, Long employeeId, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("companyId", companyId);
        if (yearMonth != null) params.put("yearMonth", yearMonth);
        if (employeeId != null) params.put("employeeId", employeeId);
        if (status != null) params.put("status", status);
        return salaryMapper.selectSalaryCalculationHistory(params);
    }

    /**
     * 급여명세서 엑셀 다운로드용 데이터 조회
     * @param companyId 회사ID
     * @param yearMonth 년월(선택)
     * @param status 상태(선택)
     * @return 엑셀 다운로드용 데이터 목록
     */
    public List<Map<String, Object>> getSalaryExportData(Long companyId, String yearMonth, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("companyId", companyId);
        if (yearMonth != null) params.put("yearMonth", yearMonth);
        if (status != null) params.put("status", status);
        return salaryMapper.selectSalaryExportData(params);
    }

    /**
     * 급여 명세서 조회
     * 
     * @param empId 사원 ID
     * @param payMonth 지급 년월
     * @return 급여 명세서
     */
    public SalaryStatementVO getSalaryStatement(String empId, String payMonth) {
        log.debug("급여 명세서 조회 - 사원ID: {}, 지급년월: {}", empId, payMonth);
        
        try {
            // 1. 급여 마스터 정보 조회
            Map<String, Object> params = new HashMap<>();
            params.put("empId", empId);
            params.put("payMonth", payMonth);
            Map<String, Object> salaryMaster = salaryMapper.selectSalaryDetailByEmployeeAndMonth(params);
            
            if (salaryMaster == null) {
                log.warn("급여 명세서를 찾을 수 없습니다: 사원ID={}, 년월={}", empId, payMonth);
                return null;
            }
            
            // 2. 급여 상세 항목 조회
            Long salaryId = (Long) salaryMaster.get("salary_id");
            List<Map<String, Object>> salaryDetails = salaryMapper.selectSalaryDetailItems(salaryId);
            
            // 3. SalaryStatementVO 객체 생성
            SalaryStatementVO statement = new SalaryStatementVO();
            statement.setId(salaryId);
            statement.setEmpId(empId);
            statement.setPayMonth(payMonth);
            statement.setBaseSalary((BigDecimal) salaryMaster.get("base_salary"));
            statement.setNetPay((BigDecimal) salaryMaster.get("net_salary"));
            statement.setCreatedDate(LocalDateTime.now());
            statement.setUpdatedDate(LocalDateTime.now());
            
            // 4. 수당/공제 항목 설정 (별도 필드로 저장)
            BigDecimal allowanceTotal = BigDecimal.ZERO;
            BigDecimal deductionTotal = BigDecimal.ZERO;
            
            for (Map<String, Object> detail : salaryDetails) {
                String itemType = (String) detail.get("item_type");
                String itemName = (String) detail.get("item_name");
                BigDecimal amount = (BigDecimal) detail.get("amount");
                
                if ("ALLOWANCE".equals(itemType)) {
                    allowanceTotal = allowanceTotal.add(amount);
                    // 특정 수당 항목 설정
                    if ("overtime".equals(itemName)) {
                        statement.setOvertimePay(amount);
                    } else if ("annualLeave".equals(itemName)) {
                        statement.setAnnualLeavePay(amount);
                    }
                } else if ("DEDUCTION".equals(itemType)) {
                    deductionTotal = deductionTotal.add(amount);
                    // 특정 공제 항목 설정
                    if ("late".equals(itemName)) {
                        statement.setLateDeduction(amount);
                    } else if ("earlyLeave".equals(itemName)) {
                        statement.setEarlyLeaveDeduction(amount);
                    } else if ("absent".equals(itemName)) {
                        statement.setAbsentDeduction(amount);
                    }
                }
            }
            
            statement.setAllowanceTotal(allowanceTotal);
            statement.setDeductionTotal(deductionTotal);
            
            log.debug("급여 명세서 조회 완료 - 급여ID: {}", salaryId);
            return statement;
            
        } catch (Exception e) {
            log.error("급여 명세서 조회 중 오류 발생: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 급여 지급 처리
     * 
     * @param statementId 급여명세서 ID
     * @return 처리 결과
     */
    public String processSalaryPayment(Long statementId) {
        log.debug("급여 지급 처리 - 명세서ID: {}", statementId);
        
        // TODO: 실제 구현 필요
        log.warn("급여 지급 처리 기능이 아직 구현되지 않았습니다.");
        return "not_implemented";
    }

    /**
     * 급여 지급 승인/반려
     * 
     * @param paymentId 지급 ID
     * @param status 상태 (APPROVED/REJECTED)
     * @param rejectReason 반려사유
     * @return 처리 결과
     */
    public String approveSalaryPayment(Long paymentId, String status, String rejectReason) {
        log.debug("급여 지급 승인/반려 - 지급ID: {}, 상태: {}", paymentId, status);
        
        // TODO: 실제 구현 필요
        log.warn("급여 지급 승인/반려 기능이 아직 구현되지 않았습니다.");
        return "not_implemented";
    }

    /**
     * 급여 통계 조회
     * 
     * @param deptCode 부서코드
     * @param payMonth 지급년월
     * @return 통계 정보
     */
    public Map<String, Object> getSalaryStatistics(String deptCode, String payMonth) {
        log.debug("급여 통계 조회 - 부서코드: {}, 지급년월: {}", deptCode, payMonth);
        
        // TODO: 실제 구현 필요
        log.warn("급여 통계 조회 기능이 아직 구현되지 않았습니다.");
        return new HashMap<>();
    }

    /**
     * 급여 지급 이력 조회
     * 
     * @param empId 사원번호
     * @param fromMonth 시작년월
     * @param toMonth 종료년월
     * @return 지급 이력
     */
    public List<Map<String, Object>> getSalaryPaymentHistory(String empId, String fromMonth, String toMonth) {
        log.debug("급여 지급 이력 조회 - 사원ID: {}, 기간: {} ~ {}", empId, fromMonth, toMonth);
        
        // TODO: 실제 구현 필요
        log.warn("급여 지급 이력 조회 기능이 아직 구현되지 않았습니다.");
        return new ArrayList<>();
    }

    /**
     * 기본급 조회
     * 
     * @param empId 사원번호
     * @return 기본급
     */
    public double getBaseSalary(String empId) {
        log.debug("기본급 조회 - 사원ID: {}", empId);
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("empId", empId);
            params.put("comId", "com-101"); // 기본 회사ID
            
            return salaryMapper.getBaseSalary(params);
            
        } catch (Exception e) {
            log.error("기본급 조회 중 오류 발생: {}", e.getMessage(), e);
            return 0.0;
        }
    }

    /**
     * 급여 목록 조회 (관리자용)
     * 
     * @param comId 회사ID
     * @param yearMonth 지급년월
     * @param status 상태
     * @param employeeName 사원명
     * @return 급여 목록
     */
    public List<Map<String, Object>> getSalaryList(String comId, String yearMonth, String status, String employeeName) {
        log.debug("급여 목록 조회 - 회사ID: {}, 년월: {}, 상태: {}, 사원명: {}", comId, yearMonth, status, employeeName);
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("companyId", comId);
            if (yearMonth != null && !yearMonth.isEmpty()) {
                params.put("yearMonth", yearMonth);
            }
            if (status != null && !status.isEmpty()) {
                params.put("status", status);
            }
            if (employeeName != null && !employeeName.isEmpty()) {
                params.put("employeeName", employeeName);
            }
            
            return salaryMapper.selectSalaryList(params);
            
        } catch (Exception e) {
            log.error("급여 목록 조회 중 오류 발생: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * 급여 엑셀 다운로드
     * 
     * @param comId 회사ID
     * @param yearMonth 지급년월
     * @param status 상태
     * @return 엑셀 파일 데이터
     */
    public byte[] exportSalaryExcel(String comId, String yearMonth, String status) {
        log.debug("급여 엑셀 다운로드 - 회사ID: {}, 년월: {}, 상태: {}", comId, yearMonth, status);
        
        try {
            // 급여 데이터 조회
            List<Map<String, Object>> salaryData = getSalaryExportData(Long.valueOf(comId), yearMonth, status);
            
            // 엑셀 파일 생성
            return createSalaryExcelFile(salaryData, yearMonth);
            
        } catch (Exception e) {
            log.error("급여 엑셀 다운로드 중 오류 발생: {}", e.getMessage(), e);
            return new byte[0];
        }
    }

    /**
     * 급여 엑셀 파일 생성
     * 
     * @param salaryData 급여 데이터
     * @param yearMonth 년월
     * @return 엑셀 파일 바이트 배열
     */
    private byte[] createSalaryExcelFile(List<Map<String, Object>> salaryData, String yearMonth) {
        try {
            // 간단한 CSV 형태로 엑셀 생성 (실제로는 Apache POI 사용 권장)
            StringBuilder csv = new StringBuilder();
            
            // 헤더 추가
            csv.append("사원번호,사원명,부서,직급,기본급,수당,공제,실수령액,상태\n");
            
            // 데이터 추가
            for (Map<String, Object> salary : salaryData) {
                csv.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                    salary.get("employee_id"),
                    salary.get("employee_name"),
                    salary.get("dept_name"),
                    salary.get("position"),
                    salary.get("base_salary"),
                    salary.get("total_allowance"),
                    salary.get("total_deduction"),
                    salary.get("net_salary"),
                    salary.get("status")
                ));
            }
            
            return csv.toString().getBytes("UTF-8");
            
        } catch (Exception e) {
            log.error("엑셀 파일 생성 중 오류 발생: {}", e.getMessage(), e);
            return new byte[0];
        }
    }

    /**
     * 근무일수 조회
     * 
     * @param empId 사원번호
     * @param payMonth 지급년월
     * @return 근무일수 정보
     */
    public Map<String, Object> getWorkDays(String empId, String payMonth) {
        log.debug("근무일수 조회 - 사원ID: {}, 지급년월: {}", empId, payMonth);
        
        // TODO: 실제 구현 필요
        log.warn("근무일수 조회 기능이 아직 구현되지 않았습니다.");
        return new HashMap<>();
    }

    /**
     * 지각/조퇴 공제 조회
     * 
     * @param empId 사원번호
     * @param payMonth 지급년월
     * @return 지각/조퇴 공제 정보
     */
    public Map<String, Object> getLateEarlyDeduction(String empId, String payMonth) {
        log.debug("지각/조퇴 공제 조회 - 사원ID: {}, 지급년월: {}", empId, payMonth);
        
        // TODO: 실제 구현 필요
        log.warn("지각/조퇴 공제 조회 기능이 아직 구현되지 않았습니다.");
        return new HashMap<>();
    }

    /**
     * 연차수당 조회
     * 
     * @param empId 사원번호
     * @param payMonth 지급년월
     * @return 연차수당
     */
    public double getAnnualLeavePay(String empId, String payMonth) {
        log.debug("연차수당 조회 - 사원ID: {}, 지급년월: {}", empId, payMonth);
        
        // TODO: 실제 구현 필요
        log.warn("연차수당 조회 기능이 아직 구현되지 않았습니다.");
        return 0.0;
    }

    /**
     * 연장수당 조회
     * 
     * @param empId 사원번호
     * @param payMonth 지급년월
     * @return 연장수당
     */
    public double getOvertimePay(String empId, String payMonth) {
        log.debug("연장수당 조회 - 사원ID: {}, 지급년월: {}", empId, payMonth);
        
        // TODO: 실제 구현 필요
        log.warn("연장수당 조회 기능이 아직 구현되지 않았습니다.");
        return 0.0;
    }

    /**
     * 지각/조퇴 공제 계산
     * 
     * @param empId 사원번호
     * @param payMonth 지급년월
     * @param comId 회사ID
     * @return 공제 정보
     */
    public Map<String, Object> calculateLateEarlyDeduction(String empId, String payMonth, String comId) {
        log.debug("지각/조퇴 공제 계산 - 사원ID: {}, 지급년월: {}, 회사ID: {}", empId, payMonth, comId);
        
        // TODO: 실제 구현 필요
        log.warn("지각/조퇴 공제 계산 기능이 아직 구현되지 않았습니다.");
        return new HashMap<>();
    }

    /**
     * 연차수당 계산
     * 
     * @param empId 사원번호
     * @param payMonth 지급년월
     * @param comId 회사ID
     * @return 연차수당
     */
    public double calculateAnnualLeavePay(String empId, String payMonth, String comId) {
        log.debug("연차수당 계산 - 사원ID: {}, 지급년월: {}, 회사ID: {}", empId, payMonth, comId);
        
        // TODO: 실제 구현 필요
        log.warn("연차수당 계산 기능이 아직 구현되지 않았습니다.");
        return 0.0;
    }

    /**
     * 연장근무수당 계산
     * 
     * @param empId 사원번호
     * @param payMonth 지급년월
     * @param comId 회사ID
     * @return 연장근무수당
     */
    public double calculateOvertimePay(String empId, String payMonth, String comId) {
        log.debug("연장근무수당 계산 - 사원ID: {}, 지급년월: {}, 회사ID: {}", empId, payMonth, comId);
        
        // TODO: 실제 구현 필요
        log.warn("연장근무수당 계산 기능이 아직 구현되지 않았습니다.");
        return 0.0;
    }
} 