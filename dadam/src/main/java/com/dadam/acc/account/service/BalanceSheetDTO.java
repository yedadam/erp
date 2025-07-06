package com.dadam.acc.account.service;

import lombok.Data;

@Data
public class BalanceSheetDTO {
    private String year;        // 연도
    private String half;        // 반기 (H1, H2)
    private String quarter;     // 분기 (Q1 ~ Q4)
    private String ym;          // 연월 (YYYY-MM)

    private String acctType;        // 계정유형 코드
    private String acctTypeName;    // 계정유형명
    private String acctCode;        // 계정코드
    private String name;            // 계정명 (XML과 일치)

    private String comId;       // 회사 ID

    private Long debit;         // 차변 합계
    private Long credit;        // 대변 합계
    private Long balance;
}
