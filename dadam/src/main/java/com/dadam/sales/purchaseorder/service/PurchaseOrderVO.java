package com.dadam.sales.purchaseorder.service;

import java.util.Date;

import lombok.Data;

@Data
public class PurchaseOrderVO {
	
		private String note;   // 메모
		private String empId;  // 담당자 
		private String comId;  // 회사id 혹시몰라서

		// 발주서
		private String purOrdCode;
		private String vdrCode;
		private Date purOrdDate;
		private Date expDlvDate;
		private String purOrdReqCode;
		private Date purOrdUdtDate;
		private String status;
		private String vatYn;
		private int totSupPrice;
		private int totVatPrice;
		private int quantity;
		
		
		// 발주서상세
		private String purOrdDtlCode;
		private String itemCode;
		private String itemName;
		private int supPrice;
		private int vatPrice;
		private int totPrice;
		private Date reccvDate;
}
