package com.dadam.inventory.inbound.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseVO {

	private String note;   // 메모
	private String empId;  // 담당자 
	private String comId;  // 회사id
	private String lot;
	private int price;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date createDate;
	private String locCode;
	private String stkCode;

	// 발주서
	private String purOrdCode;
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
	private int pquantity;
	private int notStock;
	private int currQty;
	
	
	// 발주서상세
	private String purOrdDtlCode;
	private String itemCode;
	private String itemName;
	private int supPrice;
	private int vatPrice;
	private int totPrice;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date reccvDate;

}
