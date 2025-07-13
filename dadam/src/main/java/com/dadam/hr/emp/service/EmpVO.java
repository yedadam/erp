package com.dadam.hr.emp.service;

import java.util.Date;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 사원 VO
 * - 사원 정보 데이터 전달 객체
 */
@Data
public class EmpVO {
    /** 사원번호 */
    private String empId;
    /** 회사ID */
    private String comId;
    /** 부서코드 */
    private String deptCode;
    /** 비밀번호 */
    private String pwd;
    /** 사원명 */
    private String empName;
    /** 이메일 */
    private String email;
    /** 연락처 */
    private String tel;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    /** 입사일자 */
    private Date hireDate;
    /** 직급코드 */
    private String position;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    /** 퇴사일자 */
    private Date resignDate;
    /** 재직상태코드 */
    private String empStatus;
    /** 근무유형코드 */
    private String workType;
    /** 프로필 이미지 경로 */
    private String profileImgPath;
    /** 비고 */
    private String note;
    /** 급여 */
    private Integer sal;
    /** 은행명 */
    private String bank;
    /** 계좌번호 */
    private String acctNo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    /** 생년월일 */
    private Date birthDate;
    /** 퇴사사유 */
    private String resignReason;
    /** 주소 */
    private String addr;
    /** 상세주소 */
    private String addrDetail;

    /** 부서명 */
    private String deptName;

    /** 직급명칭(한글) */
    private String positionName;   // 직급명칭
    /** 근무유형명칭(한글) */
    private String workTypeName;   // 근무유형명칭
    /** 재직상태명칭(한글) */
    private String empStatusName;  // 재직상태명칭

    // 연차 관련 필드
    /** 연차 총일수 */
    private Double annualLeaveTotal;
    /** 연차 사용일수 */
    private Double annualLeaveUsed;
    /** 연차 잔여일수 */
    private Double annualLeaveRemain;

    // null일 경우 0 반환하는 getter 추가
    public double getAnnualLeaveTotalSafe() {
        return annualLeaveTotal != null ? annualLeaveTotal : 0.0;
    }
    public double getAnnualLeaveUsedSafe() {
        return annualLeaveUsed != null ? annualLeaveUsed : 0.0;
    }
    public double getAnnualLeaveRemainSafe() {
        return annualLeaveRemain != null ? annualLeaveRemain : 0.0;
    }

    /**
     * 연차 총일수 setter (double 변환)
     * @param total - 연차 총일수
     */
    public void setAnnualLeaveTotal(double total) {
        this.annualLeaveTotal = total;
    }
    public void setAnnualLeaveUsed(double used) {
        this.annualLeaveUsed = used;
    }
    public void setAnnualLeaveRemain(double remain) {
        this.annualLeaveRemain = remain;
    }
    
    // === 스케줄러에서 사용하는 메서드 ===
    
    /**
     * 사원 유형 조회 (근무유형 반환)
     * @return 근무유형코드
     */
    public String getEmpType() {
        return this.workType;
    }

    // === 근로계약 관련 필드 ===
    /** 계약유형 */
    private String contractType;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    /** 계약시작일 */
    private Date contractStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    /** 계약종료일 */
    private Date contractEndDate;
    /** 수습기간(개월) */
    private Integer probationPeriod;
    /** 연봉 */
    private Integer annualSalary;
    /** 월급 */
    private Integer monthlySalary;
    /** 상여금 */
    private Integer bonus;
    /** 추가수당 */
    private Integer additionalAllowance;

    // === 근태관리 관련 필드 ===
    /** 근무시작시간 */
    private String workStartTime;
    /** 근무종료시간 */
    private String workEndTime;
    /** 근무패턴 */
    private String workPattern;
    /** 근무스케줄 */
    private String workSchedule;
    /** 점심시작시간 */
    private String lunchStartTime;
    /** 점심종료시간 */
    private String lunchEndTime;
    /** 야근허용여부 */
    private String overtimeAllowed;
    
    // === 급여정보 관련 필드 ===
    /** 식대수당 */
    private Integer mealAllowance;
    /** 교통수당 */
    private Integer transportAllowance;
    /** 급여지급일 */
    private String payDay;
    /** 세금계산방식 */
    private String taxCalculation;
    /** 공제항목 */
    private String deductionType;
    /** 휴가정책 */
    private String leavePolicy;
    
    // === 사원별 급여항목(EMP_ALLOWANCE) 관련 필드 ===
    /** 사원별 급여항목 리스트 */
    private java.util.List<java.util.Map<String, Object>> empAllowances;
} 