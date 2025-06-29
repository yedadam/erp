package com.dadam.sales.shipreq.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ShipReqDtlVO {
	private String shipReqDtlCode; //출하상세의뢰번호
	private String vdrCode; //거래처코드
	private String vdrName;
	private String itemCode; //품목코드
	private String name; //아이템이름 
	private String status; //상태  
	private Long quantity; //수량
	private Long price; //개당가격
	
	private Long supPrice; //공급가액 
	private Long vatPrice; //부가세 
	private Long discPrice; //할인금액 
	private Long totPrice;  //총액
	
	private String comId; //회사id
	private String shipReqCode; //출하의뢰번호 
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dliveDate; 		
}
