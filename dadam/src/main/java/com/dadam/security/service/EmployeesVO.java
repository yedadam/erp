package com.dadam.security.service;

import java.sql.Date;

import lombok.Data;

@Data
public class EmployeesVO {
	private String empId;
	private String comId;
	private String deptId;
	private String pwd;
	private String empName;
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
}
