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

    /**
     * 연차 총일수 setter (int→double 변환)
     * @param total - 연차 총일수
     */
    public void setAnnualLeaveTotal(int total) {
        this.annualLeaveTotal = (double) total;
    }
} 