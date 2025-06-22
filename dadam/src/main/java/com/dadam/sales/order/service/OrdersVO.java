package com.dadam.sales.order.service;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrdersVO {
	//주문건 전체조회 
	private String ordCode;
	private String vdrCode; 
	private String status;
	private Date createdDate;
	private String payMethod; 
	private Date payDate; 
	private int totQuantity; 
	private Date reqDlvDate; 
	private String empId; 
	private String updateId; 
	private Date updateDate; 
	private String comId; 
	private int totSupPrice;
	private int totVatPrice; 
	private int totDisc; 
	private int discount; 
	private int totPrice; 
	private String quoCode; 
	
	private List<OrdDtlVO> detailList; 
}
