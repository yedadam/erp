package com.dadam.inventory.hold.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LotVO {
	
	private String lot;
	private String holdDtlCode;
	private String code;
	private String holdCode;
	private int holdQty;
	private int quantity;
	private String comId;
}
