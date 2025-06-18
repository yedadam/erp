package com.dadam.standard.item.service;

import lombok.Data;

@Data
public class ItemVO {
	private String itemCode;
	private String name;
	private String type;
	private int price;
}
