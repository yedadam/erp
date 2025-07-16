package com.dadam.inventory.physical.service;

import java.util.Date;
import java.util.List;

import com.dadam.inventory.hold.service.LotVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhysicalDetailVO {

	private String phyDtlCode;   // 코드
	private String phyCode;		// 창고코드
	private String lot;		// 창고이름
	private String itemCode;	// 담당자
	private int quantity;	// 담당자
	private String locCode;	
	private String note;
	private String comId;
}
