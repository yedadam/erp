package com.dadam.inventory.inbound.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InboundVO {
	// 입고
	private String whCode; // 창고코드
	private String name;   // 창고이름
	private String type;   // 창고구분 (창고 wht01, 공장 wht02)
	private String itemCode;   // 품목코드
	private String itemName;	// 품목이름
	private String purOrdDtlCode;	// 발주서코드
	private String empId;  // 담당자
	private String empName;  // 담당자
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate; // 입고 일자
	private int quantity; // 입고수량
	private int currQty;// 입고수량
	private int price; // 단가
	private String status; // 단가
	private String statusName; // 단가
	private String note;   // 메모
	private String comId;  // 회사id
	private String locCode;   // 창고 위치코드
	private String lot;		// 입고 lot
	
}
