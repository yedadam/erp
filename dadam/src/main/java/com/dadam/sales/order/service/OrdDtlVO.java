package com.dadam.sales.order.service;

import lombok.Data;

@Data
public class OrdDtlVO {
	//주문건 상세조회 
	private String ordDtlCode;//상세정보 
	private String itemCode; //품목정보
	private String name; //이름
	private Long price; //가격
	private Long quantity; //수량
	private Long supPrice; //공급가액
	private Long vatPrice; //부가세
	private Long totPrice; //총가격 
	private Long discPrice; //할인금액 
	private String comId; //회사 
	private String ordCode; //주문번호 
}
