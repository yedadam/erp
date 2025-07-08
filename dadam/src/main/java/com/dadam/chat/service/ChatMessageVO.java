package com.dadam.chat.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChatMessageVO {
	private String messageId;
	private String message;
	@JsonFormat(pattern = "HH:mm:ss", timezone = "Asia/Seoul")
	private Date createdDate;
	private String chatId;
	private String empId;
	private String empName;
	private String comId;
}
