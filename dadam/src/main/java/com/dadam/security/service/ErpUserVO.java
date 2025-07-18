package com.dadam.security.service;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class ErpUserVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
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
	private String master;
	private Date subsExpiration;
	private String optionCode;
	private String deptCode;
}
