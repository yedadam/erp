package com.dadam.standard.item.service;

import lombok.Data;

@Data
public class MoneyVO {
	private String txnHistCode;
	private String txnDate;
	private String txnType;
	private String txnMemo;
	private int price;
	private int balance;
	private String bank;
	private String acctNo;
	private String comId;
	private String itemName;
	private String itemCode;
	private String name;
	private int ajdQty;
    private String note;
    private String adjCode;
    private String salPayCode;
    private int baseSal;
    private int bonus;
    private int extraAllow;
    private int totPrice;
    private String empName;
}
