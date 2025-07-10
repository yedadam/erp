package com.dadam.inventory.outbound.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OutboundVO {

	private String outCode;
	private String code;
	private String outDtlCode;
	private String holdCode;
	private String shipReqDtlCode;
	private String itemCode;
	private String itemName;
	private String name;
	private String status;
	private String statusName;
	private String shipstatus;
	private String hstatus;
	private String hdstatus;
	private String holdDtlCode;
	private String typeName;
	private String type;
	private String note;
	private int quantity;
	private int holdQty;
	private int currQty;
	private int qty;
	private int totPrice;
	private int price;
	private String vdrCode;
	private String vdrName;
	private String shipReqCode;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date shipExpDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dliveDate;
	private String empId;
	private String empName;
	private String comId;
	private String lot;
	private int hqty;   // 출하의뢰수량
	private int hdqty;   // 홀드상세수량
}
