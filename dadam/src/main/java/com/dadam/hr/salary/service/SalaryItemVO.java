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
    /** 카테고리(항목유형): 기본급, 수당, 복리후생, 상여, 공제, 비과세, 기타 */
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
    /** 계산방식 (calc_type) */
    private String calcType;
    /** 적용대상 (work_types, 콤마구분, 공통코드와 매핑) */
    private String workTypes;
    /** 적용대상 한글명 (work_type_names, 콤마구분) */
    private String workTypeNames;
    /** 과세여부 (tax_free_yn, Y=과세, N=비과세) */
    private String taxFreeYn;
} 