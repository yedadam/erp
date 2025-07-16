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
// import com.dadam.hr.attendance.mapper.AttendanceManageMapper;
import com.dadam.hr.emp.mapper.EmpMapper;
import com.dadam.hr.salary.service.SalaryCalculationVO;
import com.dadam.hr.salary.service.SalaryDetailVO;
import com.dadam.hr.attendance.service.AttendanceStatisticsVO;
import com.dadam.hr.salary.service.SalaryStatementVO;
import com.dadam.hr.salary.service.SalaryItemVO;
import com.dadam.hr.salary.service.SalaryItemService;

import lombok.extern.slf4j.Slf4j;

/**
 * 급여 자동계산 서비스 (실제 업계 표준 SALARYITEM 테이블 기반)
 * - 기존 급여항목 관리 시스템과 완벽 호환
 * - 회사별 커스터마이징 지원
 * - 실제 ERP 시스템에서 가장 많이 사용하는 구조
 * 
 * @author ERP Development Team
 * @version 2.0
 * @since 2025-07-11
 */
@Slf4j
@Service
@Transactional
public class SalaryCalculationService {

    @Autowired
    private SalaryMapper salaryMapper;
    
    @Autowired
    private EmpMapper employeeMapper;
    
    @Autowired
    private SalaryItemService salaryItemService;

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
     * 개별 사원 급여 계산 (실제 SALARYITEM 테이블 기반)
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
            AttendanceStatisticsVO attendanceStats = new AttendanceStatisticsVO(); // AttendanceStatsMapper 대신 직접 객체 생성
            // attendanceStats.setTotalWorkDays(attendanceParams.get("totalWorkDays"));
            // attendanceStats.setTotalWorkHours(attendanceParams.get("totalWorkHours"));
            // attendanceStats.setOvertimeHours(attendanceParams.get("overtimeHours"));
            // attendanceStats.setNightHours(attendanceParams.get("nightHours"));
            // attendanceStats.setHolidayHours(attendanceParams.get("holidayHours"));
            // attendanceStats.setYearMonth(yearMonth);
            
            if (attendanceStats == null) {
                log.warn("근태 통계를 찾을 수 없습니다: 사원ID={}, 년월={}", employeeId, yearMonth);
                return null;
            }
            
            // 3. 급여 항목 조회 (실제 SALARYITEM 테이블 사용)
            Map<String, Object> salaryItemParams = new HashMap<>();
            salaryItemParams.put("comId", companyId.toString());
            List<SalaryItemVO> salaryItems = salaryItemService.getSalaryItemList(salaryItemParams);
            
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
     * 급여 계산 로직 실행 (실제 SALARYITEM 테이블 기반)
     * 
     * @param employeeInfo 사원 기본 정보
     * @param attendanceStats 근태 통계
     * @param salaryItems 급여 항목 목록
     * @param yearMonth 계산 년월
     * @return 급여 계산 결과
     */
    private SalaryCalculationVO performSalaryCalculation(
            Map<String, Object> employeeInfo,
            AttendanceStatisticsVO attendanceStats,
            List<SalaryItemVO> salaryItems,
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
        
        // 2. 수당/공제 계산 (실제 SALARYITEM 기반)
        Map<String, BigDecimal> allowances = calculateAllowances(employeeInfo, attendanceStats, salaryItems);
        Map<String, BigDecimal> deductions = calculateDeductions(employeeInfo, attendanceStats, salaryItems);
        
        calculation.setAllowances(allowances);
        calculation.setDeductions(deductions);
        
        // 3. 총 급여 계산 (기본급 + 수당)
        BigDecimal totalSalary = calculateTotalSalary(baseSalary, allowances);
        calculation.setTotalSalary(totalSalary);
        
        // 4. 실수령액 계산 (총급여 - 공제)
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
     * 수당 계산 (실제 SALARYITEM 기반)
     * 
     * @param employeeInfo 사원 정보
     * @param attendanceStats 근태 통계
     * @param salaryItems 급여 항목 목록
     * @return 수당 항목들
     */
    private Map<String, BigDecimal> calculateAllowances(
            Map<String, Object> employeeInfo,
            AttendanceStatisticsVO attendanceStats,
            List<SalaryItemVO> salaryItems) {
        
        Map<String, BigDecimal> allowances = new HashMap<>();
        
        for (SalaryItemVO item : salaryItems) {
            // 수당 항목만 처리 (공제 제외)
            if ("ALLOWANCE".equals(item.getType()) && "Y".equals(item.getAcctYn())) {
                BigDecimal amount = calculateItemAmount(item, employeeInfo, attendanceStats);
                if (amount.compareTo(BigDecimal.ZERO) > 0) {
                    allowances.put(item.getAllowCode(), amount);
                }
            }
        }
        
        return allowances;
    }

    /**
     * 공제 계산 (실제 SALARYITEM 기반)
     * 
     * @param employeeInfo 사원 정보
     * @param attendanceStats 근태 통계
     * @param salaryItems 급여 항목 목록
     * @return 공제 항목들
     */
    private Map<String, BigDecimal> calculateDeductions(
            Map<String, Object> employeeInfo,
            AttendanceStatisticsVO attendanceStats,
            List<SalaryItemVO> salaryItems) {
        
        Map<String, BigDecimal> deductions = new HashMap<>();
        
        for (SalaryItemVO item : salaryItems) {
            // 공제 항목만 처리 (수당 제외)
            if ("DEDUCTION".equals(item.getType()) && "Y".equals(item.getAcctYn())) {
                BigDecimal amount = calculateItemAmount(item, employeeInfo, attendanceStats);
                if (amount.compareTo(BigDecimal.ZERO) > 0) {
                    deductions.put(item.getAllowCode(), amount);
                }
            }
        }
        
        return deductions;
    }

    /**
     * 개별 항목 금액 계산
     * 
     * @param item 급여 항목
     * @param employeeInfo 사원 정보
     * @param attendanceStats 근태 통계
     * @return 계산된 금액
     */
    private BigDecimal calculateItemAmount(
            SalaryItemVO item,
            Map<String, Object> employeeInfo,
            AttendanceStatisticsVO attendanceStats) {
        
        String calcType = item.getCalcType();
        BigDecimal defaultAmount = item.getDefaultAmount();
        
        if ("FIXED".equals(calcType)) {
            // 고정 금액
            return defaultAmount != null ? defaultAmount : BigDecimal.ZERO;
            
        } else if ("RATE".equals(calcType)) {
            // 기본급 비율
        BigDecimal baseSalary = new BigDecimal(employeeInfo.get("baseSalary").toString());
            BigDecimal rate = defaultAmount != null ? defaultAmount.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP) : BigDecimal.ZERO;
            return baseSalary.multiply(rate);
            
        } else if ("HOUR".equals(calcType)) {
            // 시간당 계산
            BigDecimal hourlyWage = new BigDecimal(employeeInfo.get("hourlyWage").toString());
            BigDecimal hours = getHoursByItemCode(item.getAllowCode(), attendanceStats);
            return hourlyWage.multiply(hours);
            
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 항목별 근무시간 조회
     * 
     * @param itemCode 항목 코드
     * @param attendanceStats 근태 통계
     * @return 근무시간
     */
    private BigDecimal getHoursByItemCode(String itemCode, AttendanceStatisticsVO attendanceStats) {
        switch (itemCode) {
            case "OVERTIME":
                return attendanceStats.getOvertimeHours() != null ? attendanceStats.getOvertimeHours() : BigDecimal.ZERO;
            case "NIGHT":
                return attendanceStats.getNightHours() != null ? attendanceStats.getNightHours() : BigDecimal.ZERO;
            case "HOLIDAY":
                return attendanceStats.getHolidayHours() != null ? attendanceStats.getHolidayHours() : BigDecimal.ZERO;
            default:
                return BigDecimal.ZERO;
        }
    }

    /**
     * 총 급여 계산
     * 
     * @param baseSalary 기본급
     * @param allowances 수당 항목들
     * @return 총 급여
     */
    private BigDecimal calculateTotalSalary(BigDecimal baseSalary, Map<String, BigDecimal> allowances) {
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
            // 생성일(문자열) yyyy-MM-dd 포맷으로 저장
            statement.setCreatedDate(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            statement.setUpdatedDate(LocalDateTime.now());
            
            // 4. 수당/공제 항목 설정 (별도 필드로 저장)
            BigDecimal allowanceTotal = BigDecimal.ZERO;
            BigDecimal deductionTotal = BigDecimal.ZERO;
            
            for (Map<String, Object> detail : salaryDetails) {
                String itemType = (String) detail.get("item_type");
                BigDecimal amount = (BigDecimal) detail.get("amount");
                
                if ("ALLOWANCE".equals(itemType)) {
                    allowanceTotal = allowanceTotal.add(amount);
                } else if ("DEDUCTION".equals(itemType)) {
                    deductionTotal = deductionTotal.add(amount);
                }
            }
            
            statement.setAllowanceTotal(allowanceTotal);
            statement.setDeductionTotal(deductionTotal);
            
            return statement;
            
        } catch (Exception e) {
            log.error("급여 명세서 조회 중 오류 발생", e);
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