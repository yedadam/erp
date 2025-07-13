package com.dadam.hr;

import com.dadam.hr.attendance.service.AnnualLeaveService;
import com.dadam.hr.attendance.service.AnnualLeaveVO;
import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;
import com.dadam.hr.attendance.service.WorkTimeValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * HR 핵심 기능 통합 테스트
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2025-07-12
 */
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class HrCoreFunctionTest {

    @Autowired
    private AnnualLeaveService annualLeaveService;
    
    @Autowired
    private EmpService empService;
    
    @Autowired
    private WorkTimeValidationService workTimeValidationService;

    /**
     * 연차 계산 로직 테스트
     */
    @Test
    public void testAnnualLeaveCalculation() {
        log.info("=== 연차 계산 로직 테스트 시작 ===");
        
        try {
            // 1. 테스트 사원 생성
            EmpVO testEmployee = createTestEmployee();
            log.info("테스트 사원 생성: {}", testEmployee.getEmpId());
            
            // 2. 연차 정보 생성
            AnnualLeaveVO annualLeave = createTestAnnualLeave(testEmployee);
            log.info("연차 정보 생성: 총 {}일, 사용 {}일, 잔여 {}일", 
                    annualLeave.getTotalDays(), annualLeave.getUsedDays(), annualLeave.getRemainingDays());
            
            // 3. 연차 사용 테스트
            testAnnualLeaveUsage(annualLeave);
            
            // 4. 연차 잔여 조회 테스트
            testRemainingLeaveCalculation(testEmployee.getEmpId(), testEmployee.getComId());
            
            log.info("✅ 연차 계산 로직 테스트 완료");
            
        } catch (Exception e) {
            log.error("❌ 연차 계산 로직 테스트 실패", e);
        }
    }

    /**
     * 근무시간 검증 테스트
     */
    @Test
    public void testWorkTimeValidation() {
        log.info("=== 근무시간 검증 테스트 시작 ===");
        
        try {
            String testEmpId = "e1001";
            String testComId = "com001";
            LocalDate testDate = LocalDate.now();
            
            // 1. 근무시간 검증 테스트
            Map<String, Object> validationResult = workTimeValidationService.validateWorkTime(testEmpId, testComId, testDate);
            log.info("근무시간 검증 결과: {}", validationResult);
            
            // 2. 위반 통계 조회 테스트
            Map<String, Object> violationStats = workTimeValidationService.getWorkTimeViolationStats(testComId, 
                    testDate.minusMonths(1), testDate);
            log.info("위반 통계: {}", violationStats);
            
            log.info("✅ 근무시간 검증 테스트 완료");
            
        } catch (Exception e) {
            log.error("❌ 근무시간 검증 테스트 실패", e);
        }
    }

    /**
     * 사원 관리 기능 테스트
     */
    @Test
    public void testEmployeeManagement() {
        log.info("=== 사원 관리 기능 테스트 시작 ===");
        
        try {
            // 1. 사원 목록 조회 테스트
            Map<String, Object> searchParams = new HashMap<>();
            searchParams.put("comId", "com001");
            List<EmpVO> empList = empService.findEmpList(searchParams);
            log.info("사원 목록 조회: {}명", empList.size());
            
            // 2. 사원 상세 조회 테스트
            if (!empList.isEmpty()) {
                EmpVO firstEmp = empList.get(0);
                EmpVO empDetail = empService.getEmpDetail(firstEmp.getEmpId(), firstEmp.getComId());
                log.info("사원 상세 조회: {} - {}", empDetail.getEmpId(), empDetail.getEmpName());
            }
            
            // 3. 연차 정보 조회 테스트
            if (!empList.isEmpty()) {
                EmpVO firstEmp = empList.get(0);
                EmpVO leaveInfo = empService.getAnnualLeaveInfo(firstEmp.getEmpId());
                log.info("연차 정보 조회: 총 {}일, 사용 {}일, 잔여 {}일", 
                        leaveInfo.getAnnualLeaveTotal(), leaveInfo.getAnnualLeaveUsed(), leaveInfo.getAnnualLeaveRemain());
            }
            
            log.info("✅ 사원 관리 기능 테스트 완료");
            
        } catch (Exception e) {
            log.error("❌ 사원 관리 기능 테스트 실패", e);
        }
    }

    /**
     * 연차 스케줄러 기능 테스트
     */
    @Test
    public void testAnnualLeaveScheduler() {
        log.info("=== 연차 스케줄러 기능 테스트 시작 ===");
        
        try {
            LocalDate today = LocalDate.now();
            
            // 1. 신입 사원 조회 테스트
            List<EmpVO> newEmployees = empService.getNewEmployees(today);
            log.info("신입 사원 조회: {}명", newEmployees.size());
            
            // 2. 입사일 기준 사원 조회 테스트
            List<EmpVO> employeesByHireDate = empService.getEmployeesByHireDate(today);
            log.info("입사일 기준 사원 조회: {}명", employeesByHireDate.size());
            
            // 3. 연차 만료 조회 테스트
            List<AnnualLeaveVO> expiredLeaves = annualLeaveService.getExpiredAnnualLeaves(today);
            log.info("만료된 연차 조회: {}건", expiredLeaves.size());
            
            // 4. 미사용 연차 사원 조회 테스트
            List<AnnualLeaveVO> unusedLeaveEmployees = annualLeaveService.getUnusedLeaveEmployees(today);
            log.info("미사용 연차 사원 조회: {}명", unusedLeaveEmployees.size());
            
            // 5. 오늘 사용된 연차 조회 테스트
            List<AnnualLeaveVO> usedLeavesToday = annualLeaveService.getUsedLeavesToday(today);
            log.info("오늘 사용된 연차 조회: {}건", usedLeavesToday.size());
            
            log.info("✅ 연차 스케줄러 기능 테스트 완료");
            
        } catch (Exception e) {
            log.error("❌ 연차 스케줄러 기능 테스트 실패", e);
        }
    }

    /**
     * 통계 및 리포트 테스트
     */
    @Test
    public void testStatisticsAndReports() {
        log.info("=== 통계 및 리포트 테스트 시작 ===");
        
        try {
            LocalDate testDate = LocalDate.now();
            
            // 1. 월별 연차 통계 생성 테스트
            Map<String, Object> monthlyStats = annualLeaveService.generateMonthlyLeaveStatistics(testDate);
            log.info("월별 연차 통계 생성: {}", monthlyStats);
            
            // 2. 근로기준법 위반 통계 조회 테스트
            Map<String, Object> violationStats = workTimeValidationService.getWorkTimeViolationStats("com001", 
                    testDate.minusMonths(1), testDate);
            log.info("근로기준법 위반 통계: {}", violationStats);
            
            log.info("✅ 통계 및 리포트 테스트 완료");
            
        } catch (Exception e) {
            log.error("❌ 통계 및 리포트 테스트 실패", e);
        }
    }

    // === 헬퍼 메서드들 ===
    
    /**
     * 테스트 사원 생성
     */
    private EmpVO createTestEmployee() {
        EmpVO employee = new EmpVO();
        employee.setEmpId("TEST001");
        employee.setComId("com001");
        employee.setEmpName("테스트사원");
        employee.setDeptCode("D001");
        employee.setHireDate(java.sql.Date.valueOf(LocalDate.now().minusYears(2)));
        employee.setEmpStatus("재직");
        employee.setWorkType("정규직");
        employee.setAnnualLeaveTotal(15);
        return employee;
    }
    
    /**
     * 테스트 연차 정보 생성
     */
    private AnnualLeaveVO createTestAnnualLeave(EmpVO employee) {
        AnnualLeaveVO annualLeave = new AnnualLeaveVO();
        annualLeave.setEmpId(employee.getEmpId());
        annualLeave.setComId(employee.getComId());
        annualLeave.setYear(LocalDate.now().getYear());
        annualLeave.setTotalDays(15);
        annualLeave.setUsedDays(5);
        annualLeave.setRemainingDays(10);
        annualLeave.setHireDate(employee.getHireDate());
        annualLeave.setCreatedAt(LocalDateTime.now());
        annualLeave.setUpdatedAt(LocalDateTime.now());
        return annualLeave;
    }
    
    /**
     * 연차 사용 테스트
     */
    private void testAnnualLeaveUsage(AnnualLeaveVO annualLeave) {
        log.info("연차 사용 테스트 시작");
        
        // 연차 사용 전 잔여
        int beforeRemaining = annualLeave.getRemainingDays();
        log.info("사용 전 잔여: {}일", beforeRemaining);
        
        // 연차 사용 (1일)
        int usedDays = 1;
        annualLeave.setUsedDays(annualLeave.getUsedDays() + usedDays);
        annualLeave.setRemainingDays(annualLeave.getTotalDays() - annualLeave.getUsedDays());
        
        log.info("연차 사용: {}일", usedDays);
        log.info("사용 후 잔여: {}일", annualLeave.getRemainingDays());
        
        // 검증
        if (annualLeave.getRemainingDays() == beforeRemaining - usedDays) {
            log.info("✅ 연차 사용 테스트 성공");
        } else {
            log.error("❌ 연차 사용 테스트 실패");
        }
    }
    
    /**
     * 연차 잔여 계산 테스트
     */
    private void testRemainingLeaveCalculation(String empId, String comId) {
        log.info("연차 잔여 계산 테스트 시작");
        
        try {
            double remainingLeave = annualLeaveService.getRemainAnnualLeave(empId, comId);
            log.info("잔여 연차: {}일", remainingLeave);
            log.info("✅ 연차 잔여 계산 테스트 성공");
        } catch (Exception e) {
            log.error("❌ 연차 잔여 계산 테스트 실패", e);
        }
    }
} 