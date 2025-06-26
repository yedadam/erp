package com.dadam.sales.purchase.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PurchaseOrderDetailVO {
	
	// 발주서상세
		private String purOrdDtlCode;
		private String itemCode;
		private String purOrdCode;
		private String itemName;
		private int supPrice;
		private int vatPrice;
		private int totPrice;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date reccvDate;
		private int quantity;
		private String comId;
}
