package com.dadam.standard.vender.service;

import lombok.Data;

@Data
public class VenderVO {
	private String vdrCode;
	private String type;
	private String vdrName;
	private String bizNo;
	private String ceoName;
	private String addr;
	private String addDetail;
	private String email;
	private String tel;
	private String bank;
	private String acctNo;
	private int  creditPeriod;
	private int  creditPrice;
	private int creditBalPrice;
	private int discount;
	private String comId;
}
