package com.dadam.sales.order.service;

import java.util.Date;

import lombok.Data;

@Data
public class OrdersVO {
	//주문건 전체조회 
	private String ordCode; //주문번호 
	private Date createdDate; //등록일자 
	private String vdrName; //거래처이름
	private String vdrCode; //거래처코드
	private Date reqDlvDate; //납기일자 
	private int totPrice; //총금액 
	private int creditPrice; //여신한도 
	private int creditBalPrice; //여신잔액 
	private String payMethod; //지불방식 
	private String status; //진행상태 
	private int discount; //할인율
}
