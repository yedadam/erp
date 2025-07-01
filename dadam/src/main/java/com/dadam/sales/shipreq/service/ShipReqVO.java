package com.dadam.sales.shipreq.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ShipReqVO {
	
	private String shipReqCode; //출하번호 
 	private String vdrCode; //거래처번호
	private String vdrName; //거래처이름
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate; //등록일자 
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date shipExpDate; //출하예정일자 
	private String status; //상태
	private String empId; //회사번호 
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateDate; //수정일자 
	private String updateId; //수정ID  
	private Long totPrice;   //총합계 
	private String comId;    //회사id 
	private String ordCode;  //주문번호 
}
