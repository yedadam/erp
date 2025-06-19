package com.dadam.sales.order.service;

import lombok.Data;

@Data
public class OrdDtlVO {
	//주문건 상세조회 
	private String ordDtlCode;//상세정보 
	private String itemCode; //품목정보
	private String name; //이름
	private int price; //가격
	private int quantity; //수량
	private int supPrice; //공급가액
	private int vatPrice; //부가세
	private int totPrice; //총가격 
	private int discPrice; //할인금액 
}
