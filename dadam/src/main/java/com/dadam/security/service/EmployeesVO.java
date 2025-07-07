package com.dadam.security.service;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class EmployeesVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String empId;
	private String comId;
	private String deptCode;
	private String pwd;
	private String empName;
	private String deptName;
	private String email; 
	private String tel;
	private Date hireDate;
	private String position;
	private Date resignDate;
	private String empStatus;
	private String workType;
	private String profileImgPath;
	private String note;
	private String sal;
	private Date subsExpiration;
	private String optionCode;
	private String authCode;
	private String master;
}
