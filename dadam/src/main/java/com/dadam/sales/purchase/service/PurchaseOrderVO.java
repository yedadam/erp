package com.dadam.sales.purchase.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PurchaseOrderVO {
	private String note;   // 메모
	private String empId;  // 담당자 
	private String comId;  // 회사id 혹시몰라서
	private String lot;
	private int price;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date createDate;
	private String locCode;

	// 발주서
	private String purOrdCode;
	private String vdrName;
	private String vdrCode;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date purOrdDate;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date expDlvDate;
	private String purOrdReqCode;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date purOrdUdtDate;
	private String status;
	private String vatYn;
	private int totSupPrice;
	private int totVatPrice;
	private int quantity;
	private int currQty;
	private String empName;
	
	// 발주서상세
	private String purOrdDtlCode;
	private String itemCode;
	private String itemName;
	private int supPrice;
	private int vatPrice;
	private int totPrice;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date reccvDate;
	
	
	//발주 의뢰
	private String purReqCode;
	private String inCode;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date createdDate;
	private Date purInDate;
	//private String empId;
	//private String status;
	//private String note;
	//private String comId;
}
