package com.dadam.chat.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChatMessageVO {
	private String messageId;
	private String message;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	private String chatId;
	private String empId;
	private String empName;
	private String comId;
}
