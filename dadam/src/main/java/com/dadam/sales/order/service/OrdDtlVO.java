package com.dadam.sales.order.service;

import lombok.Data;

@Data
public class OrdDtlVO {
	//주문건 상세조회 
	private String ordDtlCode;
	private String itemCode;
	private String name; 
	private int price;
	private int quantity;
	private int supPrice; 
	private int vatPrice;
	private int totPrice; 
	private int discPrice;
}
