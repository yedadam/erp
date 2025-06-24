package com.dadam.acc.credit.service;

import java.util.Date;

import lombok.Data;

@Data
public class CreditVO {
	
	private String vdrName;
	private String vdrCode;
	private int creditPeriod;
	private int creditPrice;
	private int creditBalPrice;
	private String status;
	
	private Date createdDate;

}
