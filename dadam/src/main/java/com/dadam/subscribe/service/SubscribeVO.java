package com.dadam.subscribe.service;

import lombok.Data;

@Data
public class SubscribeVO {
	private String optionCode;
	private String optionName;
	private int price;
	private int discount;
	private String content;
	private String contentDetail;
}
