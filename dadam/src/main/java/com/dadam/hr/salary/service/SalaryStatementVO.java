package com.dadam.hr.salary.service;

import java.util.Date;

/**
 * 급여명세서 VO
 * - 급여1~10, 기본급, 수당, 공제, 근태, 실지급액 등 포함
 * - 회사별 커스터마이징을 위해 확장성 고려
 */
public class SalaryStatementVO {
    /** 명세서 ID */
    private Long id;
    /** 사원 ID */
    private Long empId;
    /** 지급년월 */
    private String payMonth;
    /** 기본급 */
    private int baseSalary;
    /** 급여1~10 (회사별 매핑) */
    private int salary1;
    private int salary2;
    private int salary3;
    private int salary4;
    private int salary5;
    private int salary6;
    private int salary7;
    private int salary8;
    private int salary9;
    private int salary10;
    /** 수당 총액 */
    private int allowanceTotal;
    /** 공제 총액 */
    private int deductionTotal;
    /** 근태(지각, 결근 등) */
    private int attendance;
    /** 실지급액 */
    private int netPay;
    /** 생성일 */
    private Date createdDate;
    /** 수정일 */
    private Date updatedDate;
    // getter/setter 생략 (lombok 사용시 @Data 등 적용)
} 