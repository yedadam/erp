package com.dadam.inventory.hold.service;

import java.util.Date;

import com.dadam.sample.service.SampleVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HoldVO {
	// 홀드
	private String holdCode; // 홀드코드
	private String stkCode;   // 재고코드
	private String itemCode;   // 품목코드
	private String itemName;   // 품목이름
	private int quantity;   // 수량
	private String type;   // 홀드구분
	private String typeName;
	private String status; 
	private String statusName; 
	private String note;   // 메모
	private String locCode;   // 위치코드
	private String empId;  // 담당자 
	private String comId;  // 회사id 혹시몰라서
	private String lot;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date createdDate;  // 등록일자
	
}
