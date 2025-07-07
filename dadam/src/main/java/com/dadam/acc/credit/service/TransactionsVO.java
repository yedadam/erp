package com.dadam.acc.credit.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransactionsVO {
    private String txnHistCode; // 입출금내역코드(PK)
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date txnDate;        // 거래일자
    private String txnType;      // 입출금구분(01입금/02출금)
    private String txnName;      // 입금자/출금자
    private String txnMemo;      // 통장내용
    private Double price;     // 금액
    private Double balance;   // 잔액
    private String bank;          // 은행명
    private String acctNo;       // 계좌번호
    private String comId;        // 회사ID(FK)
    private String cfmdYn;       // 확인여부('Y'/'N')
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date cfmdAt;         // 확인일시
    private String cfmdBy;       // 확인자

} 