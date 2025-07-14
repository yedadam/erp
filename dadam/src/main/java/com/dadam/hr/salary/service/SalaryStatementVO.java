package com.dadam.hr.salary.service;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 급여명세서 VO (급여관리 화면 연동용)
 * - 프론트엔드 컬럼명(카멜케이스)과 일치하도록 필드명 통일
 * - 각 필드별 한글 설명 주석 추가
 * - 동적 수당/공제 항목(sal01~sal10 등) 포함
 */
@Data
public class SalaryStatementVO {
    /** 명세서 ID */
    private Long id;
    /** 회사ID */
    private String comId;
    /** 사원번호 */
    private String empId;
    /** 사원명 */
    private String empName;
    /** 부서명 */
    private String deptName;
    /** 직급명 */
    private String positionName;
    /** 부서코드 */
    private String deptCode;
    /** 급여월(정산월) */
    private String calcMonth;
    /** 지급년월 */
    private String payMonth;
    /** 기본급 */
    private BigDecimal baseSalary;
    /** 기본급(문자열) */
    private String baseSal;
    /** 보너스 */
    private String bonus;
    /** OT수당 */
    private String otAllow;
    /** 휴일수당 */
    private String holiAllow;
    /** 식대 */
    private String mealAllow;
    /** 소득세 */
    private String incTax;
    /** 국민연금 */
    private String natPension;
    /** 건강보험 */
    private String healthInsur;
    /** 고용보험 */
    private String empInsur;
    /** 장기요양보험 */
    private String ltcInsur;
    /** 실수령액(문자열) */
    private String netSalary;
    /** 실수령액(숫자) */
    private BigDecimal netPay;
    /** 상태(정상/지급완료 등) */
    private String status;
    /** 지급일(YYYY-MM-DD) */
    private String createdDate;
    /** 수정일 */
    private LocalDateTime updatedDate;
    /** 명세서 코드 */
    private String salId;
    /** 근무일수 */
    private Integer workDays;
    /** 실제근무일수 */
    private Integer actualWorkDays;
    /** 정상출근일수 */
    private Integer normalDays;
    /** 지각일수 */
    private Integer lateDays;
    /** 조퇴일수 */
    private Integer earlyLeaveDays;
    /** 결근일수 */
    private Integer absentDays;
    /** 연장근무시간 */
    private BigDecimal overtimeHours;
    /** 연장근무수당 */
    private BigDecimal overtimePay;
    /** 지각공제 */
    private BigDecimal lateDeduction;
    /** 조퇴공제 */
    private BigDecimal earlyLeaveDeduction;
    /** 결근공제 */
    private BigDecimal absentDeduction;
    /** 연차수당 */
    private BigDecimal annualLeavePay;
    /** 수당 총액 */
    private BigDecimal allowanceTotal;
    /** 공제 총액 */
    private BigDecimal deductionTotal;
    // === 동적 수당/공제 항목(프론트 컬럼명과 일치) ===
    /** 자격수당 */
    private String sal1;
    /** 복지포인트 */
    private String sal2;
    /** 예비수당3 */
    private String sal3;
    /** 예비수당4 */
    private String sal4;
    /** 예비수당5 */
    private String sal5;
    /** 예비수당6 */
    private String sal6;
    /** 예비수당7 */
    private String sal7;
    /** 예비수당8 */
    private String sal8;
    /** 예비수당9 */
    private String sal9;
    /** 예비수당10 */
    private String sal10;
    // === 사원별 급여항목(EMP_ALLOWANCE) 관련 필드 ===
    /** 사원별 급여항목 리스트 */
    private List<Map<String, Object>> empAllowances;
} 