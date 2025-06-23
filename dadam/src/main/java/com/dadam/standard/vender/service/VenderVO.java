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
	private Long  creditPeriod;
	private Long  creditPrice;
	private Long creditBalPrice;
	private Long discount;
	private String comId;
}
