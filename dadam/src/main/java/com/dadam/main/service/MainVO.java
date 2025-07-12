package com.dadam.main.service;

import lombok.Data;

@Data
public class MainVO {
	private String orderDay;
	private int totPrice;
	private String itemName;
	private int itemQuantity;
	private double percentage;
	private double safePercentage;
	private String itemCode;
}
