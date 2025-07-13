package com.dadam.hr.attendance;

import com.dadam.hr.attendance.service.AnnualLeaveService;
import com.dadam.hr.attendance.service.AnnualLeaveVO;
import com.dadam.hr.emp.service.EmpService;
import com.dadam.hr.emp.service.EmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 자동 연차계산 스케줄러 (교수님 요구사항 반영)
 * - 매일 입사자 확인 스케줄러
 * - 입사일 기준 연차 자동 부여
 * - 연차 사용 시 자동 업데이트
 * - 년 단위 체크 및 자동 계산
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2025-07-11
 */
@Slf4j
// @Component
// @EnableScheduling
public class AnnualLeaveScheduler {

    @Autowired
    private AnnualLeaveService annualLeaveService;
    
    @Autowired
    private EmpService empService;

    /**
     * 매일 새벽 1시 실행 - 새로운 입사자 확인 및 연차 부여
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void checkNewEmployees() {
        log.info("새로운 입사자 연차 부여 스케줄러 시작");
        
        try {
            LocalDate today = LocalDate.now();
            
            // 1. 오늘 입사한 사원 조회
            List<EmpVO> newEmployees = empService.getNewEmployees(today);
            
            if (newEmployees.isEmpty()) {
                log.info("오늘 입사한 사원이 없습니다.");
                return;
            }
            
            // 2. 각 사원별 연차 정보 생성
            for (EmpVO employee : newEmployees) {
                try {
                    createAnnualLeaveForNewEmployee(employee);
                    log.info("사원 {} 연차 정보 생성 완료", employee.getEmpId());
                } catch (Exception e) {
                    log.error("사원 {} 연차 정보 생성 중 오류 발생: {}", employee.getEmpId(), e.getMessage());
                }
            }
            
            log.info("새로운 입사자 연차 부여 완료 - 총 {}명", newEmployees.size());
            
        } catch (Exception e) {
            log.error("새로운 입사자 연차 부여 스케줄러 중 오류 발생", e);
        }
    }

    /**
     * 매일 새벽 2시 실행 - 연차 만료 및 갱신 처리
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void updateAnnualLeave() {
        log.info("연차 만료 및 갱신 스케줄러 시작");
        
        try {
            LocalDate today = LocalDate.now();
            
            // 1. 연차 만료일 체크 및 처리
            updateExpiredAnnualLeave(today);
            
            // 2. 연차 사용 내역 업데이트
            updateUsedAnnualLeave(today);
            
            // 3. 연차 자동 갱신 (입사일 기준)
            updateAnnualLeaveByHireDate(today);
            
            log.info("연차 만료 및 갱신 처리 완료");
            
        } catch (Exception e) {
            log.error("연차 만료 및 갱신 스케줄러 중 오류 발생", e);
        }
    }

    /**
     * 매월 1일 새벽 3시 실행 - 월별 연차 통계 생성
     */
    @Scheduled(cron = "0 0 3 1 * ?")
    public void generateMonthlyLeaveStatistics() {
        log.info("월별 연차 통계 생성 스케줄러 시작");
        
        try {
            LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
            
            // 1. 월별 연차 사용 통계 생성
            generateMonthlyLeaveStats(firstDayOfMonth);
            
            // 2. 연차 미사용자 알림 생성
            generateUnusedLeaveNotifications(firstDayOfMonth);
            
            log.info("월별 연차 통계 생성 완료");
            
        } catch (Exception e) {
            log.error("월별 연차 통계 생성 스케줄러 중 오류 발생", e);
        }
    }

    /**
     * 새로운 입사자 연차 정보 생성
     * 
     * @param employee 사원 정보
     */
    private void createAnnualLeaveForNewEmployee(EmpVO employee) {
        try {
            // 1. 입사일 기준 연차 계산
            LocalDate hireDate = employee.getHireDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            LocalDate today = LocalDate.now();
            
            // 2. 입사 후 경과 개월 수 계산
            Period period = Period.between(hireDate, today);
            int monthsWorked = period.getYears() * 12 + period.getMonths();
            
            // 3. 연차 일수 계산 (교수님 요구사항: 입사일 기준, 년 단위 체크)
            int annualLeaveDays = calculateAnnualLeaveDays(monthsWorked, employee.getEmpType());
            
            // 4. 연차 정보 생성
            AnnualLeaveVO annualLeave = new AnnualLeaveVO();
            annualLeave.setEmpId(employee.getEmpId());
            annualLeave.setComId(employee.getComId());
            annualLeave.setYear(today.getYear());
            annualLeave.setTotalDays(annualLeaveDays);
            annualLeave.setUsedDays(0);
            annualLeave.setRemainingDays(annualLeaveDays);
            annualLeave.setHireDate(employee.getHireDate());
            annualLeave.setCreatedAt(LocalDateTime.now());
            annualLeave.setUpdatedAt(LocalDateTime.now());
            
            // 5. 연차 정보 저장
            annualLeaveService.createAnnualLeave(annualLeave);
            
            log.info("사원 {} 연차 정보 생성 - 총 {}일, 사용 {}일, 잔여 {}일", 
                    employee.getEmpId(), annualLeaveDays, 0, annualLeaveDays);
            
        } catch (Exception e) {
            log.error("사원 {} 연차 정보 생성 중 오류 발생: {}", employee.getEmpId(), e.getMessage());
        }
    }

    /**
     * 연차 일수 계산 (교수님 요구사항: 입사일 기준, 년 단위 체크)
     * 
     * @param monthsWorked 근무 개월 수
     * @param empType 고용형태
     * @return 연차 일수
     */
    private int calculateAnnualLeaveDays(int monthsWorked, String empType) {
        // 근로기준법에 따른 연차 계산
        if (monthsWorked < 12) {
            // 1년 미만: 1개월 만근 시 1일
            return Math.min(monthsWorked, 11);
        } else if (monthsWorked < 24) {
            // 1년 이상 2년 미만: 15일
            return 15;
        } else if (monthsWorked < 36) {
            // 2년 이상 3년 미만: 16일
            return 16;
        } else if (monthsWorked < 48) {
            // 3년 이상 4년 미만: 17일
            return 17;
        } else if (monthsWorked < 60) {
            // 4년 이상 5년 미만: 18일
            return 18;
        } else if (monthsWorked < 72) {
            // 5년 이상 6년 미만: 19일
            return 19;
        } else if (monthsWorked < 84) {
            // 6년 이상 7년 미만: 20일
            return 20;
        } else if (monthsWorked < 96) {
            // 7년 이상 8년 미만: 21일
            return 21;
        } else {
            // 8년 이상: 22일 (최대)
            return 22;
        }
    }

    /**
     * 연차 만료일 체크 및 처리
     * 
     * @param today 오늘 날짜
     */
    private void updateExpiredAnnualLeave(LocalDate today) {
        try {
            // 1. 만료된 연차 조회 (입사일 기준 2년 후 만료)
            List<AnnualLeaveVO> expiredLeaves = annualLeaveService.getExpiredAnnualLeaves(today);
            
            for (AnnualLeaveVO leave : expiredLeaves) {
                // 2. 만료 처리
                leave.setStatus("EXPIRED");
                leave.setUpdatedAt(LocalDateTime.now());
                annualLeaveService.updateAnnualLeave(leave);
                
                log.info("사원 {} 연차 만료 처리 - 만료일: {}", leave.getEmpId(), leave.getExpiryDate());
            }
            
            log.info("연차 만료 처리 완료 - 총 {}건", expiredLeaves.size());
            
        } catch (Exception e) {
            log.error("연차 만료 처리 중 오류 발생", e);
        }
    }

    /**
     * 연차 사용 내역 업데이트
     * 
     * @param today 오늘 날짜
     */
    private void updateUsedAnnualLeave(LocalDate today) {
        try {
            // 1. 오늘 사용된 연차 조회
            List<AnnualLeaveVO> usedLeaves = annualLeaveService.getUsedLeavesToday(today);
            
            for (AnnualLeaveVO usedLeave : usedLeaves) {
                String empId = usedLeave.getEmpId();
                String comId = usedLeave.getComId();
                int usedDays = (int) usedLeave.getDays();
                
                // 2. 연차 사용 내역 업데이트
                AnnualLeaveVO annualLeave = annualLeaveService.getAnnualLeaveByEmpAndYear(empId, comId, today.getYear());
                
                if (annualLeave != null) {
                    annualLeave.setUsedDays(annualLeave.getUsedDays() + usedDays);
                    annualLeave.setRemainingDays(annualLeave.getTotalDays() - annualLeave.getUsedDays());
                    annualLeave.setUpdatedAt(LocalDateTime.now());
                    
                    annualLeaveService.updateAnnualLeave(annualLeave);
                    
                    log.info("사원 {} 연차 사용 업데이트 - 사용: {}일, 잔여: {}일", 
                            empId, annualLeave.getUsedDays(), annualLeave.getRemainingDays());
                }
            }
            
            log.info("연차 사용 내역 업데이트 완료 - 총 {}건", usedLeaves.size());
            
        } catch (Exception e) {
            log.error("연차 사용 내역 업데이트 중 오류 발생", e);
        }
    }

    /**
     * 입사일 기준 연차 자동 갱신
     * 
     * @param today 오늘 날짜
     */
    private void updateAnnualLeaveByHireDate(LocalDate today) {
        try {
            // 1. 입사일이 오늘인 사원 조회
            List<EmpVO> employeesWithHireDateToday = empService.getEmployeesByHireDate(today);
            
            for (EmpVO employee : employeesWithHireDateToday) {
                try {
                    // 2. 기존 연차 정보 조회
                    AnnualLeaveVO existingLeave = annualLeaveService.getAnnualLeaveByEmpAndYear(
                            employee.getEmpId(), employee.getComId(), today.getYear());
                    
                    if (existingLeave == null) {
                        // 3. 새로운 연차 정보 생성
                        createAnnualLeaveForNewEmployee(employee);
                    } else {
                        // 4. 기존 연차 정보 갱신
                        updateExistingAnnualLeave(existingLeave, employee);
                    }
                    
                    log.info("사원 {} 입사일 기준 연차 갱신 완료", employee.getEmpId());
                    
                } catch (Exception e) {
                    log.error("사원 {} 연차 갱신 중 오류 발생: {}", employee.getEmpId(), e.getMessage());
                }
            }
            
            log.info("입사일 기준 연차 갱신 완료 - 총 {}명", employeesWithHireDateToday.size());
            
        } catch (Exception e) {
            log.error("입사일 기준 연차 갱신 중 오류 발생", e);
        }
    }

    /**
     * 기존 연차 정보 갱신
     * 
     * @param existingLeave 기존 연차 정보
     * @param employee 사원 정보
     */
    private void updateExistingAnnualLeave(AnnualLeaveVO existingLeave, EmpVO employee) {
        try {
            LocalDate hireDate = employee.getHireDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            LocalDate today = LocalDate.now();
            
            // 1. 근무 개월 수 재계산
            Period period = Period.between(hireDate, today);
            int monthsWorked = period.getYears() * 12 + period.getMonths();
            
            // 2. 새로운 연차 일수 계산
            int newTotalDays = calculateAnnualLeaveDays(monthsWorked, employee.getEmpType());
            
            // 3. 연차 정보 업데이트
            existingLeave.setTotalDays(newTotalDays);
            existingLeave.setRemainingDays(newTotalDays - existingLeave.getUsedDays());
            existingLeave.setUpdatedAt(LocalDateTime.now());
            
            annualLeaveService.updateAnnualLeave(existingLeave);
            
            log.info("사원 {} 연차 정보 갱신 - 총: {}일, 사용: {}일, 잔여: {}일", 
                    employee.getEmpId(), newTotalDays, existingLeave.getUsedDays(), existingLeave.getRemainingDays());
            
        } catch (Exception e) {
            log.error("사원 {} 연차 정보 갱신 중 오류 발생: {}", employee.getEmpId(), e.getMessage());
        }
    }

    /**
     * 월별 연차 통계 생성
     * 
     * @param firstDayOfMonth 월 첫째 날
     */
    private void generateMonthlyLeaveStats(LocalDate firstDayOfMonth) {
        try {
            // 1. 월별 연차 사용 통계 생성
            Map<String, Object> monthlyStats = annualLeaveService.generateMonthlyLeaveStatistics(firstDayOfMonth);
            
            // 2. 통계 정보 저장
            annualLeaveService.saveMonthlyLeaveStatistics(monthlyStats);
            
            log.info("월별 연차 통계 생성 완료 - 년월: {}", firstDayOfMonth);
            
        } catch (Exception e) {
            log.error("월별 연차 통계 생성 중 오류 발생", e);
        }
    }

    /**
     * 연차 미사용자 알림 생성
     * 
     * @param firstDayOfMonth 월 첫째 날
     */
    private void generateUnusedLeaveNotifications(LocalDate firstDayOfMonth) {
        try {
            // 1. 연차 미사용자 조회 (잔여 연차 5일 이상)
            List<AnnualLeaveVO> unusedLeaves = annualLeaveService.getUnusedLeaveEmployees(firstDayOfMonth);
            
            for (AnnualLeaveVO leave : unusedLeaves) {
                // 2. 알림 생성
                createUnusedLeaveNotification(leave);
                
                log.info("사원 {} 연차 미사용 알림 생성 - 잔여: {}일", 
                        leave.getEmpId(), leave.getRemainingDays());
            }
            
            log.info("연차 미사용 알림 생성 완료 - 총 {}건", unusedLeaves.size());
            
        } catch (Exception e) {
            log.error("연차 미사용 알림 생성 중 오류 발생", e);
        }
    }

    /**
     * 연차 미사용 알림 생성
     * 
     * @param leave 연차 정보
     */
    private void createUnusedLeaveNotification(AnnualLeaveVO leave) {
        try {
            // 실제 구현 시에는 알림 서비스를 통해 알림 생성
            // 예: notificationService.createUnusedLeaveNotification(leave);
            
            log.info("연차 미사용 알림 생성 - 사원ID: {}, 잔여일수: {}", 
                    leave.getEmpId(), leave.getRemainingDays());
            
        } catch (Exception e) {
            log.error("연차 미사용 알림 생성 중 오류 발생", e);
        }
    }
} 