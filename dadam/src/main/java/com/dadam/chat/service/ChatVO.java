package com.dadam.chat.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChatVO {
	private String empId;
	private String empName;
	private String deptName;
	private String profileImgPath;
	private String chatDtlId;
	private String chatId;
	private String name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	private int chatQuantity;
	private String message;
	private String comId;
}
