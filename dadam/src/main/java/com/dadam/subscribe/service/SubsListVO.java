package com.dadam.subscribe.service;

import java.sql.Date;

import lombok.Data;

@Data
public class SubsListVO {
	private String subsCode;
	private String subsType;
	private Date subsExpiration;
	private Date subsStart;
	private int subsTerm;
	private int subsPrice;
	private int subsQuantity;
	private String subsState;
	private String comId;
	private String optionCode;
	private String billingKey;
	private String constImage;
	private int remaining;
}
