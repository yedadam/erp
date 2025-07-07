package com.dadam.hr.salary.service;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 급여항목 마스터 VO
 */
@Data
public class SalaryItemVO {
    /** 항목 고유번호 */
    private Long id;
    /** 회사ID */
    private String comId;
    /** 급여항목코드 */
    private String allowCode;
    /** 급여항목명 */
    private String allowName;
    /** 항목구분(지급/공제 등) */
    private String type;
    /** 계산방식(FIXED/RATE/HOUR 등) */
    private String calcType;
    /** 기본금액 */
    private BigDecimal defaultAmount;
    /** 비과세여부(Y/N) */
    private String taxFreeYn;
    /** 사용여부(Y/N) */
    private String acctYn;
    /** 정렬순서 */
    private Integer sortOrder;
    /** 생성일 */
    private LocalDateTime createdDate;
    /** 수정일 */
    private LocalDateTime updatedDate;
} 