package com.dadam.acc.credit.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditVO {
	
	//여신관리 헤더
	private String vdrName;		// 거래처 명
	private String vdrCode;		// 거래처 코드
	private String bizNo;		// 거래처 사업자등록번호
	private String empId;		// 담당자
	private Double creditPeriod;	// 여신 개월수
	private Double creditPrice; 	// 여신 총 금액
	private Double creditBalPrice;	// 여신 잔액
	private String status;		// 상태
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;	// 등록일자
	
	// 여신관리 상세
	private String articleCode; // 항목코드
	private Double totPrice; 		// 총 금액
	private String bank; 		// 은행명
	private String acctNo;		// 계좌명
	private String type;		// 상태
	private String phyEmpId;	// 작성자
	private String chitCode;	// 전표코드(수금번호)
	private Double price;
	private String iType;
}
