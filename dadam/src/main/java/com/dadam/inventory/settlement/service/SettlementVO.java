package com.dadam.inventory.settlement.service;

import java.sql.Date;

import lombok.Data;

@Data
public class SettlementVO {
	private String setCode;
	private String deptCode;
	private String empId;
	private Date createdDate;
	private String status;
	private String comId;
	private String setDtlCode;
	private String itemCode;
	private int preQuantity;
	private int prePrice;
	private int purQuantity;
	private int delQuantity;
	private int adjQuantity;
	private int afterQuantity;
	private int afterPrice;
	private String elecCode;
	private String docType;
	private String fileImage;
	private String approveStatus;
	private String resectReason;
	private String reqId;
	private String appId;
	private String name;
	private String pkCode;
	private String empName;
    private int proceedNum;
    private String signImage; 
}
