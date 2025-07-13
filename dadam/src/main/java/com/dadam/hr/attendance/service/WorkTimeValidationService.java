package com.dadam.hr.attendance.service;

import com.dadam.hr.attendance.mapper.WorkTimeValidationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 근로기준법 준수 검증 서비스 (교수님 요구사항 반영)
 * - 최저시간, 최대근무시간 법정 준수
 * - 주간/월간 근무시간 제한
 * - 연장근무 시간 제한
 * - 휴식시간 보장
 * 
 * @author ERP Development Team
 * @version 1.0
 * @since 2025-07-11
 */
@Slf4j
@Service
public class WorkTimeValidationService {

    @Autowired
    private WorkTimeValidationMapper workTimeValidationMapper;

    // 근로기준법 상수
    private static final int MAX_WEEKLY_HOURS = 52; // 주간 최대 근무시간 (연장근무 포함)
    private static final int MAX_MONTHLY_HOURS = 209; // 월간 최대 근무시간 (연장근무 포함)
    private static final int MAX_DAILY_HOURS = 8; // 일일 최대 근무시간
    private static final int MAX_OVERTIME_HOURS = 12; // 월간 최대 연장근무시간
    private static final int MIN_REST_HOURS = 11; // 일일 최소 휴식시간

    /**
     * 근무시간 법정 준수 검증
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param workDate 근무일자
     * @return 검증 결과
     */
    public Map<String, Object> validateWorkTime(String empId, String comId, LocalDate workDate) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 일일 근무시간 검증
            Map<String, Object> dailyValidation = validateDailyWorkTime(empId, comId, workDate);
            
            // 2. 주간 근무시간 검증
            Map<String, Object> weeklyValidation = validateWeeklyWorkTime(empId, comId, workDate);
            
            // 3. 월간 근무시간 검증
            Map<String, Object> monthlyValidation = validateMonthlyWorkTime(empId, comId, workDate);
            
            // 4. 연장근무 시간 검증
            Map<String, Object> overtimeValidation = validateOvertimeHours(empId, comId, workDate);
            
            // 5. 휴식시간 검증
            Map<String, Object> restValidation = validateRestTime(empId, comId, workDate);
            
            // 6. 종합 검증 결과
            boolean isValid = (Boolean) dailyValidation.get("valid") &&
                            (Boolean) weeklyValidation.get("valid") &&
                            (Boolean) monthlyValidation.get("valid") &&
                            (Boolean) overtimeValidation.get("valid") &&
                            (Boolean) restValidation.get("valid");
            
            result.put("valid", isValid);
            result.put("dailyValidation", dailyValidation);
            result.put("weeklyValidation", weeklyValidation);
            result.put("monthlyValidation", monthlyValidation);
            result.put("overtimeValidation", overtimeValidation);
            result.put("restValidation", restValidation);
            
            // 7. 검증 결과 저장
            saveWorkTimeValidation(empId, comId, workDate, result);
            
            log.info("사원 {} 근무시간 검증 완료 - 유효성: {}", empId, isValid);
            
        } catch (Exception e) {
            log.error("근무시간 검증 중 오류 발생", e);
            result.put("valid", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }

    /**
     * 일일 근무시간 검증
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param workDate 근무일자
     * @return 검증 결과
     */
    private Map<String, Object> validateDailyWorkTime(String empId, String comId, LocalDate workDate) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 일일 근무시간 조회
            double dailyHoursDouble = workTimeValidationMapper.getDailyWorkHours(empId, comId, workDate);
            BigDecimal dailyHours = BigDecimal.valueOf(dailyHoursDouble);
            
            // 2. 법정 제한 검증
            boolean isValid = dailyHours.compareTo(BigDecimal.valueOf(MAX_DAILY_HOURS)) <= 0;
            
            result.put("valid", isValid);
            result.put("dailyHours", dailyHours);
            result.put("maxDailyHours", MAX_DAILY_HOURS);
            result.put("violation", !isValid);
            
            if (!isValid) {
                result.put("message", "일일 근무시간이 법정 제한을 초과했습니다.");
            }
            
        } catch (Exception e) {
            log.error("일일 근무시간 검증 중 오류 발생", e);
            result.put("valid", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }

    /**
     * 주간 근무시간 검증
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param workDate 근무일자
     * @return 검증 결과
     */
    private Map<String, Object> validateWeeklyWorkTime(String empId, String comId, LocalDate workDate) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 주간 근무시간 조회
            double weeklyHoursDouble = workTimeValidationMapper.getWeeklyWorkHours(empId, comId, workDate);
            BigDecimal weeklyHours = BigDecimal.valueOf(weeklyHoursDouble);
            
            // 2. 법정 제한 검증
            boolean isValid = weeklyHours.compareTo(BigDecimal.valueOf(MAX_WEEKLY_HOURS)) <= 0;
            
            result.put("valid", isValid);
            result.put("weeklyHours", weeklyHours);
            result.put("maxWeeklyHours", MAX_WEEKLY_HOURS);
            result.put("violation", !isValid);
            
            if (!isValid) {
                result.put("message", "주간 근무시간이 법정 제한을 초과했습니다.");
            }
            
        } catch (Exception e) {
            log.error("주간 근무시간 검증 중 오류 발생", e);
            result.put("valid", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }

    /**
     * 월간 근무시간 검증
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param workDate 근무일자
     * @return 검증 결과
     */
    private Map<String, Object> validateMonthlyWorkTime(String empId, String comId, LocalDate workDate) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 월간 근무시간 조회
            double monthlyHoursDouble = workTimeValidationMapper.getMonthlyWorkHours(empId, comId, workDate);
            BigDecimal monthlyHours = BigDecimal.valueOf(monthlyHoursDouble);
            
            // 2. 법정 제한 검증
            boolean isValid = monthlyHours.compareTo(BigDecimal.valueOf(MAX_MONTHLY_HOURS)) <= 0;
            
            result.put("valid", isValid);
            result.put("monthlyHours", monthlyHours);
            result.put("maxMonthlyHours", MAX_MONTHLY_HOURS);
            result.put("violation", !isValid);
            
            if (!isValid) {
                result.put("message", "월간 근무시간이 법정 제한을 초과했습니다.");
            }
            
        } catch (Exception e) {
            log.error("월간 근무시간 검증 중 오류 발생", e);
            result.put("valid", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }

    /**
     * 연장근무 시간 검증
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param workDate 근무일자
     * @return 검증 결과
     */
    private Map<String, Object> validateOvertimeHours(String empId, String comId, LocalDate workDate) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 월간 연장근무시간 조회
            double monthlyOvertimeHoursDouble = workTimeValidationMapper.getMonthlyOvertimeHours(empId, comId, workDate);
            BigDecimal monthlyOvertimeHours = BigDecimal.valueOf(monthlyOvertimeHoursDouble);
            
            // 2. 법정 제한 검증
            boolean isValid = monthlyOvertimeHours.compareTo(BigDecimal.valueOf(MAX_OVERTIME_HOURS)) <= 0;
            
            result.put("valid", isValid);
            result.put("monthlyOvertimeHours", monthlyOvertimeHours);
            result.put("maxOvertimeHours", MAX_OVERTIME_HOURS);
            result.put("violation", !isValid);
            
            if (!isValid) {
                result.put("message", "월간 연장근무시간이 법정 제한을 초과했습니다.");
            }
            
        } catch (Exception e) {
            log.error("연장근무시간 검증 중 오류 발생", e);
            result.put("valid", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }

    /**
     * 휴식시간 검증
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param workDate 근무일자
     * @return 검증 결과
     */
    private Map<String, Object> validateRestTime(String empId, String comId, LocalDate workDate) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 일일 휴식시간 조회
            double dailyRestHoursDouble = workTimeValidationMapper.getDailyRestHours(empId, comId, workDate);
            BigDecimal dailyRestHours = BigDecimal.valueOf(dailyRestHoursDouble);
            
            // 2. 법정 제한 검증
            boolean isValid = dailyRestHours.compareTo(BigDecimal.valueOf(MIN_REST_HOURS)) >= 0;
            
            result.put("valid", isValid);
            result.put("dailyRestHours", dailyRestHours);
            result.put("minRestHours", MIN_REST_HOURS);
            result.put("violation", !isValid);
            
            if (!isValid) {
                result.put("message", "일일 휴식시간이 법정 최소시간을 충족하지 못했습니다.");
            }
            
        } catch (Exception e) {
            log.error("휴식시간 검증 중 오류 발생", e);
            result.put("valid", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }

    /**
     * 근무시간 검증 결과 저장
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param workDate 근무일자
     * @param validationResult 검증 결과
     */
    private void saveWorkTimeValidation(String empId, String comId, LocalDate workDate, Map<String, Object> validationResult) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("empId", empId);
            params.put("comId", comId);
            params.put("workDate", workDate);
            params.put("validationResult", (Boolean) validationResult.get("valid"));
            params.put("createdAt", LocalDateTime.now());
            
            // 위반 유형 설정
            String violationType = null;
            if (!(Boolean) validationResult.get("valid")) {
                if (!(Boolean) ((Map<String, Object>) validationResult.get("dailyValidation")).get("valid")) {
                    violationType = "DAILY_LIMIT";
                } else if (!(Boolean) ((Map<String, Object>) validationResult.get("weeklyValidation")).get("valid")) {
                    violationType = "WEEKLY_LIMIT";
                } else if (!(Boolean) ((Map<String, Object>) validationResult.get("monthlyValidation")).get("valid")) {
                    violationType = "MONTHLY_LIMIT";
                } else if (!(Boolean) ((Map<String, Object>) validationResult.get("overtimeValidation")).get("valid")) {
                    violationType = "OVERTIME_LIMIT";
                } else if (!(Boolean) ((Map<String, Object>) validationResult.get("restValidation")).get("valid")) {
                    violationType = "REST_TIME";
                }
            }
            params.put("violationType", violationType);
            
            workTimeValidationMapper.insertWorkTimeValidation(params);
            
        } catch (Exception e) {
            log.error("근무시간 검증 결과 저장 중 오류 발생", e);
        }
    }

    /**
     * 근로기준법 위반 통계 조회
     * 
     * @param comId 회사 ID
     * @param fromDate 시작일
     * @param toDate 종료일
     * @return 위반 통계
     */
    public Map<String, Object> getWorkTimeViolationStats(String comId, LocalDate fromDate, LocalDate toDate) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("comId", comId);
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
            
            // 1. 위반 유형별 통계
            Map<String, Object> violationStats = workTimeValidationMapper.getViolationStats(params);
            
            // 2. 부서별 위반 통계
            List<Map<String, Object>> deptViolationStats = workTimeValidationMapper.getDeptViolationStats(params);
            
            // 3. 사원별 위반 통계
            List<Map<String, Object>> empViolationStats = workTimeValidationMapper.getEmpViolationStats(params);
            
            result.put("violationStats", violationStats);
            result.put("deptViolationStats", deptViolationStats);
            result.put("empViolationStats", empViolationStats);
            
        } catch (Exception e) {
            log.error("근로기준법 위반 통계 조회 중 오류 발생", e);
            result.put("error", e.getMessage());
        }
        
        return result;
    }

    /**
     * 근로기준법 준수 알림 생성
     * 
     * @param empId 사원 ID
     * @param comId 회사 ID
     * @param violationType 위반 유형
     */
    public void createWorkTimeViolationAlert(String empId, String comId, String violationType) {
        try {
            Map<String, Object> alert = new HashMap<>();
            alert.put("empId", empId);
            alert.put("comId", comId);
            alert.put("violationType", violationType);
            alert.put("alertDate", LocalDateTime.now());
            alert.put("status", "PENDING");
            
            workTimeValidationMapper.insertWorkTimeViolationAlert(alert);
            
            log.info("근로기준법 위반 알림 생성 - 사원ID: {}, 위반유형: {}", empId, violationType);
            
        } catch (Exception e) {
            log.error("근로기준법 위반 알림 생성 중 오류 발생", e);
        }
    }
} 