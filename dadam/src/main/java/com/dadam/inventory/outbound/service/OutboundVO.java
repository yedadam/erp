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

	private String shipReqDtlCode;
	private String itemCode;
	private String itemName;
	private String name;
	private String status;
	private String statusName;
	private int quantity;
	private int totPrice;
	private int price;
	private String vdrCode;
	private String vdrName;
	private String shipReqCode;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date createdDate;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date shipExpDate;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date dliveDate;
	private String empId;
	private String empName;
	private String comId;
	private String lot;
}
