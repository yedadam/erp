package com.dadam.acc.account.service;

import java.util.Date;

import lombok.Data;

@Data
public class AccountVO {
	private String acctCode; //계정과목코드
	private String name; //계정과목 타이틀
	private String acctType; // 대분류
	private String acctClass; //중분류
	private String acctYn; //사용여부
	private String note; //비고
	private Date createdDate; // 등록일자
	private Date updateDate; //수정일자
	private String comId; //회사id
}
