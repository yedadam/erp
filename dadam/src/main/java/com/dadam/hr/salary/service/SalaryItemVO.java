package com.dadam.hr.salary.service;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 급여항목 마스터 VO
 */
@Data
public class SalaryItemVO {
    /** 급여항목 PK (id) */
    private Long id;
    /** 항목코드 (allow_code) */
    private String allowCode;
    /** 항목명 (allow_name) */
    private String allowName;
    /** 카테고리 (type) */
    private String type;
    /** 기본값 (default_amount) */
    private java.math.BigDecimal defaultAmount;
    /** 상태 (acct_yn) */
    private String acctYn;
    /** 생성일 (created_date) */
    private String createdDate;
    /** 비고 (note) */
    private String note;
    /** 회사ID (com_id) */
    private String comId;
} 