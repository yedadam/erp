package com.dadam.hr.salary.service;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 급여 지급 VO
 * - 급여 지급 처리 및 이력 관리
 */
@Data
public class SalaryPaymentVO {
    /** 지급 ID */
    private Long paymentId;
    /** 회사ID */
    private String comId;
    /** 급여명세서 ID */
    private Long statementId;
    /** 사원번호 */
    private String empId;
    /** 지급년월 */
    private String payMonth;
    /** 지급일자 */
    private String payDate;
    /** 지급금액 */
    private BigDecimal payAmount;
    /** 지급방법 (BANK/CASH 등) */
    private String payMethod;
    /** 은행명 */
    private String bankName;
    /** 계좌번호 */
    private String accountNumber;
    /** 지급상태 (PENDING/APPROVED/REJECTED/PAID) */
    private String payStatus;
    /** 승인자ID */
    private String approverId;
    /** 승인일시 */
    private LocalDateTime approvedAt;
    /** 반려사유 */
    private String rejectReason;
    /** 생성일시 */
    private LocalDateTime createdAt;
    /** 수정일시 */
    private LocalDateTime updatedAt;
    
    // 조회용 필드
    /** 사원명 */
    private String empName;
    /** 부서명 */
    private String deptName;
    /** 승인자명 */
    private String approverName;
    /** 급여명세서 정보 */
    private SalaryStatementVO salaryStatement;
} 