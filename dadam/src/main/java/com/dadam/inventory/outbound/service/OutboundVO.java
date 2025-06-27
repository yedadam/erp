package com.dadam.inventory.outbound.service;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OutboundVO {

	private String shipReqDtlCode;
	private String itemCode;
	private String name;
	private String status;
	private int quantity;
	private int totPrice;
	private String vdrCode;
	private String vdrName;
	private String shipReqCode;
	private Date shipExpDate;
	private String empId;
	private String empName;
}
