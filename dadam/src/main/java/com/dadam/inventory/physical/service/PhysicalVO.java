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
public class PhysicalVO {

	private String phyCode;   // 코드
	private String whCode;		// 창고코드
	private String whName;		// 창고이름
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date phyDate;		// 반영일자
	private String phyEmpId;	// 담당자
	private String empId;	// 담당자
	private String phyEmpName;	
	private String apprEmpId;	// 승인자
	private String apprEmpName;
	private String note;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	private String comId;
	private List<PhysicalDetailVO> sub;
}
