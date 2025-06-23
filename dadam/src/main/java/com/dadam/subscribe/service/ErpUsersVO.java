package com.dadam.subscribe.service;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class ErpUsersVO {
	
	private String comId;
	private String password;
	private String comName;
	private String name;
	private String tel;
	private String email;
	private String address;
	private String busTel;
	private Date createdDate;
	private String authority;
	
	//SubsList와 일대다 관게
	private List<SubsListVO> subsList;
}
