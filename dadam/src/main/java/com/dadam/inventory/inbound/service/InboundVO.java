package com.dadam.inventory.inbound.service;

import java.util.Date;

import com.dadam.sample.service.SampleVO;

import lombok.Data;

@Data
public class InboundVO {
	// 입고
	private String whCode; // 창고코드
	private String name;   // 창고이름
	private String type;   // 창고구분 (창고 wht01, 공장 wht02)
	
	private String note;   // 메모
	private String empId;  // 담당자 
	private String comId;  // 회사id 혹시몰라서
	
}
