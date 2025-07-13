package com.dadam.inventory.warehouse.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseVO {
	
	private String whCode;
	private String name;
	private String type;
	private String note;
	private String comId;
	private String locCode;
	private String itemCode;
	private int quantity;
	private int hold_qty;
}
