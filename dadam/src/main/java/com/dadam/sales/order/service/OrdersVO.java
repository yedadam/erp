package com.dadam.sales.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrdersVO {
	//주문건 전체조회 
	private String ordCode;
	private String vdrCode; 
	private String vdrName;
	private String status;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	private String payMethod;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date payDate; 
	private Long totQuantity; 
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date reqDlvDate; 
	private String empId; 
	private String updateId; 
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateDate; 
	private String comId; 
	private Long totSupPrice;
	private Long totVatPrice; 
	private Long totDisc; 
	private Long discount; 
	private Long totPrice; 
	private String quoCode; 
	private int creditPrice;
	private int creditBalPrice;
	
	
}
