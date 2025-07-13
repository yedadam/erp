package com.dadam.hr.emp.service;

import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 근로계약 VO
 * - 근로계약 정보 데이터 전달 객체
 */
@Data
public class ContractVO {
    /** 계약코드 */
    private String contCode;
    /** 계약유형 */
    private String contType;
    /** 계약시작일 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /** 계약종료일 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /** 연봉 */
    private Integer annSal;
    /** 월급 */
    private Integer monSal;
    /** 근무시작시간 */
    private String workStart;
    /** 근무종료시간 */
    private String workEnd;
    /** 1일 근무시간 */
    private String dayWorkHrs;
    /** 수습기간 */
    private Integer probPeriod;
    /** 계약상태 */
    private String contStatus;
    /** 종료사유 */
    private String termReason;
    /** 계약서 파일경로 */
    private String contFilePath;
    /** 생성일 */
    private String createdDate;
    /** 상여 */
    private Integer bonus;
    /** 추가수당 */
    private Integer extraAllow;
    /** 부서ID */
    private String deptId;
    /** 회사ID */
    private String comId;
    /** 급여지급일 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date salPayDate;
    /** 사원ID */
    private String empId;
    
    // 조회용 필드
    /** 사원명 */
    private String empName;
    /** 부서명 */
    private String deptName;
    /** 계약유형명 */
    private String contTypeName;
} 